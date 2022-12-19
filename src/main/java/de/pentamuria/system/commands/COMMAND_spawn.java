package de.pentamuria.system.commands;

import de.pentamuria.system.countdowns.SpawnCountdown;
import de.pentamuria.system.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class COMMAND_spawn implements CommandExecutor {
    private final Main plugin;

    public COMMAND_spawn(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player) {
            Player p = (Player)sender;
            if(args.length == 0) {
                if(!plugin.inFight.contains(p)) {
                    new SpawnCountdown(p).start(plugin);
                } else {
                    p.sendMessage("§cDu befindest dich im Kampf!");
                }
            } else if(args.length == 1) {
                if(args[0].equalsIgnoreCase("set")) {
                    if(p.hasPermission("pentamuria.spawn.set")) {
                        plugin.locationManager.setSpawn(p, p.getLocation());
                    }
                }
            } else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("setradius")) {
                    if(p.hasPermission("pentamuria.spawn.setradius")) {
                        plugin.locationManager.setSpawnRadius(p, Integer.parseInt(args[1]));
                    }
                }
            }
        }

        return true;
    }
}
