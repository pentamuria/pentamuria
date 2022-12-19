package de.pentamuria.system.manager;

import de.pentamuria.system.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class HomeManager {

    private final Main main;
    private HashMap<String, Location> home1;
    private HashMap<String, Location> home2;

    public HomeManager(Main main) {
        this.main = main;
        this.home1 = new HashMap<>();
        this.home2 = new HashMap<>();
        loadPlayerHomes();
    }

    public void loadPlayerHomes() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            Location loc1 = getHome(1, all, true);
            if(!loc1.equals(all.getLocation())) {
                home1.put(all.getUniqueId().toString(), loc1);
            }
            Location loc2 = getHome(2, all, true);
            if(!loc2.equals(all.getLocation())) {
                home2.put(all.getUniqueId().toString(), loc2);
            }
        }
    }

    public void loadPlayerHome(Player p) {
        if(!home1.containsKey(p.getUniqueId().toString())) {
            Location loc1 = getHome(1, p, true);
            if (!loc1.equals(p.getLocation())) {
                home1.put(p.getUniqueId().toString(), loc1);
            }
        }
        if(!home2.containsKey(p.getUniqueId().toString())) {
            Location loc2 = getHome(2, p, true);
            if (!loc2.equals(p.getLocation())) {
                home2.put(p.getUniqueId().toString(), loc2);
            }
        }
    }

    public void setHome(Player p, int home) {
        if(home==1) {
            home1.put(p.getUniqueId().toString(), p.getLocation());
        } else if(home==2) {
            home2.put(p.getUniqueId().toString(), p.getLocation());
        } else {
            return;
        }
        setHome(home, p);
    }

    public Location getHome(Player p, int home) {
        String uuid = p.getUniqueId().toString();
        loadPlayerHome(p);
        if(home == 1) {
            if(home1.containsKey(uuid)) {
                return home1.get(uuid);
            }
        } else if(home==2) {
            if(home2.containsKey(uuid)) {
                return home2.get(uuid);
            }
        }
        p.sendMessage("§c[Fehler] §7Das §7Zuhause [§b" + home + "§7] §cexistiert nicht");
        return p.getLocation();

    }


    private void setHome(int home, Player p) {

        File file = new File("plugins/Pentamuria/Home", "home.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if(home == 1) {

            String world = p.getWorld().getName();
            double x = p.getLocation().getX();
            double y = p.getLocation().getY();
            double z = p.getLocation().getZ();
            double yaw = p.getLocation().getYaw();
            double pitch = p.getLocation().getPitch();

            cfg.set(p.getName() + "." + home + ".world", world);
            cfg.set(p.getName() + "." + home + ".x", x);
            cfg.set(p.getName() + "." + home + ".y", y);
            cfg.set(p.getName() + "." + home + ".z", z);
            cfg.set(p.getName() + "." + home + ".yaw", yaw);
            cfg.set(p.getName() + "." + home + ".pitch", pitch);
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            p.sendMessage("§3Zuhause [§a" + home + "§3] + §3wurde gesetzt");



        } else if(home == 2) {
            String world = p.getWorld().getName();
            double x = p.getLocation().getX();
            double y = p.getLocation().getY();
            double z = p.getLocation().getZ();
            double yaw = p.getLocation().getYaw();
            double pitch = p.getLocation().getPitch();

            cfg.set(p.getName() + "." + home + ".world", world);
            cfg.set(p.getName() + "." + home + ".x", x);
            cfg.set(p.getName() + "." + home + ".y", y);
            cfg.set(p.getName() + "." + home + ".z", z);
            cfg.set(p.getName() + "." + home + ".yaw", yaw);
            cfg.set(p.getName() + "." + home + ".pitch", pitch);

            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            p.sendMessage("§3Zuhause [§a" + home + "§3] + §3wurde gesetzt");

        } else {
            p.sendMessage("/sethome <1|2>");
        }


    }
    private Location getHome(int home, Player p, boolean silent) {

        File file = new File("plugins/Pentamuria/Home", "home.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if(file.exists() == false) {
            if(!silent)p.sendMessage("§cKein Home gespeichert");
            return p.getLocation();
        }
        if(!cfg.contains(p.getName())) {
            if(!silent)p.sendMessage("§cKein Home gespeichert");
            return p.getLocation();
        }

        if(home > 2) {
            if(!silent)p.sendMessage("§cKein Home " + home + " gespeichert");
            return p.getLocation();
        }

        String test = cfg.getString(p.getName() + "." + home + ".world");

        if(test == null) {

            return p.getLocation();

        }

        String world = cfg.getString(p.getName() + "." + home + ".world");
        double x = cfg.getDouble(p.getName() + "." + home + ".x");
        double y = cfg.getDouble(p.getName() + "." + home + ".y");
        double z = cfg.getDouble(p.getName() + "." + home + ".z");
        double yaw = cfg.getDouble(p.getName() + "." + home + ".yaw");
        double pitch = cfg.getDouble(p.getName() + "." + home + ".pitch");


        Location loc = new Location(Bukkit.getWorld(world), x, y, z);
        loc.setPitch((float) pitch);

        loc.setYaw((float) yaw);
        return loc;
    }

}
