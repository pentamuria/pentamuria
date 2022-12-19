package de.pentamuria.system.countdowns;

import de.pentamuria.system.main.Main;
import de.pentamuria.system.utils.ActionBar;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class FightCountdown {
    public static HashMap<Player, BukkitTask> countdown = new HashMap<>();
    private Player p;
    private int count = 5;

    public FightCountdown(Player p) {
        this.p = p;
        this.count = 15;
    }

    public void start(Player d, Main main) {
        if(countdown.containsKey(p)) {
            return;
        }

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(main, new Runnable() {

            @Override
            public void run() {
                sendBar(count);

                if(count == 0) {
                    //Ausführung
                    if(main.inFight.contains(p)) {
                        main.inFight.remove(p);
                        p.sendMessage(main.pr + "Du bist nun §cnicht mehr §7im Kampf");

                    }
                    main.lastDmg.remove(p, d);

                    //Wichtig
                    countdown.get(p).cancel();
                    countdown.remove(p);

                    return;

                }
                count--;

            }
        }, 0, 20);

        countdown.put(this.p, task);


    }

    public void stop(Player d, Main main) {
        if(!countdown.containsKey(p)) {
            return;
        }


        countdown.get(this.p).cancel();
        countdown.remove(p);
        this.sendBar(this.p, "§8[§cXXXXX§8] §e§lX");
        if(main.inFight.contains(p)) {
            main.inFight.remove(p);
            p.sendMessage("§aDu bist nun nicht mehr im Kampf");

        }
        main.lastDmg.remove(p, d);


    }
    public void stop() {
        if(!countdown.containsKey(p)) {
            return;
        }


        countdown.get(this.p).cancel();
        countdown.remove(p);
        ActionBar.sendActionBar(this.p, "§8[§cXXXXX§8] §e§lX");
    }
    private void sendBar(int count) {
        // §8[§c§8

        String s = "§8[";
        for(int i = 0; i < count; i++) {
            s = s + "§c0";

        }
        for(int i = 0; i < (15 - count); i++) {
            s = s + "§70";
        }

        s = s + "§8] §e§l" + count;
        ActionBar.sendActionBar(this.p, s);

    }
    private void sendBar(Player p, String msg) {
        p.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR,new TextComponent(msg));


    }
}
