package me.jrl1004.plugins.magic.abilities;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permissible;

public abstract class AbstractAbility {

	private String permission = "";
	private final String name;

	// Required to show the name of the spell above the hotbar
	private final ItemStack castItem;

	public AbstractAbility(String name) {
		this(name, customStack(Material.BOOK, ChatColor.GOLD + name));
		// If no item is supplied, it will default to a book
	}

	public AbstractAbility(String name, ItemStack item) {
		this.name = name;
		this.castItem = item;
	}

	public abstract void castSpell(Player player);

	public String getName() {
		return name;
	}

	public ItemStack getItem() {
		return castItem.clone();
	}

	protected static ItemStack customStack(Material material, String name, String... lore) {
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(name);
		if (lore.length > 0) itemMeta.setLore(Arrays.asList(lore));
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	protected void setPerm(String s) {
		this.permission = s;
	}

	public boolean canUse(Permissible p) {
		return p.hasPermission(permission);
	}
}
