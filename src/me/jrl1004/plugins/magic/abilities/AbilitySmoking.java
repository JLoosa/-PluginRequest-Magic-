package me.jrl1004.plugins.magic.abilities;

import me.jrl1004.plugins.magic.managers.ChatManager;
import me.jrl1004.plugins.magic.particles.ParticleEffect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class AbilitySmoking extends AbstractAbility {

	public AbilitySmoking() {
		super("Smoking");
		setPerm("magic.smoking");
	}

	@Override
	public void castSpell(Player player) {
		if (!canUse(player)) {
			ChatManager.messageBad(player, "You are not capable of performing this spell.");
			return;
		}
		renderPlayerPartciles(player.getLocation());
		Vector savedDirection = player.getLocation().getDirection();
		BlockIterator bi = new BlockIterator(player, 15);
		Block landingBlock = null;
		while (bi.hasNext()) {
			Block current = bi.next();
			landingBlock = current;
			if (current.getType().isBlock() && current.getType().isSolid()) {
				landingBlock = current;
				break;
			}
			ParticleEffect.SMOKE_LARGE.display(savedDirection, 0.1f, current.getLocation().add(0.5, 0.5, 0.5), Bukkit.getOnlinePlayers());
		}
		if (landingBlock == null) {
			landingBlock = player.getLocation().getBlock();
		}
		Location landingLocation = landingBlock.getLocation().add(0.5, 1, 0.5);
		landingLocation.setDirection(savedDirection);
		player.teleport(landingLocation);
		player.getLocation().setDirection(savedDirection);
		renderPlayerPartciles(landingBlock.getLocation());
		player.setFallDistance(0f); // Turn off teleport falling damage
	}

	private void renderPlayerPartciles(Location location) {
		for (int i = 0; i < 125; i++) {
			ParticleEffect.SMOKE_LARGE.display(Vector.getRandom(), 0.01f, location.clone().add(0.5, 1, 0.5), Bukkit.getOnlinePlayers());
		}
		location.getWorld().playSound(location, Sound.WITHER_SHOOT, 1, 1);
	}
}
