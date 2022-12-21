package de.pentamuria.system.main;

import de.pentamuria.gilde.gildensystem.GildenSystem;
import de.pentamuria.statistics.statisticsapi.StatisticsAPI;
import de.pentamuria.system.commands.*;
import de.pentamuria.system.events.*;
import de.pentamuria.system.manager.BagManager;
import de.pentamuria.system.manager.HomeManager;
import de.pentamuria.system.manager.LocationManager;
import de.pentamuria.system.scoreboard.CustomPlayerScoreboard;
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

    CustomPlayerScoreboard playerScoreboard;

    public StatisticsAPI statsAPI;
    public GildenSystem gildenSystem;

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadCommands();
        loadEvents();
        loadManager();

        Bukkit.getConsoleSender().sendMessage("§7-----------§8[§5Pentamuria§8]§7-----------");
        Bukkit.getConsoleSender().sendMessage(prefix + "Dieses Plugin wurde §agestartet");

        // Start Synchro
        syncTimer.start();

        // Load APIS
        if(Bukkit.getServer().getPluginManager().getPlugin("StatisticsAPI")!=null) {
            statsAPI = (StatisticsAPI) Bukkit.getServer().getPluginManager().getPlugin("StatisticsAPI");
            Bukkit.getConsoleSender().sendMessage(pr + "Verbindung zur §aStatisticsAPI §7wurde §ainitialisiert");
        } else {
            Bukkit.getConsoleSender().sendMessage(pr + "Verbindung zur §aStatisticsAPI §ist §4fehlgeschlagen");
            Bukkit.getConsoleSender().sendMessage(pr + "§cBitte sofort die §aStatisticsAPI §chinzufügen");
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("GildenSystem")!=null) {
            gildenSystem = (GildenSystem) Bukkit.getServer().getPluginManager().getPlugin("GildenSystem");
            Bukkit.getConsoleSender().sendMessage(pr + "Verbindung zum §aGildenSystem §7wurde §ainitialisiert");
        } else {
            Bukkit.getConsoleSender().sendMessage(pr + "Verbindung zum §aGildenSystem §ist §4fehlgeschlagen");
            Bukkit.getConsoleSender().sendMessage(pr + "§cBitte sofort das §aGildenSystem §chinzufügen");
        }
        Bukkit.getConsoleSender().sendMessage("§7---------------------------------");

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
        loadScoreboard();
        new JoinListener(this, playerScoreboard);
        new DeathListener(this, playerScoreboard);
    }

    private void loadManager() {
        // Load all Manager Classes
        bagManager = new BagManager(this);
        syncTimer = new SyncTimer(this);
        locationManager = new LocationManager(this);
        homeManager = new HomeManager(this);
    }

    public void loadScoreboard() {
        playerScoreboard = new CustomPlayerScoreboard("§5Pentamuria §4♥");
        playerScoreboard.setDefaultSidebarScore(9, " ");
        playerScoreboard.setDefaultSidebarScore(8, "§l§a⚒ Deine Gilde");
        playerScoreboard.setDefaultSidebarScore(7,"§7↣ §eLade...");
        playerScoreboard.setDefaultSidebarScore(6, " ");
        playerScoreboard.setDefaultSidebarScore(5, "§l§a♰ Deine Tode");
        playerScoreboard.setDefaultSidebarScore(4,"§7↣ §cLade...");
        playerScoreboard.setDefaultSidebarScore(3, " ");
        playerScoreboard.setDefaultSidebarScore(2, "§l§a❀ Spieleranzahl");
        playerScoreboard.setDefaultSidebarScore(1,"§7↣ §cLade...");
        playerScoreboard.setDefaultSidebarScore(0, " ");
    }
}
