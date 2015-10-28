package me.jrl1004.plugins.magic.abilities;

import me.jrl1004.plugins.magic.managers.ChatManager;
import me.jrl1004.plugins.magic.managers.PotionCraftManager;

import org.bukkit.entity.Player;

public class AbilityPotioncraft extends AbstractAbility {

	public AbilityPotioncraft() {
		super("Potioncraft");
		setPerm("magic.potioncraft");
	}

	@Override
	public void castSpell(Player player) {
		if(!canUse(player)) {
			ChatManager.messageBad(player, "You are not capable of performing this spell.");
			return;
		}
		PotionCraftManager.getInstance().openPotionList(player);
	}

}
