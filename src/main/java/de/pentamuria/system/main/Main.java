package de.pentamuria.system.main;

import de.pentamuria.system.commands.COMMAND_bag;
import de.pentamuria.system.events.InventoryCloseListener;
import de.pentamuria.system.manager.BagManager;
import de.pentamuria.system.utils.SyncTimer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public String pr = "§5Pentamuria §8- §7";
    String prefix = "§5Pentamuria §8- §7";

    // Objects
    public BagManager bagManager;
    public SyncTimer syncTimer;

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadCommands();
        loadEvents();
        loadManager();


        Bukkit.getConsoleSender().sendMessage(prefix + "Dieses Plugin wurde §agestartet");

        // Start Synchro
        syncTimer.start();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        // Save all necessary data
        bagManager.savePlayerBags();

        Bukkit.getConsoleSender().sendMessage(pr + "Dieses Plugin wurde §4deaktiviert");
    }

    private void loadCommands() {
        // Load all Command Classes
        COMMAND_bag cCOMMAND_bag = new COMMAND_bag(this);
        getCommand("bag").setExecutor(cCOMMAND_bag);

    }

    private void loadEvents() {
        // Load all Event Classes
        new InventoryCloseListener(this);
    }

    private void loadManager() {
        // Load all Manager Classes
        bagManager = new BagManager(this);
        syncTimer = new SyncTimer(this);
    }
}
