package me.jrl1004.plugins.magic.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import me.jrl1004.plugins.magic.managers.AbilityManager;

public class HotbarScrollListener implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void onScroll(PlayerItemHeldEvent event) {
		if (!event.getPlayer().isSneaking())
			return;
		int checkSlot = event.getPreviousSlot();
		if (checkSlot != 8)
			return;
		// Hotbar slots are 0 - 8; therefore, slot 9 is actually slot 8
		if (event.getNewSlot() == 0) {
			AbilityManager.getInstance().decreaseSpellValue(event.getPlayer());
			event.getPlayer().getInventory()
					.setItemInMainHand(AbilityManager.getInstance().getCurrentSpell(event.getPlayer()).getItem());
		} else {
			AbilityManager.getInstance().increaseSpellValue(event.getPlayer());
			event.getPlayer().getInventory()
					.setItemInMainHand(AbilityManager.getInstance().getCurrentSpell(event.getPlayer()).getItem());
		}
		event.getPlayer().getInventory().setHeldItemSlot(8); // Reset the hotbar
																// slot back
	}

}
