package me.jrl1004.plugins.magic.managers;

import java.util.HashMap;
import java.util.Locale;

import me.jrl1004.plugins.magic.abilities.AbilityTelekenesis;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class TelekenesisManager implements Listener {

	private static TelekenesisManager manager;
	private HashMap<String, SpellTarget> spellSelection;

	private TelekenesisManager() {
		spellSelection = new HashMap<String, SpellTarget>();
	}

	// #StaticAbuse
	public static TelekenesisManager getInstance() {
		if (manager == null) manager = new TelekenesisManager();
		return manager;
	}

	public void useTheForce(Player player) {
		if (spellSelection.get(player.getName()) == null) return;
		if (spellSelection.get(player.getName()).throwTarget(player.getLocation().getDirection().multiply(2)))
			spellSelection.remove(player.getName());
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		if (player.getInventory().getHeldItemSlot() != 8) return; // Not using a spell
		if (AbilityManager.getInstance().getCurrentSpell(player) instanceof AbilityTelekenesis == false) return; // Not using telekenesis
		if (!AbilityManager.getInstance().getCurrentSpell(player).canUse(player)) return;
		if (spellSelection.containsKey(player.getName()) || event.getAction().toString().contains("AIR")) return; // They did not select a target
		if (!event.getAction().toString().contains("BLOCK")) return; // They did not physically click a block (used as anti pressure plate check)
		spellSelection.put(player.getName(), new SpellTarget(event.getClickedBlock()));
		ChatManager.messageMain(player, "Telekenesis > Block Selected!");
	}

	@EventHandler
	public void onClick(PlayerInteractEntityEvent event) {
		final Player player = event.getPlayer();
		if (player.getInventory().getHeldItemSlot() != 8) return; // Not using a spell
		if (AbilityManager.getInstance().getCurrentSpell(player) instanceof AbilityTelekenesis == false) return; // Not using telekenesis
		if (!AbilityManager.getInstance().getCurrentSpell(player).canUse(player)) return;
		if (spellSelection.containsKey(player.getName())) return; // They did not select a target
		if (!(event.getRightClicked() instanceof LivingEntity)) return; // So you can only throw players and mobs
		spellSelection.put(player.getName(), new SpellTarget((LivingEntity) event.getRightClicked()));
		if (event.getRightClicked() instanceof Player) {
			ChatManager.messageMain(player, "You picked up " + ((Player) event.getRightClicked()).getName());
		}
		else {
			ChatManager.messageMain(player, "You picked up a " + event.getRightClicked().getType().toString().toLowerCase(Locale.ENGLISH));
		}
	}

	protected class SpellTarget {
		private final Block blockTarget;
		private final LivingEntity entityTarget;
		private long timeSelected;

		public SpellTarget(LivingEntity entityTarget) {
			this.entityTarget = entityTarget;
			this.blockTarget = null;
			timeSelected = System.nanoTime();
		}

		public SpellTarget(Block blockTarget) {
			this.blockTarget = blockTarget;
			this.entityTarget = null;
			timeSelected = 0;
		}

		@SuppressWarnings("deprecation")
		public boolean throwTarget(Vector direction) {
			if (System.nanoTime() - timeSelected < 1e8) return false;
			if (blockTarget == null) {
				if (entityTarget.isValid()) {
					entityTarget.setVelocity(direction);
				}
			}
			else {
				FallingBlock block = blockTarget.getWorld().spawnFallingBlock(blockTarget.getLocation().add(0.5, 0.5, 0.5), blockTarget.getType(), blockTarget.getState().getRawData());
				blockTarget.setType(Material.AIR);
				block.setVelocity(direction);
			}
			return true;
		}
	}
}
