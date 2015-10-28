package me.jrl1004.plugins.magic.abilities;

import java.util.HashSet;

import me.jrl1004.plugins.magic.managers.ChatManager;
import me.jrl1004.plugins.magic.particles.ParticleEffect;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

@SuppressWarnings("deprecation")
public class AbilitySmoking extends AbstractAbility {

	public AbilitySmoking() {
		super("Smoking");
		setPerm("magic.smoking");
	}

	@Override
	public void castSpell(Player player) {
		if(!canUse(player)) {
			ChatManager.messageBad(player, "You are not capable of performing this spell.");
			return;
		}
		ParticleEffect.SMOKE_LARGE.display(new Vector(0, 0, 0), 1, player.getLocation(), 48d);
		Location block = player.getTargetBlock((HashSet<Byte>) null, 25).getLocation();
		player.teleport(block.getBlock().getType().isSolid() ? block.add(0.5, 1.5, 0.5) : block); //May put players in a wall... oops
		ParticleEffect.SMOKE_LARGE.display(new Vector(0, 0, 0), 1, player.getLocation(), 48d);
		player.setFallDistance(0f); // Turn off teleport falling damage
	}
}
