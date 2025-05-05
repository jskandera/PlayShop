package me.joff1507.pshop.manager;

import me.joff1507.pshop.PlayerShop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LogUtils {

    private static final File logFile = new File(PlayerShop.getInstance().getDataFolder(), "transactions.log");

    public static void log(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write("[" + LocalDateTime.now() + "] " + message);
            writer.newLine();
        } catch (IOException e) {
            PlayerShop.getInstance().getLogger().warning("Erreur lors de l'écriture dans le journal de transactions.");
        }
    }
}
