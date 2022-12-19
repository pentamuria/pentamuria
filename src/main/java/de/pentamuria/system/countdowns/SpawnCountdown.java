package de.pentamuria.system.countdowns;

import de.pentamuria.system.main.Main;
import de.pentamuria.system.utils.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class SpawnCountdown {

    private static HashMap<Player, BukkitTask> countdown;
    static {
        countdown = new HashMap<>();
    }

    private Player p;
    private int count;

    public SpawnCountdown(Player p) {
        this.p = p;
        this.count = 6;

    }

    public void start(Main main) {

        if(countdown.containsKey(p)) {
            return;
        }

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(main, new Runnable() {

            @Override
            public void run() {
                sendBar(count);

                if(count == 0) {

                    p.teleport(main.locationManager.getSpawn());
                    p.sendMessage("§bTeleport zum §6Spawn §aerfolgreich");
                    countdown.get(p).cancel();
                    countdown.remove(p);
                    return;
                }
                count--;

            }
        }, 0, 20);


        countdown.put(this.p, task);


    }

    public void stop() {
        if(!countdown.containsKey(p)) {
            return;
        }

        countdown.get(this.p).cancel();
        this.p.sendMessage("§cCountdown abgebrochen!");
        ActionBar.sendActionBar(this.p, "§8[§cXXXXX§8] §e§lX");
        countdown.remove(p);
    }




    private void sendBar(int count) {
        // §8[§c§8

        String s = "§8[";
        for(int i = 0; i < count; i++) {
            s = s + "§c0";

        }
        for(int i = 0; i < (6 - count); i++) {
            s = s + "§70";
        }

        s = s + "§8] §e§l" + count;
        ActionBar.sendActionBar(this.p, s);





    }

}
