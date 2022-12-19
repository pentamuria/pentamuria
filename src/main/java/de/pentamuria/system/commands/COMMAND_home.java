package de.pentamuria.system.commands;

import de.pentamuria.system.countdowns.HomeCountdown;
import de.pentamuria.system.main.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class COMMAND_home implements CommandExecutor {
    private final Main plugin;

    public COMMAND_home(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 1) {
                Location loc = plugin.homeManager.getHome(p, Integer.parseInt(args[0]));
                if (loc.equals(p.getLocation())) {
                    return true;
                }

                new HomeCountdown(p, Integer.parseInt(args[0])).start(plugin, loc);
            } else if(args.length == 2 && args[0].equalsIgnoreCase("set")) {
                plugin.homeManager.setHome(p, Integer.parseInt(args[1]));
            } else {
                p.sendMessage("§a[HILFE] §c/home <set> <1|2>");

            }
        }
        return true;
    }
}
