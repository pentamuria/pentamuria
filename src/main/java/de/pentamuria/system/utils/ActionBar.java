package de.pentamuria.system.utils;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;


public class ActionBar {
	
	
	public static void sendActionBar(final Player player, final String message) {
		
		player.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR,new TextComponent(message));
	}
	
	

}
