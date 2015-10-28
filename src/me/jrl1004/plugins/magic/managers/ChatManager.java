package me.jrl1004.plugins.magic.managers;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatManager {

	private static final String _PREFIX_ = ChatColor.GOLD + "[" + ChatColor.AQUA + "Magic!" + ChatColor.GOLD + "] " + ChatColor.RESET;

	private ChatManager() {
	}

	public enum MessageType {
		GOOD(ChatColor.GREEN), BAD(ChatColor.RED), MAIN(ChatColor.AQUA);

		private final ChatColor color;

		private MessageType(ChatColor color) {
			this.color = color;
		}

		public ChatColor getColor() {
			return color;
		}
	}

	public static void message(CommandSender cs, MessageType type, String... messages) {
		if (cs == null) return;
		if (messages.length == 0) return;
		for (String string : messages) {
			cs.sendMessage(_PREFIX_ + type.getColor() + string);
		}
	}

	public static void messageGood(CommandSender cs, String... messages) {
		message(cs, MessageType.GOOD, messages);
	}

	public static void messageBad(CommandSender cs, String... messages) {
		message(cs, MessageType.BAD, messages);
	}

	public static void messageMain(CommandSender cs, String... messages) {
		message(cs, MessageType.MAIN, messages);
	}

}
