package de.pentamuria.system.events;

import de.pentamuria.gilde.gildensystem.GildenSystem;
import de.pentamuria.system.countdowns.FightCountdown;
import de.pentamuria.system.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    private final Main plugin;

    public DeathListener(Main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        e.setDeathMessage(null);
        // Add Death to Stats
        plugin.statsAPI.stats.getPlayerStats(p.getUniqueId().toString()).addDeath();

        String gilde =  plugin.gildenSystem.gildenManager.hasGilde(p) ? plugin.gildenSystem.gildenManager.getPlayerGildeWithColor(p) : "§cKeine";
        String playerColor = plugin.gildenSystem.gildenManager.hasGilde(p) ? plugin.gildenSystem.gildenManager.getPlayerColor(p) : "§c";
        if(plugin.inFight.contains(p)) {
            if(plugin.lastDmg.containsKey(p)) {
                Player killer = plugin.lastDmg.get(p);
                plugin.statsAPI.stats.getPlayerStats(killer.getUniqueId().toString()).addKill();
                String killerGilde =
                        plugin.gildenSystem.gildenManager.hasGilde(killer) ? plugin.gildenSystem.gildenManager.getPlayerGildeWithColor(killer) : "§cKeine";
                String killerColor =
                        plugin.gildenSystem.gildenManager.hasGilde(killer) ? plugin.gildenSystem.gildenManager.getPlayerColor(killer): "§c";
                Bukkit.broadcastMessage("§7Der Spieler " + gilde + " §8| " + playerColor + p.getName() + " §7wurde von " +
                        killerGilde + " §8| " + killerColor + killer.getName() + " §cgetötet");
                if(plugin.inFight.contains(killer))FightCountdown.stop(killer, plugin);
            } else {
                Bukkit.broadcastMessage("§7Der Spieler " + gilde + " §8| " + playerColor + p.getName() + " §7ist §cgestorben");
            }
            FightCountdown.stop(p, plugin);
        } else {
            Bukkit.broadcastMessage("§7Der Spieler " + gilde + " §8| " + playerColor + p.getName() + " §7ist §cgestorben");
        }

        // Update Scoreboards
        plugin.scoreboardAPI.getPlayerScoreboard().updateDeaths(e.getPlayer(), plugin.statsAPI.stats.getPlayerStats(e.getPlayer().getUniqueId().toString()).getDeaths());

        if(GildenSystem.gildenSystem.gildenManager.getPlayerGilde(e.getPlayer().getUniqueId().toString()).equalsIgnoreCase("Solo") || GildenSystem.gildenSystem.gildenManager.getPlayerGilde(e.getPlayer().getUniqueId().toString()).equalsIgnoreCase("Keine")) {
            e.getPlayer().setPlayerListName("§a" + e.getPlayer().getName() + " §8| §c" + plugin.statsAPI.stats.getPlayerStats(e.getPlayer().getUniqueId().toString()).getDeaths() + " Tode");
        } else {
            e.getPlayer().setPlayerListName("§c"+GildenSystem.gildenSystem.gildenManager.getPlayerGildeWithColor(e.getPlayer())+" §8| "+ GildenSystem.gildenSystem.gildenManager.getPlayerColor(e.getPlayer()) + e.getPlayer().getName() + " §8| §c" + plugin.statsAPI.stats.getPlayerStats(e.getPlayer().getUniqueId().toString()).getDeaths() + " Tode");
        }

    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if(!e.getEntityType().equals(EntityType.PLAYER)) {
            if(e.getEntity().getKiller()!=null) {
                Player killer = e.getEntity().getKiller();
                plugin.statsAPI.stats.getPlayerStats(killer.getUniqueId().toString()).addMobKill();
            }
        }
    }
}
