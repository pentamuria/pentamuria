package de.pentamuria.system.events;

import de.pentamuria.gilde.gildensystem.GildenSystem;
import de.pentamuria.system.main.Main;
import de.pentamuria.system.scoreboard.CustomPlayerScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Random;

public class JoinListener implements Listener {
    private final Main plugin;
    private CustomPlayerScoreboard playerScoreboard;

    public JoinListener(Main main, CustomPlayerScoreboard playerScoreboard) {
        this.plugin = main;
        this.playerScoreboard = playerScoreboard;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        int online = Bukkit.getOnlinePlayers().size();
        playerScoreboard.getCustomScoreboard(e.getPlayer())
                .setSidebarScore(7, "§7↣ " + GildenSystem.gildenSystem.gildenManager.getPlayerGildeWithColor(e.getPlayer()));

        playerScoreboard.getCustomScoreboard(e.getPlayer())
                .setSidebarScore(4, "§7↣ §c" + plugin.statsAPI.stats.getPlayerStats(e.getPlayer().getUniqueId().toString()).getDeaths());

        for(Player all : Bukkit.getOnlinePlayers()) {
            playerScoreboard.getCustomScoreboard(all)
                    .setSidebarScore(1, "§7↣ §d" + online);
        }
        e.getPlayer().setPlayerListHeader("§7Willkommen bei §5Pentamuria!\n");
        if(GildenSystem.gildenSystem.gildenManager.getPlayerGilde(e.getPlayer().getUniqueId().toString()).equalsIgnoreCase("Solo") || GildenSystem.gildenSystem.gildenManager.getPlayerGilde(e.getPlayer().getUniqueId().toString()).equalsIgnoreCase("Keine")) {
            e.getPlayer().setPlayerListName("§a" + e.getPlayer().getName() + " §8| §c" + plugin.statsAPI.stats.getPlayerStats(e.getPlayer().getUniqueId().toString()).getDeaths() + " Tode");
        } else {
            e.getPlayer().setPlayerListName("§c"+GildenSystem.gildenSystem.gildenManager.getPlayerGildeWithColor(e.getPlayer())+" §8| "+ GildenSystem.gildenSystem.gildenManager.getPlayerColor(e.getPlayer()) + e.getPlayer().getName() + " §8| §c" + plugin.statsAPI.stats.getPlayerStats(e.getPlayer().getUniqueId().toString()).getDeaths() + " Tode");
        }
        e.getPlayer().setPlayerListFooter("\n§a/help §8-> §7Alles was du wissen musst!\n\n§aViel Spaß, §b"+e.getPlayer().getName()+" §4♥");

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {

        int online = Bukkit.getOnlinePlayers().size();
        for(Player all : Bukkit.getOnlinePlayers()) {
            if(!all.getName().equalsIgnoreCase(e.getPlayer().getName())) {
                playerScoreboard.getCustomScoreboard(all)
                        .setSidebarScore(1, "§7↣ §d" + (online-1));
            }
        }
        playerScoreboard.remove(e.getPlayer());

        // Check if player is in fight
        Player p = e.getPlayer();
        if(plugin.inFight.contains(p)) {
            if(plugin.lastDmg.containsKey(p)) {
                // Kill Player and configure stats
                p.setHealth(0.0);
            }
        }

    }
}
