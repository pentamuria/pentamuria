package de.pentamuria.system.countdowns;

import de.pentamuria.system.main.Main;
import de.pentamuria.system.utils.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class HomeCountdown {

    private static HashMap<Player, BukkitTask> countdown;
    static {
        countdown = new HashMap<>();
    }

    private Player p;
    private int count;
    private int home;


    public HomeCountdown(Player p, int home) {
        this.p = p;
        this.count = 6;
        this.home = home;

    }

    public void start(Main plugin, Location loc) {

        if(countdown.containsKey(p)) {
            return;
        }

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {

            @Override
            public void run() {
                sendBar(count);

                if(count == 0) {

                    p.teleport(loc);
                    p.sendMessage(plugin.pr + "Teleport zum §bHome " + home + " §7war §aerfolgreich");
                    countdown.get(p).cancel();
                    countdown.remove(p);
                    return;
                }
                count--;

            }
        }, 0, 20);


        countdown.put(this.p, task);


    }

    public static void stop(Player p) {
        if(!countdown.containsKey(p)) {
            return;
        }

        countdown.get(p).cancel();
        p.sendMessage("§cCountdown abgebrochen!");
        ActionBar.sendActionBar(p, "§8[§cXXXXX§8] §e§lX");
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
