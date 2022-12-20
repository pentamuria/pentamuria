package de.pentamuria.system.events;

import de.pentamuria.system.main.Main;
import de.pentamuria.system.utils.ActionBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    private final Main plugin;

    public BlockListener(Main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if(e.getBlock().getLocation().getWorld().getName().equalsIgnoreCase(plugin.locationManager.getSpawn().getWorld().getName())) {
            if(e.getBlock().getLocation().distance(plugin.locationManager.getSpawn()) < plugin.locationManager.getRadius()) {
                e.setCancelled(true);
                int zahl = (int)e.getBlock().getLocation().distance(plugin.locationManager.getSpawn());
                int minus = plugin.locationManager.getRadius()-zahl;
                ActionBar.sendActionBar(p, "§cHier kannst du nichts abbauen §8| §bNoch §6" + minus + " §bBlöcke");
            }
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if(e.getBlock().getLocation().getWorld().getName().equalsIgnoreCase(plugin.locationManager.getSpawn().getWorld().getName())) {
            if(e.getBlock().getLocation().distance(plugin.locationManager.getSpawn()) < plugin.locationManager.getRadius()) {
                e.setCancelled(true);
                int zahl = (int)e.getBlock().getLocation().distance(plugin.locationManager.getSpawn());
                int minus = plugin.locationManager.getRadius()-zahl;
                ActionBar.sendActionBar(p, "§cHier kannst du nichts hinbauen §8| §bNoch §6" + minus + " §bBlöcke");
            }
        }
    }
}
