package me.jrl1004.plugins.magic.managers;

import java.util.HashMap;

import me.jrl1004.plugins.magic.abilities.AbilityFireball;
import me.jrl1004.plugins.magic.abilities.AbilityHealing;
import me.jrl1004.plugins.magic.abilities.AbilityHeartripping;
import me.jrl1004.plugins.magic.abilities.AbilityNothing;
import me.jrl1004.plugins.magic.abilities.AbilityPotioncraft;
import me.jrl1004.plugins.magic.abilities.AbilitySmoking;
import me.jrl1004.plugins.magic.abilities.AbilityTelekenesis;
import me.jrl1004.plugins.magic.abilities.AbilityTeleport;
import me.jrl1004.plugins.magic.abilities.AbstractAbility;

import org.bukkit.OfflinePlayer;

public class AbilityManager {

	private static AbilityManager manager;
	private AbstractAbility[] spells;
	private HashMap<String, Integer> spellSelection;
	private HashMap<String, Long> switchDelay;

	private AbilityManager() {
		//TODO: Add all the spells
		spells = new AbstractAbility[] { new AbilityNothing(), new AbilitySmoking(), new AbilityTeleport(), new AbilityFireball(), new AbilityTelekenesis(), new AbilityHealing(), new AbilityHeartripping(), new AbilityPotioncraft() };
		spellSelection = new HashMap<String, Integer>();
		this.switchDelay = new HashMap<String, Long>();
	}

	// #StaticAbuse
	public static AbilityManager getInstance() {
		if (manager == null) manager = new AbilityManager();
		return manager;
	}

	private boolean canSwitch(OfflinePlayer player) {
		String name = player.getUniqueId().toString();
		long currentTime = System.nanoTime();
		if (!switchDelay.containsKey(name)) {
			switchDelay.put(name, currentTime);
			return true;
		}
		return currentTime - switchDelay.get(name) >= 50;
	}

	public void setSpellInt(OfflinePlayer player, int value) {
		if (!canSwitch(player)) return;
		while (value < 0)
			value += spells.length;
		if (value >= spells.length) value %= spells.length;
		String name = player.getUniqueId().toString();
		spellSelection.put(name, value);
	}

	public int getSpellInt(OfflinePlayer player) {
		String name = player.getUniqueId().toString();
		if (!spellSelection.containsKey(name)) setSpellInt(player, 0);
		return spellSelection.get(name);
	}

	public AbstractAbility getCurrentSpell(OfflinePlayer player) {
		return spells[getSpellInt(player)]; // Just as a failsafe in case I use too big of a number for the array
	}

	public void increaseSpellValue(OfflinePlayer player) {
		setSpellInt(player, getSpellInt(player) + 1);
	}

	public void decreaseSpellValue(OfflinePlayer player) {
		setSpellInt(player, getSpellInt(player) - 1);
	}

}
