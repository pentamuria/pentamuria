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

public class COMMAND_hilfe implements CommandExecutor {
    private final Main plugin;

    public COMMAND_hilfe(Main main) {
        this.plugin = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;
            Inventory inv = Bukkit.createInventory(null, 9*5, "§aHilfe");

            inv.setItem(22, new ItemBuilder(Material.DIAMOND, 1).setName("§b/spawn")
                    .addLoreLine("§7").addLoreLine("§8↣ §7Teleportiere dich zum §eSpawn")
                    .toItemStack());

            inv.setItem(10, new ItemBuilder(Material.GRASS, 1).setName("§b/home <set> <1|2>")
                    .addLoreLine("§7").addLoreLine("§8↣ §7Teleportiere dich zu oder setze")
                    .addLoreLine("§e1-2 §aHomes")
                    .toItemStack());

            inv.setItem(16, new ItemBuilder(Material.ENDER_PEARL, 1).setName("§b/tpa <Spieler>")
                    .addLoreLine("§7").addLoreLine("§8↣ §7Teleportiere dich zu einem anderen §aSpieler")
                    .toItemStack());

            inv.setItem(18, new ItemBuilder(Material.EMERALD, 1).setName("§a/stats")
                    .addLoreLine("§7").addLoreLine("§8↣ §7Sehe alle deine §agespeicherten Statistiken")
                    .toItemStack());

            inv.setItem(28, new ItemBuilder(Material.ENDER_CHEST, 1).setName("§b/bag §7| §b/gilde bag")
                    .addLoreLine("§7").addLoreLine("§8↣ §7Öffne deinen/euren eigenen §eRucksack §7und")
                            .addLoreLine("§7verstaue alle deine §eItems")
                    .toItemStack());

            inv.setItem(34, new ItemBuilder(Material.DIAMOND_SWORD, 1).setName("§b/gilde")
                    .addLoreLine("§7").addLoreLine("§8↣ §7Erhalte Informationen über das §cGilden-System")
                    .addLoreLine("§cGilden §7sind das §cHerzstück §7von §5Pentamuria")
                    .toItemStack());

            inv.setItem(26, new ItemBuilder(Material.EMERALD, 1).setName("§b/trade <Spieler>")
                    .addLoreLine("§7").addLoreLine("§8↣ §7Handel §asicher §7mit anderen Spielern")
                    .toItemStack());

            inv.setItem(20, new ItemBuilder(Material.ENCHANTED_BOOK, 1).setName("§d/be menu")
                    .addLoreLine("§7").addLoreLine("§8↣ §7Entdecke komplett neue §dVerzauberungen")
                    .toItemStack());

            inv.setItem(24, new ItemBuilder(Material.CHEST, 1).setName("§9/gilde chest <§alock§7|§cunlock§7>")
                    .addLoreLine("§7").addLoreLine("§8↣ §7Entdecke komplett neue §dVerzauberungen")
                    .toItemStack());

            for(int i = 0; i<inv.getSize();i++) {
                if(inv.getItem(i)==null) {
                    inv.setItem(i, new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE, 1).setName("§7").toItemStack());
                }
            }
            p.openInventory(inv);
        }
        return true;
    }
}
