package de.pentamuria.system.events;

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
        Random random = new Random();
        int max = 20;
        int min = 1;

        int result = random.nextInt(max + 1 - min) + min;
        int online = Bukkit.getOnlinePlayers().size();
        playerScoreboard.getCustomScoreboard(e.getPlayer())
                .setSidebarScore(7, "§7↣ §b" + e.getPlayer().getName());

        playerScoreboard.getCustomScoreboard(e.getPlayer())
                .setSidebarScore(4, "§7↣ §c" + result);

        for(Player all : Bukkit.getOnlinePlayers()) {
            playerScoreboard.getCustomScoreboard(all)
                    .setSidebarScore(1, "§7↣ §d" + online);
        }

        e.getPlayer().setPlayerListHeader("§7Willkommen bei §5Pentamuria!\n");
        e.getPlayer().setPlayerListName("§cSolo §8| §c" + e.getPlayer().getName() + " §8| §c" + result + " Tode");
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
    }
}
