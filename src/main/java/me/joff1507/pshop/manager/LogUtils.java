package me.joff1507.pshop.manager;

import org.bukkit.Bukkit;

public class LogUtils {

    private static final String PREFIX = "§7[§6PlayerShop§7] ";

    public static void info(String message) {
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§a" + message);
    }

    public static void warn(String message) {
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§e" + message);
    }

    public static void error(String message) {
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§c" + message);
    }
}
