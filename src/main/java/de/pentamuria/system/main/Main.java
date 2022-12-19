package de.pentamuria.system.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public String pr = "";
    String prefix = "§5Pentamuria §8- §7";

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage(pr + "Dieses Plugin wurde §agestartet");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage(pr + "Dieses Plugin wurde §4deaktiviert");
    }
}
