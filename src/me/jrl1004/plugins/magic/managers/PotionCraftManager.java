package me.jrl1004.plugins.magic.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class PotionCraftManager implements Listener {

	private static PotionCraftManager manager;
	private Inventory potionInventory;

	private PotionCraftManager() {
		updateInventory();
	}

	@SuppressWarnings("deprecation")
	private void updateInventory() {
		int newValue = toNextMod9((int) (4 * (PotionType.values().length - 1)));
		if (potionInventory != null) potionInventory.clear();
		potionInventory = Bukkit.createInventory(null, newValue, ChatColor.AQUA + "PotionCrafting");
		for (PotionType pt : PotionType.values()) {
			if (pt == PotionType.WATER || pt.toString().equalsIgnoreCase("WATER")) continue;
			for (int b = 0; b < 2; b++)
				for (int i = 0; i < 2; i++) {
					Potion potion = new Potion(pt, i + 1, b == 1, true);
					potionInventory.addItem(potion.toItemStack(1));
				}
		}
	}

	private int toNextMod9(int input) {
		while (input % 9 != 0)
			input++;
		return input;
	}

	// #StaticAbuse
	public static PotionCraftManager getInstance() {
		if (manager == null) manager = new PotionCraftManager();
		return manager;
	}

	public void openPotionList(Player player) {
		updateInventory();
		player.openInventory(potionInventory);
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getInventory().hashCode() != potionInventory.hashCode()) return;
		ItemStack clicked = event.getCurrentItem();
		if (clicked == null || clicked.getType() != Material.POTION) clicked = event.getCursor();
		if (clicked == null || clicked.getType() != Material.POTION) return;
		event.setCancelled(true);
		event.getWhoClicked().getInventory().addItem(clicked.clone());
	}
}
