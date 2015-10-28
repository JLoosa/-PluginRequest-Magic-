package me.jrl1004.plugins.magic.abilities;

import me.jrl1004.plugins.magic.managers.ChatManager;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

public class AbilityFireball extends AbstractAbility {

	public AbilityFireball() {
		super(ChatColor.RED + "Fireball!");
		setPerm("magic.fireball");
	}

	@Override
	public void castSpell(Player player) {
		if(!canUse(player)) {
			ChatManager.messageBad(player, "You are not capable of performing this spell.");
			return;
		}
		Location fSpawn = player.getEyeLocation().add(player.getLocation().getDirection());
		Fireball fireball = (Fireball) player.getWorld().spawnEntity(fSpawn, EntityType.FIREBALL);
		fireball.setVelocity(player.getLocation().getDirection());
	}

}
