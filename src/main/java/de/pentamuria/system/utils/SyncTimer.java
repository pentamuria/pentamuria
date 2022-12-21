package de.pentamuria.system.utils;

import de.pentamuria.system.main.Main;
import org.bukkit.Bukkit;

public class SyncTimer {
    private final Main plugin;

    public SyncTimer(Main main) {
        this.plugin = main;
    }

    public void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            @Override
            public void run() {
                Bukkit.getConsoleSender().sendMessage(plugin.pr + "Beginne die §bSynchronisation");
                Bukkit.getConsoleSender().sendMessage("§7-------------[§bSynchro]-------------");

                plugin.bagManager.savePlayerBags();
                plugin.statsAPI.stats.uploadStats();

                Bukkit.getConsoleSender().sendMessage("§7-------------------------------");
                Bukkit.getConsoleSender().sendMessage(plugin.pr + "§bSynchronisation §7wurde §aerfolgreich abgeschlossen");
            }

            //}, 20*60*20, 20*60*20);
        }, 20*60*2, 20*60*2);
    }
}
