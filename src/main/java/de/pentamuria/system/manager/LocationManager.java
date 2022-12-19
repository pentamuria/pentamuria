package de.pentamuria.system.manager;

import de.pentamuria.system.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class LocationManager {
    private final Main plugin;

    private Location spawn;
    private int radius;

    public LocationManager(Main main) {
        this.plugin = main;
        spawn = getWorldSpawn();
        radius = getWorldSpawnRadius();
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Player p, Location spawn) {
        this.spawn = spawn;
        setWorldSpawn(p);
    }

    public int getRadius() {
        return radius;
    }

    public void setSpawnRadius(Player p, int radius) {
        this.radius = radius;
        setWorldSpawnRadius(p, radius);
    }

    private void setWorldSpawn(Player p) {
        File file = new File("plugins/Pentamuria", "locs.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("Spawn", p.getLocation());


        try {
            cfg.save(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p.sendMessage(plugin.pr + "Spawn wurde §agesetzt");
        //p.sendMessage(Main.main.pr);

    }
    private Location getWorldSpawn() {
        File file = new File("plugins/Pentamuria", "locs.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if(file.exists()) {
            if(cfg.contains("Spawn")) {
                return (Location)cfg.get("Spawn");
            } else {
                //p.sendMessage(Main.main.pr + " §cEs wurde noch kein Spawn gesetzt");
                return Bukkit.getWorlds().get(0).getSpawnLocation();
            }

        } else {
            //p.sendMessage(Main.main.pr + " §cEs wurde noch kein Spawn gesetzt");
            return Bukkit.getWorlds().get(0).getSpawnLocation();
        }


    }

    private void setWorldSpawnRadius(Player p, int radius) {
        File file = new File("plugins/Pentamuria", "locs.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("Radius", radius);


        try {
            cfg.save(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p.sendMessage(plugin.pr + "Spawn-Radius wurde §agesetzt");
        //p.sendMessage(Main.main.pr);

    }
    private int getWorldSpawnRadius() {
        File file = new File("plugins/Pentamuria", "locs.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if(file.exists()) {
            if(cfg.contains("Radius")) {
                return cfg.getInt("Radius");
            } else {
                //p.sendMessage(Main.main.pr + " §cEs wurde noch kein Spawn gesetzt");
                return 50;
            }

        } else {
            //p.sendMessage(Main.main.pr + " §cEs wurde noch kein Spawn gesetzt");
            return 50;
        }


    }
}
