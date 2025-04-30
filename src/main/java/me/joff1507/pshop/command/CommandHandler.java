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
        if (!sender.hasPermission("pshop.admin")) {
            sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'utiliser cette commande.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "/pshop <create|assign|unassign|list>");
            return true;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "create":
                if (args.length == 2) {
                    manager.createShop(args[1], null, sender);
                } else if (args.length == 3) {
                    manager.createShop(args[1], args[2], sender);
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /pshop create <shop_id> [joueur]");
                }
                return true;
            case "assign":
                if (args.length == 3) {
                    manager.assignShop(args[1], args[2], sender);
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /pshop assign <shop_id> <joueur>");
                }
                return true;
            case "unassign":
                if (args.length == 2) {
                    manager.unassignShop(args[1], sender);
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /pshop unassign <shop_id>");
                }
                return true;
            case "list":
                manager.listShops(sender);
                return true;
            default:
                sender.sendMessage(ChatColor.RED + "Commande inconnue: " + sub);
                return true;
        }
    }
}