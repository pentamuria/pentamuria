package de.pentamuria.system.manager;

import de.pentamuria.system.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class BagManager {


    private final Main plugin;

    private HashMap<String, Inventory> bags;

    public BagManager(Main main) {
        this.plugin = main;
        bags = new HashMap<>();
        loadPlayerBags();
    }

    /*
    Server Run Time Methods
     */

    /**
     * Load all Player Bags (Online Players)
     */
    public void loadPlayerBags() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            bags.put(all.getUniqueId().toString(), getBag(all));
        }
        Bukkit.getConsoleSender().sendMessage(plugin.pr + "Die §cRucksäcke §7der Spieler wurden §ageladen");
    }

    /**
     * Load Single Player Bag
     * @param uuid
     */
    public void loadPlayerBag(String uuid) {
        if (!bags.containsKey(uuid)) {
            bags.put(uuid, getBagByUuid(uuid));
        }
    }

    /**
     * Save all Player Bags to the file
     */
    public void savePlayerBags() {
        Bukkit.getConsoleSender().sendMessage(plugin.pr + "Die §cRucksäcke §7aller Spieler werden gespeichert...");

        for (String uuid : bags.keySet()) {
            addToBag(uuid, bags.get(uuid));
        }
        Bukkit.getConsoleSender().sendMessage(plugin.pr + "Die §cRucksäcke §7aller Spieler wurden §agespeichert!");
    }

    /**
     * Get Bag of a specific player
     * @param uuid
     * @return
     */
    public Inventory getPlayerBag(String uuid) {
        if (!bags.containsKey(uuid)) {
            loadPlayerBag(uuid);
        }
        return bags.get(uuid);
    }

    /**
     * Saves the bag of a player
     * @param uuid
     * @param inv
     */
    public void savePlayerBag(String uuid, Inventory inv) {
        bags.put(uuid, inv);
    }

    /**
     * Upload Bag to file
     * @param p
     */
    public void uploadPlayerBag(Player p) {
        if (!bags.containsKey(p.getUniqueId().toString())) {
            loadPlayerBag(p.getUniqueId().toString());
        }
        addToBag(p.getUniqueId().toString(), bags.get(p.getUniqueId().toString()));
        p.sendMessage("§7----------------------------------------");
        p.sendMessage("§7Dein Rucksack §7wurde §aerfolgreich hochgeladen!");
        p.sendMessage("§7Bitte benutze diesen Befehl nur, wenn du");
        p.sendMessage("§4extrem wichtige Items §7in deinen Rucksack legst");
        p.sendMessage("");
        p.sendMessage("§cRucksäcke werden ansonsten nach einiger Zeit automatisch synchronisiert!");
        p.sendMessage("§7----------------------------------------");
    }

    /**
     * Method for saving the player bag inv
     * @param uid
     * @param bag
     */
    private void addToBag(String uid, Inventory bag) {
        File file = new File("plugins/Pentamuria/Bag", "bagback.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if(file.exists()) {
            if(cfg.contains(uid)) {
                cfg.set(uid, bag.getContents());
                try {
                    cfg.save(file);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                cfg.set(uid, bag.getContents());
                try {
                    cfg.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {

            cfg.set(uid, bag.getContents());
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }

    /**
     * Get Bag of a player from the file
     * @param p
     * @return
     */
    private Inventory getBag(Player p) {
        File file = new File("plugins/Pentamuria/Bag", "bagback.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if(file.exists()) {
            if(cfg.contains(p.getUniqueId().toString())) {
                Inventory inv = Bukkit.createInventory(null, 5*9, "§aDein Rucksack");
                ItemStack[] content = ((List<ItemStack>) cfg.get(p.getUniqueId().toString())).toArray(new ItemStack[0]);
                inv.setContents(content);
                return inv;
            } else {
                Inventory inv = Bukkit.createInventory(null, 5*9, "§aDein Rucksack");
                inv.addItem(new ItemStack(Material.OAK_LOG));
                return inv;
            }
        } else {
            Inventory inv = Bukkit.createInventory(null, 5*9, "§aDein Rucksack");
            inv.addItem(new ItemStack(Material.OAK_LOG));
            return inv;
        }
    }

    /**
     * Get Bag of a player from the file by its uuid
     * @param uuid
     * @return
     */
    private Inventory getBagByUuid(String uuid) {
        File file = new File("plugins/Pentamuria/Bag", "bagback.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if(file.exists()) {
            if(cfg.contains(uuid)) {
                Inventory inv = Bukkit.createInventory(null, 5*9, "§aDein Rucksack");
                ItemStack[] content = ((List<ItemStack>) cfg.get(uuid)).toArray(new ItemStack[0]);
                inv.setContents(content);
                return inv;
            } else {
                Inventory inv = Bukkit.createInventory(null, 5*9, "§aDein Rucksack");
                inv.addItem(new ItemStack(Material.OAK_LOG));
                return inv;
            }
        } else {
            Inventory inv = Bukkit.createInventory(null, 5*9, "§aDein Rucksack");
            inv.addItem(new ItemStack(Material.OAK_LOG));
            return inv;
        }
    }

    /**
     * Clear Player Bag from file
     * @param player
     */
    private void removeBagInv(String player) {
        File file = new File("plugins/Pentamuria/Bag", "bagback.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if(file.exists()) {
            if(cfg.contains(player)) {
                cfg.set(player, null);
                try {
                    cfg.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
