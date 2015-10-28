package me.jrl1004.plugins.magic.abilities;

import me.jrl1004.plugins.magic.managers.ChatManager;
import me.jrl1004.plugins.magic.managers.TeleportationManager;

import org.bukkit.entity.Player;

public class AbilityTeleport extends AbstractAbility {

	public AbilityTeleport() {
		super("Teleportation");
		setPerm("magic.teleportation");
	}

	@Override
	public void castSpell(Player player) {
		if(!canUse(player)) {
			ChatManager.messageBad(player, "You are not capable of performing this spell.");
			return;
		}
		TeleportationManager.getInstance().executeTeleport(player);
	}

}
