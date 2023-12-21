package de.pentamuria.system.events;

import de.pentamuria.gilde.gildensystem.GildenSystem;
import de.pentamuria.scoreboard.utils.CustomScoreboard;
import de.pentamuria.system.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Random;

public class JoinListener implements Listener {
    private final Main plugin;

    public JoinListener(Main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        int online = Bukkit.getOnlinePlayers().size();
        plugin.scoreboardAPI.getPlayerScoreboard().updateGilde(p, GildenSystem.gildenSystem.gildenManager.getPlayerGildeWithColor(p));

        plugin.scoreboardAPI.getPlayerScoreboard().updateDeaths(p, plugin.statsAPI.stats.getPlayerStats(p.getUniqueId().toString()).getDeaths());

        plugin.scoreboardAPI.getPlayerScoreboard().updateEvent(p, plugin.eventAPI.eventManager.getEventType().getTitle());

        String colorCode = plugin.gildenSystem.gildenManager.hasGilde(p) ? plugin.gildenSystem.gildenManager.getGilde(plugin.gildenSystem.gildenManager.getPlayerGilde(p.getUniqueId().toString())).getFarbe() : "&c"; // Your color code
        char code = colorCode.charAt(1); // Extract the color code character after the "&"
        ChatColor chatColor = ChatColor.getByChar(code);
        String kuerzel = plugin.gildenSystem.gildenManager.hasGilde(p) ? plugin.gildenSystem.gildenManager.getGilde(plugin.gildenSystem.gildenManager.getPlayerGilde(p.getUniqueId().toString())).getKuerzel() + " " : "";

        for(Player all : Bukkit.getOnlinePlayers()) {
            // Update Player Count
            plugin.scoreboardAPI.getPlayerScoreboard().updatePlayers(all, online);
            // Add joined player to everyones Scoreboard
            plugin.scoreboardAPI.getPlayerScoreboard().getCustomScoreboard(all).addPlayer(
                    p,
                    plugin.gildenSystem.gildenManager.getPlayerGilde(p.getUniqueId().toString()),
                    kuerzel,
                    " §c" + plugin.statsAPI.stats.getPlayerStats(p.getUniqueId().toString()).getDeaths(),
                    chatColor);
            // Add every online player to joined Scoreboard
            String allColorCode = plugin.gildenSystem.gildenManager.hasGilde(all) ? plugin.gildenSystem.gildenManager.getGilde(plugin.gildenSystem.gildenManager.getPlayerGilde(all.getUniqueId().toString())).getFarbe() : "&c"; // Your color code
            ChatColor allChatColor = ChatColor.getByChar(allColorCode.charAt(1));
            String allKuerzel = plugin.gildenSystem.gildenManager.hasGilde(all) ? plugin.gildenSystem.gildenManager.getGilde(plugin.gildenSystem.gildenManager.getPlayerGilde(all.getUniqueId().toString())).getKuerzel() + " " : "";
            plugin.scoreboardAPI.getPlayerScoreboard().getCustomScoreboard(p).addPlayer(
                    all,
                    plugin.gildenSystem.gildenManager.getPlayerGilde(all.getUniqueId().toString()),
                    allKuerzel,
                    " §c" + plugin.statsAPI.stats.getPlayerStats(all.getUniqueId().toString()).getDeaths(),
                    allChatColor);
        }
        p.setPlayerListHeader("§7Willkommen bei §5Pentamuria!\n");
        if(GildenSystem.gildenSystem.gildenManager.getPlayerGilde(p.getUniqueId().toString()).equalsIgnoreCase("Solo") || GildenSystem.gildenSystem.gildenManager.getPlayerGilde(p.getUniqueId().toString()).equalsIgnoreCase("Keine")) {
            p.setPlayerListName("§a" + p.getName() + " §8| §c" + plugin.statsAPI.stats.getPlayerStats(p.getUniqueId().toString()).getDeaths() + " Tode");
        } else {
            p.setPlayerListName("§c"+GildenSystem.gildenSystem.gildenManager.getPlayerGildeWithColor(p)+" §8| "+ GildenSystem.gildenSystem.gildenManager.getPlayerColor(p) + p.getName() + " §8| §c" + plugin.statsAPI.stats.getPlayerStats(p.getUniqueId().toString()).getDeaths() + " Tode");
        }
        p.setPlayerListFooter("\n§a/help §8-> §7Alles was du wissen musst!\n\n§aViel Spaß, §b"+p.getName()+" §4♥");

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {

        int online = Bukkit.getOnlinePlayers().size();
        for(Player all : Bukkit.getOnlinePlayers()) {
            if(!all.getName().equalsIgnoreCase(all.getName())) {
                plugin.scoreboardAPI.getPlayerScoreboard().updatePlayers(all, (online-1));
                plugin.scoreboardAPI.getPlayerScoreboard().getCustomScoreboard(all).removePlayer(e.getPlayer());

            }
        }
        // Check if player is in fight
        Player p = e.getPlayer();
        plugin.scoreboardAPI.getPlayerScoreboard().remove(p);

        if(plugin.inFight.contains(p)) {
            if(plugin.lastDmg.containsKey(p)) {
                // Kill Player and configure stats
                p.setHealth(0.0);
            }
        }

    }
}
