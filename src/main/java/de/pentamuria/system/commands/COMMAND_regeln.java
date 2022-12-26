package de.pentamuria.system.commands;

import de.pentamuria.system.main.Main;
import de.pentamuria.system.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class COMMAND_regeln implements CommandExecutor {
    private final Main plugin;

    public COMMAND_regeln(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;
            Inventory inv = Bukkit.createInventory(null, 27, "§cRegeln");
            inv.setItem(9, new ItemBuilder(Material.DIAMOND).setName("§5Kein Cheaten")
                    .addLoreLine("§7").addLoreLine("§7Das Nutzen von §cHacks§7, §bCheats §7oder §eBugs")
                    .addLoreLine("§7ist hier vollständig §4verboten").toItemStack());
            inv.setItem(10, new ItemBuilder(Material.GOLDEN_SHOVEL).setName("§bAngemessenes Griefen")
                    .addLoreLine("§7").addLoreLine("§7Griefen ist grundsätzlich erlaubt, aber nur so viel,")
                    .addLoreLine("§7dass es §cnicht zu lange dauert §7das gegriefte §cwieder aufzubauen")
                    .toItemStack());
            inv.setItem(11, new ItemBuilder(Material.BOOK).setName("§cBug-Report")
                    .addLoreLine("§7").addLoreLine("§7Alle §cBugs §7müssen umgehend §cgemeldet §7werden")
                    .toItemStack());
            inv.setItem(12, new ItemBuilder(Material.ENDER_PEARL).setName("§9Kein TPA-Trappen")
                    .addLoreLine("§7").addLoreLine("§7Nach einer §9TPA §7darf der teleportierte Spieler")
                    .addLoreLine("§cnicht §7sofort §cangegriffen §7werden oder in §ceine Falle §7teleportiert werden")
                    .addLoreLine("§7Gleiches gilt natürlich auch für den sich §cteleportierenden Spieler!").toItemStack());
            p.openInventory(inv);
        }

        return true;
    }
}
