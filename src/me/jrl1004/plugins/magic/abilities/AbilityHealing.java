package me.jrl1004.plugins.magic.abilities;

import me.jrl1004.plugins.magic.managers.ChatManager;
import me.jrl1004.plugins.magic.particles.ParticleEffect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AbilityHealing extends AbstractAbility {

	public AbilityHealing() {
		super(ChatColor.LIGHT_PURPLE + "Healing");
		setPerm("magic.healing");
	}

	@Override
	public void castSpell(Player player) {
		if(!canUse(player)) {
			ChatManager.messageBad(player, "You are not capable of performing this spell.");
			return;
		}
		ParticleEffect.HEART.display(new Vector (0, 0, 0), 1, player.getEyeLocation().add(0, 0.5, 0), Bukkit.getOnlinePlayers());
		player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + 6)); // 6 = 3 * 2 half-hearts
	}

}
