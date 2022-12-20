package de.pentamuria.system.main;

import de.pentamuria.system.commands.*;
import de.pentamuria.system.events.*;
import de.pentamuria.system.manager.BagManager;
import de.pentamuria.system.manager.HomeManager;
import de.pentamuria.system.manager.LocationManager;
import de.pentamuria.system.utils.SyncTimer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class Main extends JavaPlugin {

    public String pr = "§5Pentamuria §8- §7";
    String prefix = "§5Pentamuria §8- §7";

    // Objects
    public BagManager bagManager;
    public SyncTimer syncTimer;
    public LocationManager locationManager;
    public HomeManager homeManager;

    // Listen
    public ArrayList<Player> inFight = new ArrayList<Player>();
    public HashMap<Player, Player> lastDmg = new HashMap<Player, Player>();

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

        COMMAND_spawn cCOMMAND_spawn = new COMMAND_spawn(this);
        getCommand("spawn").setExecutor(cCOMMAND_spawn);

        COMMAND_tpa cCOMMAND_tpa = new COMMAND_tpa(this);
        getCommand("tpa").setExecutor(cCOMMAND_tpa);

        COMMAND_home cCOMMAND_home = new COMMAND_home(this);
        getCommand("home").setExecutor(cCOMMAND_home);

        COMMAND_hilfe cCOMMAND_hilfe = new COMMAND_hilfe(this);
        getCommand("hilfe").setExecutor(cCOMMAND_hilfe);

    }

    private void loadEvents() {
        // Load all Event Classes
        new InventoryCloseListener(this);
        new DamageListener(this);
        new MoveListener(this);
        new BlockListener(this);
        new InventoryClickListener(this);
    }

    private void loadManager() {
        // Load all Manager Classes
        bagManager = new BagManager(this);
        syncTimer = new SyncTimer(this);
        locationManager = new LocationManager(this);
        homeManager = new HomeManager(this);
    }
}
