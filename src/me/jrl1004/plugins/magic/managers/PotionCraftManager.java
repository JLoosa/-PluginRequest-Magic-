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
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class PotionCraftManager implements Listener {

	private static PotionCraftManager manager;
	private Inventory potionInventory;

	private PotionCraftManager() {
		updateInventory();
	}

	private void updateInventory() {
		int inventorySize = PotionType.values().length;
		inventorySize += 9 - (inventorySize % 9);
		if (potionInventory != null)
			potionInventory.clear();
		potionInventory = Bukkit.createInventory(null, inventorySize, ChatColor.AQUA + "PotionCrafting");
		for (PotionType potionType : PotionType.values()) {
			ItemStack stack = new ItemStack(Material.POTION);
			PotionMeta meta = (PotionMeta) stack.getItemMeta();
			meta.clearCustomEffects();
			PotionData data = new PotionData(potionType);
			meta.setBasePotionData(data);
			stack.setItemMeta(meta);
			potionInventory.addItem(stack);
		}
	}

	public static PotionCraftManager getInstance() {
		if (manager == null)
			manager = new PotionCraftManager();
		return manager;
	}

	public void openPotionList(Player player) {
		updateInventory();
		player.openInventory(potionInventory);
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getInventory().hashCode() != potionInventory.hashCode())
			return;
		ItemStack clicked = event.getCurrentItem();
		if (clicked == null || clicked.getType() != Material.POTION)
			clicked = event.getCursor();
		if (clicked == null || clicked.getType() != Material.POTION)
			return;
		event.setCancelled(true);
		event.getWhoClicked().getInventory().addItem(clicked.clone());
	}
}
