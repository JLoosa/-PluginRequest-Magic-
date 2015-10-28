package me.jrl1004.plugins.magic.abilities;

import me.jrl1004.plugins.magic.managers.ChatManager;

import org.bukkit.entity.Player;

public class AbilityNothing extends AbstractAbility {

	public AbilityNothing() {
		super("An Abundance of Nothing! :D");
	}

	@Override
	public void castSpell(Player player) {
		if(!canUse(player)) {
			ChatManager.messageBad(player, "You are somehow incapable of nothing... which is like being incapable of everything");
			return;
		}
		player.sendMessage("Through the force of sheer willpower, you manage to do nothing at all.");
	}

}
