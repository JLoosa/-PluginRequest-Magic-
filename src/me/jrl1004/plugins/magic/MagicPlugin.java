package me.jrl1004.plugins.magic;

import me.jrl1004.plugins.magic.listeners.HotbarScrollListener;
import me.jrl1004.plugins.magic.listeners.SpellCastListener;
import me.jrl1004.plugins.magic.managers.HeartrippingManager;
import me.jrl1004.plugins.magic.managers.PotionCraftManager;
import me.jrl1004.plugins.magic.managers.TelekenesisManager;
import me.jrl1004.plugins.magic.managers.TeleportationManager;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MagicPlugin extends JavaPlugin {

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
		super.onDisable();
	}

	@Override
	public void onEnable() {
		getLogger().info("It's time for some magic!!");
		Bukkit.getPluginManager().registerEvents(new HotbarScrollListener(), this);
		Bukkit.getPluginManager().registerEvents(new SpellCastListener(), this);
		Bukkit.getPluginManager().registerEvents(TeleportationManager.getInstance(), this);
		Bukkit.getPluginManager().registerEvents(TelekenesisManager.getInstance(), this);
		Bukkit.getPluginManager().registerEvents(PotionCraftManager.getInstance(), this);
		Bukkit.getPluginManager().registerEvents(HeartrippingManager.getInstance(), this);
		super.onEnable();
	}

	public static MagicPlugin getMagic() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Magic");
		if (plugin == null || !plugin.isEnabled() || plugin instanceof MagicPlugin == false) return null;
		return (MagicPlugin) plugin;
	}

}
