package me.jrl1004.plugins.magic.managers;

import java.util.Arrays;
import java.util.HashMap;

import me.jrl1004.plugins.magic.abilities.AbilityHealing;
import me.jrl1004.plugins.magic.abilities.AbilityHeartripping;
import me.jrl1004.plugins.magic.particles.ParticleEffect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class HeartrippingManager implements Listener {

	private static HeartrippingManager				manager;
	private ItemStack								defaultSpell	= customStack(Material.BOOK, ChatColor.RED + "HeartRipping", ChatColor.GOLD + "Currently bound to:", "[Nobody]");
	private HashMap<OfflinePlayer, OfflinePlayer>	spellSelection;

	private HeartrippingManager() {
		spellSelection = new HashMap<OfflinePlayer, OfflinePlayer>();
	}

	// #StaticAbuse
	public static HeartrippingManager getInstance() {
		if (manager == null) manager = new HeartrippingManager();
		return manager;
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player && event.getDamager() instanceof Player)) return; // We only want to check this if players hit each other
		final Player attacker = (Player) event.getDamager();
		if (attacker.getInventory().getHeldItemSlot() != 8) return;
		final Player victim = (Player) event.getEntity();
		if (AbilityManager.getInstance().getCurrentSpell(attacker) instanceof AbilityHealing) {
			event.setCancelled(true);
			victim.setHealth(victim.getMaxHealth());
			ParticleEffect.HEART.display(Vector.getRandom(), 1, victim.getEyeLocation().add(0, 0, .25), Bukkit.getOnlinePlayers());
		}
		if (!(AbilityManager.getInstance().getCurrentSpell(attacker) instanceof AbilityHeartripping)) return; // Make sure the person punching has heartripping active
		if (!AbilityManager.getInstance().getCurrentSpell(attacker).canUse(attacker)) return;
		if (victim.hasPermission("heart.block")) {
			ChatManager.messageBad(attacker, "That player is too powerful!");
			ChatManager.messageGood(victim, attacker.getName() + " attempted to rip out your heart but was too weak!");
			return;
		}
		if (!spellSelection.containsKey(attacker) || !spellSelection.get(attacker).equals(victim)) {
			ChatManager.messageGood(attacker, "You ripped " + victim.getName() + "'s heart out of their body!");
			ChatManager.messageBad(victim, attacker.getName() + " has ripped your heart out of your body!");
			spellSelection.put(attacker, victim);
		}
		attacker.getInventory().setItemInMainHand(getValidBook(attacker));
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		OfflinePlayer key = null;
		for (OfflinePlayer p : spellSelection.keySet())
			if (spellSelection.get(p).equals((OfflinePlayer) event.getEntity())) {
				key = p;
				break;
			}
		if (key != null) {
			spellSelection.remove(key);
			if (key.isOnline())
				ChatManager.messageMain(key.getPlayer(), "Your Heartripper target has perished.");
		}
	}

	private static ItemStack customStack(Material material, String name, String... lore) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(name);
		if (lore.length > 0) itemMeta.setLore(Arrays.asList(lore));
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public void damageOther(Player player) {
		player.getInventory().setItemInMainHand(getValidBook(player));
		if (!spellSelection.containsKey(player)) return;
		OfflinePlayer op = spellSelection.get(player);
		if (!op.isOnline()) {
			player.getInventory().setItemInMainHand(getValidBook(player));
			return;
		}
		op.getPlayer().damage(1, player);
	}

	private ItemStack getValidBook(Player player) {
		if (!spellSelection.containsKey(player)) return defaultSpell.clone();
		OfflinePlayer op = spellSelection.get(player);
		if (!op.isOnline()) return defaultSpell.clone();
		return customStack(Material.REDSTONE, ChatColor.RED + "HeartRipping", ChatColor.GOLD + "Currently bound to:", spellSelection.get(player).getName());
	}
}
