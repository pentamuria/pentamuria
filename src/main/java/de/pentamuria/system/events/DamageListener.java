package de.pentamuria.system.events;

import de.pentamuria.system.countdowns.FightCountdown;
import de.pentamuria.system.main.Main;
import de.pentamuria.system.utils.ActionBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    private final Main plugin;

    public DamageListener(Main main) {
        this.plugin = main;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if(e.getDamager() instanceof Player) {
                Player t = (Player) e.getDamager();
                /*
                SCENARIO 01: Player hits another player
                 */
                if(p.getLocation().getWorld().getName().equalsIgnoreCase(plugin.locationManager.getSpawn().getWorld().getName())) {
                    if(p.getLocation().distance(plugin.locationManager.getSpawn()) < plugin.locationManager.getRadius()) {
                        e.setCancelled(true);
                        int zahl = (int)p.getLocation().distance(plugin.locationManager.getSpawn());
                        int minus = plugin.locationManager.getRadius()-zahl;
                        ActionBar.sendActionBar(t, "§cHier kannst du nicht kämpfen §8| §bNoch §6" + minus + " §bBlöcke");
                        return;
                    }
                }
                if(p.getName() != t.getName()) {
                    plugin.lastDmg.put(p, t);
                    plugin.lastDmg.put(t, p);
                } else {
                    return;
                }
                if(plugin.inFight.contains(p)) {
                    new FightCountdown(p).stop();
                    new FightCountdown(p).start(t, plugin);

                    new FightCountdown(t).stop();
                    new FightCountdown(t).start(p, plugin);


                } else {
                    p.setAllowFlight(false);
                    t.setAllowFlight(false);
                    plugin.inFight.add(p);
                    plugin.inFight.add(t);
                    p.sendMessage("§cDu bist nun im Kampf...Verlasse den Server nicht!");
                    t.sendMessage("§cDu bist num im Kampf...Verlasse den Server nicht");
                    new FightCountdown(p).stop();
                    new FightCountdown(p).start(t, plugin);

                    new FightCountdown(t).stop();
                    new FightCountdown(t).start(p, plugin);
                }



            }
        }
    }
}
