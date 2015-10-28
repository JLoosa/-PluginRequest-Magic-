package me.jrl1004.plugins.magic.abilities;

import me.jrl1004.plugins.magic.managers.ChatManager;
import me.jrl1004.plugins.magic.managers.TelekenesisManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class AbilityTelekenesis extends AbstractAbility {

	public AbilityTelekenesis() {
		super("Telekenesis", customStack(Material.BOOK, ChatColor.GOLD + "Telekenesis", "Click on a player or block to target them", "Click again to lob your target through the air"));
		setPerm("magic.telekenesis");
	}

	@Override
	public void castSpell(Player player) {
		if (!canUse(player)) {
			ChatManager.messageBad(player, "You are not capable of performing this spell.");
			return;
		}
		TelekenesisManager.getInstance().useTheForce(player); // Hue Hue Hue - Use the force
	}

}
