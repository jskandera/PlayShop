package me.joff1507.pshop.command;

import me.joff1507.pshop.PlayerShop;
import me.joff1507.pshop.manager.ShopManager;
import me.joff1507.pshop.manager.LogUtils;
import me.joff1507.pshop.util.WorldGuardUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private final PlayerShop plugin;
    private final ShopManager shopManager;
    private final LogUtils log;

    public CommandHandler(PlayerShop plugin) {
        this.plugin = plugin;
        this.shopManager = plugin.getShopManager();
        this.log = plugin.getLogUtils();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            log.message(sender, "&cOnly players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            log.message(player, "&eUsage: /pshop <create|remove|assign|unassign> <id>");
            return true;
        }

        String subcommand = args[0].toLowerCase();

        switch (subcommand) {
            case "create":
                if (args.length < 2) {
                    log.message(player, "&cUsage: /pshop create <id>");
                    return true;
                }
                String createId = args[1];
                if (!WorldGuardUtils.regionExists(player.getWorld(), "shop_" + createId)) {
                    log.message(player, "&cLa région 'shop_" + createId + "' n'existe pas.");
                    return true;
                }
                shopManager.createShop(player, createId);
                break;

            case "remove":
                if (args.length < 2) {
                    log.message(player, "&cUsage: /pshop remove <id>");
                    return true;
                }
                shopManager.removeShop(player, args[1]);
                break;

            case "assign":
                if (args.length < 3) {
                    log.message(player, "&cUsage: /pshop assign <id> <player>");
                    return true;
                }
                shopManager.assignShop(player, args[1], args[2]);
                break;

            case "unassign":
                if (args.length < 2) {
                    log.message(player, "&cUsage: /pshop unassign <id>");
                    return true;
                }
                shopManager.unassignShop(player, args[1]);
                break;

            default:
                log.message(player, "&cUnknown subcommand. Try: create, remove, assign, unassign.");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("create", "remove", "assign", "unassign");
        }

        if (args.length == 2 && !args[0].equalsIgnoreCase("assign")) {
            return shopManager.getShopIds();
        }

        return Collections.emptyList();
    }
}
