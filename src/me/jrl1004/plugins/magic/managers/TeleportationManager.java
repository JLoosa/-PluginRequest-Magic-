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
import org.bukkit.inventory.meta.SkullMeta;

public class TeleportationManager implements Listener {

	private static TeleportationManager manager;
	private Inventory teleportInventory;
	private int lastPlayerCount = 0;

	private TeleportationManager() {
		updateInventory();
	}

	private void updateInventory() {
		int newValue = toNextMod9(Math.max(Bukkit.getOnlinePlayers().size(), 1));
		if (lastPlayerCount == newValue) { return; }

		if (teleportInventory != null) teleportInventory.clear();
		teleportInventory = null;

		teleportInventory = Bukkit.createInventory(null, newValue, ChatColor.AQUA + "Teleporting to...");
		for (Player player : Bukkit.getOnlinePlayers()) {
			ItemStack skull = new ItemStack(Material.SKULL_ITEM);
			SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
			skullMeta.setOwner(player.getName());
			skullMeta.setDisplayName((player.hasPermission("teleport.block") ? ChatColor.RED : ChatColor.GREEN) + player.getName()); // If you can't teleport to them, their name is red
			skull.setItemMeta(skullMeta);
			teleportInventory.addItem(skull);
		}
	}

	private int toNextMod9(int input) {
		while (input % 9 != 0)
			input++;
		return input;
	}

	// #StaticAbuse
	public static TeleportationManager getInstance() {
		if (manager == null) manager = new TeleportationManager();
		return manager;
	}

	public void executeTeleport(Player player) {
		if (!AbilityManager.getInstance().getCurrentSpell(player).canUse(player)) return;
		updateInventory();
		player.openInventory(teleportInventory);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getInventory().hashCode() != teleportInventory.hashCode()) return;
		ItemStack clicked = event.getCurrentItem();
		if (clicked == null || clicked.getType() == Material.AIR) clicked = event.getCursor();
		if (clicked == null || clicked.getType() == Material.AIR) return;
		event.setCancelled(true);
		try {
			String name = ((SkullMeta) clicked.getItemMeta()).getOwner();
			Player player = Bukkit.getPlayer(name);
			if (!player.hasPermission("teleport.block")) event.getWhoClicked().teleport(player);
			else ChatManager.messageBad((Player) event.getWhoClicked(), "That player is too powerful!");
		} catch (Exception exc) {
			System.out.println("Teleportation Error > " + exc.getMessage());
		}
	}
}
