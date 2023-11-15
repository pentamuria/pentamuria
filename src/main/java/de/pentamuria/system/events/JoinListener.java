package de.pentamuria.system.events;

import de.pentamuria.gilde.gildensystem.GildenSystem;
import de.pentamuria.system.main.Main;
import org.bukkit.Bukkit;
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
        int online = Bukkit.getOnlinePlayers().size();
        plugin.scoreboardAPI.getPlayerScoreboard().updateGilde(e.getPlayer(), GildenSystem.gildenSystem.gildenManager.getPlayerGildeWithColor(e.getPlayer()));

        plugin.scoreboardAPI.getPlayerScoreboard().updateDeaths(e.getPlayer(), plugin.statsAPI.stats.getPlayerStats(e.getPlayer().getUniqueId().toString()).getDeaths());

        plugin.scoreboardAPI.getPlayerScoreboard().updateEvent(e.getPlayer(), plugin.eventAPI.eventManager.getEventType().getTitle());

        for(Player all : Bukkit.getOnlinePlayers()) {
            plugin.scoreboardAPI.getPlayerScoreboard().updatePlayers(all, online);
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
                plugin.scoreboardAPI.getPlayerScoreboard().updatePlayers(all, (online-1));

            }
        }
        plugin.scoreboardAPI.getPlayerScoreboard().remove(e.getPlayer());

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
