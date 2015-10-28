package me.jrl1004.plugins.magic.listeners;

import me.jrl1004.plugins.magic.abilities.AbstractAbility;
import me.jrl1004.plugins.magic.managers.AbilityManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpellCastListener implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		if (event.getPlayer().getInventory().getHeldItemSlot() != 8) return;
		AbstractAbility activeSpell = AbilityManager.getInstance().getCurrentSpell(event.getPlayer());
		activeSpell.castSpell(event.getPlayer());
	}

}
