package de.pentamuria.system.events;

import de.pentamuria.system.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    private final Main plugin;

    public InventoryClickListener(Main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getView().getTitle().equalsIgnoreCase("§aHilfe")) {
            e.setCancelled(true);
        } else if(e.getView().getTitle().equalsIgnoreCase("§cRegeln")) {
            e.setCancelled(true);
        }
    }

}
