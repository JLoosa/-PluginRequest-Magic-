package me.jrl1004.plugins.magic.abilities;

import me.jrl1004.plugins.magic.managers.ChatManager;
import me.jrl1004.plugins.magic.managers.HeartrippingManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class AbilityHeartripping extends AbstractAbility {

	public AbilityHeartripping() {
		super("Heartripping", customStack(Material.BOOK, ChatColor.RED + "HeartRipping", ChatColor.GOLD + "Currently bound to:", "[Nobody]"));
		setPerm("magic.heartripping");
	}

	@Override
	public void castSpell(Player player) {
		if(!canUse(player)) {
			ChatManager.messageBad(player, "You are not capable of performing this spell.");
			return;
		}
		HeartrippingManager.getInstance().damageOther(player);
	}
}
