package de.pentamuria.system.events;

import de.pentamuria.gilde.countdowns.BaseCountdown;
import de.pentamuria.system.countdowns.FightCountdown;
import de.pentamuria.system.countdowns.HomeCountdown;
import de.pentamuria.system.countdowns.SpawnCountdown;
import de.pentamuria.system.countdowns.TPACountdown;
import de.pentamuria.system.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    private Main plugin;

    public MoveListener(Main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if(e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockY() != e.getTo().getBlockY() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
            HomeCountdown.stop(p);
            SpawnCountdown.stop(p);
            TPACountdown.stop(p);
            BaseCountdown.stop(p);
        }
    }

    @EventHandler
    public void onCommandProcess(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if(plugin.inFight.contains(p)) {
            if(!FightCountdown.countdown.containsKey(p)) {
                plugin.inFight.remove(p);
            } else {
                e.setCancelled(true);
                p.sendMessage("§4[Fehler] §7Du darfst im Kampf §ckeine Befehle benutzen!");
            }
        }
    }
}
