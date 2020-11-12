package me.minhcrafters.lprankcommand.command;

import me.minhcrafters.lprankcommand.Main;
import me.minhcrafters.lprankcommand.utils.Utils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand implements CommandExecutor {

    private Main plugin;
    public Player target;

    public RankCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        LuckPerms luckPerms = LuckPermsProvider.get();
        Player p = (Player) sender;

        if (p.hasPermission("lprankcommand.use")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (p.hasPermission("lprankcommand.reload")) {
                        plugin.reloadConfig();
                        p.sendMessage(Utils.colorize(plugin.getConfig().getString("prefix") + "&aReload successful!"));
                        return true;
                    } else {
                        p.sendMessage(Utils.colorize(plugin.getConfig().getString("prefix") + plugin.getConfig().getString("no_permission")));
                    }
                } else {
                    target = Bukkit.getPlayerExact(args[0]);

                    if (target == null) {
                        p.sendMessage(Utils.colorize("&cThat player is not online!"));
                        return true;
                    } else {
                        try {
                            Group group = luckPerms.getGroupManager().getGroup(args[1]);
                            if (group == null) {
                                p.sendMessage(Utils.colorize(plugin.getConfig().getString("prefix") + "&cThat rank doesn't exist!"));
                                return true;
                            } else {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " parent set " + args[1]);
                                if (plugin.getConfig().getBoolean("MessageStaff.enabled") == true) {
                                    if (p.hasPermission("lprankcommand.staffmsg")) {
                                        Bukkit.broadcastMessage(Utils.colorize(plugin.getConfig().getString("prefix") + plugin.getConfig().getString("MessageStaff.message").replace("%player%", p.getName()).replace("%rank%", args[1])));
                                    }
                                }
                                Bukkit.broadcastMessage(Utils.colorize(plugin.getConfig().getString("prefix") + plugin.getConfig().getString("setrank").replace("%player%", p.getName()).replace("%rank%", args[1])));
                                p.sendMessage(Utils.colorize(plugin.getConfig().getString("prefix") + plugin.getConfig().getString("setrank2").replace("%rank%", args[1])));
                                return true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                p.sendMessage(Utils.colorize(plugin.getConfig().getString("prefix") + plugin.getConfig().getString("wrong_arguments")));
            }
        } else {
            p.sendMessage(Utils.colorize(plugin.getConfig().getString("prefix") + plugin.getConfig().getString("no_permission")));
        }
        return false;
    }
}
