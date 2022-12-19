package de.pentamuria.system.events;

import de.pentamuria.system.main.Main;
import de.pentamuria.system.utils.ActionBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {
    private final Main plugin;

    public InventoryCloseListener(Main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();

        if(e.getView().getTitle().equalsIgnoreCase("§aDein Rucksack")) {
            //plugin.bagManager.savePlayerBag(p.getUniqueId().toString(), invN);
            ActionBar.sendActionBar(p, "§aRucksack gespeichert §7- §b/bag upload");
        }

    }
}
