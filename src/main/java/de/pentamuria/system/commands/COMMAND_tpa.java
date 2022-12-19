package de.pentamuria.system.commands;

import de.pentamuria.system.countdowns.TPACountdown;
import de.pentamuria.system.main.Main;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class COMMAND_tpa implements CommandExecutor {
    private final Main plugin;
    public HashMap<Player, ArrayList<Player>> tpa;

    public COMMAND_tpa(Main main) {
        this.plugin = main;
        tpa = new HashMap<Player, ArrayList<Player>>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {

                    if(target.getName().equalsIgnoreCase(p.getName())) {
                        p.sendMessage("§cDu kannst dich nicht zu dir selber teleportieren!");

                    } else {

                        if(tpa.containsKey(target)) {
                            if(tpa.get(target).contains(p)) {
                                p.sendMessage("§cDu hast §a" + target.getName() + " §cbereits eine Anfrage gesendet!");

                            } else {
                                tpa.get(target).add(p);
                                //target.sendMessage("§3Du hast eine Teleportationsanfrage von §6§l" + p.getName() + " §3erhalten");
                                //target.sendMessage("§3/tpa <§aaccept§8|§4deny> §6" + p.getName());
                                //IChatBaseComponent chat = ChatSerializer.a("{\"text\":\"§3/tpa\",\"extra\":[{\"text\":\" §aaccept\",\"hoverEvent\":{\"action\":\"show_text\", \"value\":\"§aTPA-Anfrage annehmen\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpa accept " + p.getName() + "\",\"extra\":[{\"text\":\"§8|§4deny\",\"hoverEvent\":{\"action\":\"show_text\", \"value\":\"§4TPA-Anfrage ablehnen\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpa deny " + p.getName() + "\"      }      }        }]}]}");
                                target.sendMessage("§3Du hast eine Teleportationsanfrage von §6§l" + p.getName() + " §3erhalten");
                                net.md_5.bungee.api.chat.TextComponent message = new net.md_5.bungee.api.chat.TextComponent("/§3tpa §aaccept");
                                message.addExtra(" " + p.getName());
                                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aTPA-Anfrage annehmen").create()));
                                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa accept " + p.getName()));

                                target.spigot().sendMessage((BaseComponent) message);

                                net.md_5.bungee.api.chat.TextComponent message1 = new net.md_5.bungee.api.chat.TextComponent("/§3tpa §4deny");
                                message1.addExtra(" " + p.getName());
                                message1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aTPA-Anfrage §4ablehnen").create()));
                                message1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa deny " + p.getName()));

                                target.spigot().sendMessage((BaseComponent) message1);

                                p.sendMessage("§3Du hast eine Teleportationsanfrage an §6§l" + target.getName() + " §3geschickt");
                            }
                        } else {
                            ArrayList<Player> request = new ArrayList<Player>();
                            request.add(p);
                            tpa.put(target, request);
                            //target.sendMessage("§3Du hast eine Teleportationsanfrage von §6§l" + p.getName() + " §3erhalten");
                            //target.sendMessage("§3/tpa <§aaccept§8|§4deny> §6" + p.getName());
                            //IChatBaseComponent chat = ChatSerializer.a("{\"text\":\"§3/tpa\",\"extra\":[{\"text\":\" §aaccept\",\"hoverEvent\":{\"action\":\"show_text\", \"value\":\"§aTPA-Anfrage annehmen\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpa accept " + p.getName() + "\",\"extra\\\":[{\"text\":\"§8|§4deny\",\"hoverEvent\":{\"action\":\"show_text\", \"value\":\"§4TPA-Anfrage ablehnen\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/tpa deny " + p.getName() + "\"      }      }        }]}]}}]}");
                            target.sendMessage("§3Du hast eine Teleportationsanfrage von §6§l" + p.getName() + " §3erhalten");
                            net.md_5.bungee.api.chat.TextComponent message = new net.md_5.bungee.api.chat.TextComponent("/§3tpa §aaccept");
                            message.addExtra(" " +  p.getName());
                            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aTPA-Anfrage annehmen").create()));
                            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa accept " + p.getName()));

                            target.spigot().sendMessage((BaseComponent) message);

                            net.md_5.bungee.api.chat.TextComponent message1 = new net.md_5.bungee.api.chat.TextComponent("/§3tpa §4deny");
                            message1.addExtra(" " +  p.getName());
                            message1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aTPA-Anfrage §4ablehnen").create()));
                            message1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa deny " + p.getName()));

                            target.spigot().sendMessage((BaseComponent) message1);

                            p.sendMessage("§3Du hast eine Teleportationsanfrage an §6§l" + target.getName() + " §3geschickt");
                        }
                    }

                } else {
                    p.sendMessage("§cDieser Spieler ist nicht online!");
                }
            } else if(args.length==2){
                if(args[0].equalsIgnoreCase("accept")) {
                    Player from2 = Bukkit.getPlayer(args[1]);

                    if(from2 == null) {
                        p.sendMessage("§cDer Spieler §6" + args[1] + " ist offline");
                    } else {

                        if(tpa.containsKey(p)) {
                            if(tpa.get(p).contains(from2)) {
                                if(plugin.inFight.contains(from2)) {
                                    p.sendMessage(plugin.pr+"Dieser Spieler ist gerade §cim Kampf");
                                    p.sendMessage(plugin.pr+"§cDu bist noch im Kampf!");
                                    return true;
                                }
                                p.sendMessage("§bDu hast die TPA-Anfrage von §6" + from2.getName() + " §aangenommen");
                                from2.sendMessage("§6" + p.getName() + " §bhat deine TPA-Anfrage §aangenommen");
                                from2.sendMessage("§cBitte bewege dich nicht!");
                                new TPACountdown(from2).start(plugin, p);
                                //Console Message

                                //***********
                                tpa.get(p).remove(from2);
                            } else {
                                p.sendMessage("§3DU hast keine Anfrage von diesem Spieler");
                            }
                        } else {
                            p.sendMessage("§3DU hast keine Anfrage von diesem Spieler");
                        }



                    }


                } else if(args[0].equalsIgnoreCase("deny")) {

                    Player from2 = Bukkit.getPlayer(args[1]);

                    if(from2 == null) {
                        p.sendMessage("§cDer Spieler §6" + args[1] + " ist offline");
                    } else {

                        if(tpa.containsKey(p)) {
                            if(tpa.get(p).contains(from2)) {
                                p.sendMessage("§bDu hast die TPA-Anfrage von §6" + from2.getName() + " §4abgelehnt");
                                from2.sendMessage("§6" + p.getName() + " §bhat deine TPA-Anfrage §4abgelehnt");
                                //Console Message

                                //***********
                                tpa.get(p).remove(from2);
                            } else {
                                p.sendMessage("§3DU hast keine Anfrage von diesem Spieler");
                            }
                        } else {
                            p.sendMessage("§3DU hast keine Anfrage von diesem Spieler");
                        }



                    }


                } else {
                    p.sendMessage("§cSyntax error!");
                }

            }




        }


        return true;
    }
}
