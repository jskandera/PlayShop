package me.joff1507.pshop.command;

import me.joff1507.pshop.PlayerShop;
import me.joff1507.pshop.manager.ShopManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
    private final PlayerShop plugin;
    private final ShopManager manager;

    public CommandHandler(PlayerShop plugin) {
        this.plugin = plugin;
        this.manager = plugin.getShopManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "/pshop <create|assign|unassign|list|reload>");
            return true;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "create":
                if (!sender.hasPermission("pshop.admin")) return noPerm(sender);
                if (args.length == 2) {
                    manager.createShop(args[1], null, sender);
                } else if (args.length == 3) {
                    manager.createShop(args[1], args[2], sender);
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /pshop create <shop_id> [joueur]");
                }
                return true;
            case "assign":
                if (!sender.hasPermission("pshop.admin")) return noPerm(sender);
                if (args.length == 3) {
                    manager.assignShop(args[1], args[2], sender);
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /pshop assign <shop_id> <joueur>");
                }
                return true;
            case "unassign":
                if (!sender.hasPermission("pshop.admin")) return noPerm(sender);
                if (args.length == 2) {
                    manager.unassignShop(args[1], sender);
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /pshop unassign <shop_id>");
                }
                return true;
            case "list":
                if (!sender.hasPermission("pshop.admin")) return noPerm(sender);
                manager.listShops(sender);
                return true;
            case "reload":
                if (!sender.hasPermission("pshop.admin")) return noPerm(sender);
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Configuration rechargée.");
                return true;
            default:
                sender.sendMessage(ChatColor.RED + "Commande inconnue: " + sub);
                return true;
        }
    }

    private boolean noPerm(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'utiliser cette commande.");
        return true;
    }
}
