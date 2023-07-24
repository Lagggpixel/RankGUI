package me.lagggpixel.rankgui.currenices;

import me.lagggpixel.rankgui.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class BalanceFileManager {
    private final File file;
    private final FileConfiguration config;
    private static BalanceFileManager INSTANCE;

    public BalanceFileManager() {
        INSTANCE = this;
        this.file = new File(Main.getInstance().getDataFolder(), "balances.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveBalance(UUID uuid) {
        config.set(uuid.toString(), Main.getInstance().getBalances().getOrDefault(uuid, 0));
        saveConfig();
    }

    public void saveBalances() {
        Main.getInstance().getBalances().forEach((k, v) -> {
            saveBalance(k);
        });
    }

    public void loadBalances() {
        if (!file.exists()) {
            return;
        }

        Main.getInstance().getBalances().clear();

        for (String key : config.getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            int balance = config.getInt(key);
            Main.getInstance().getBalances().put(uuid, balance);
        }
    }

    private void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BalanceFileManager getInstance() {
        return INSTANCE;
    }
}
