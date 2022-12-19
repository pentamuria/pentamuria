package de.pentamuria.system.commands;

import de.pentamuria.system.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class COMMAND_bag implements CommandExecutor {
    private final Main plugin;

    public COMMAND_bag(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(args.length == 0) {
                p.openInventory(plugin.bagManager.getPlayerBag(p.getUniqueId().toString()));
            } else if(args.length == 1) {
                if(args[0].equalsIgnoreCase("upload")) {
                    plugin.bagManager.uploadPlayerBag(p);
                }
            }
        }

        return true;
    }
}
