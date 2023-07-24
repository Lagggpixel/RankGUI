package me.lagggpixel.rankgui.managers;

import me.lagggpixel.rankgui.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MessagesFileManager {

    public static MessagesFileManager INSTANCE;

    private final File file;
    private final FileConfiguration config;

    public MessagesFileManager() {
        INSTANCE = this;
        Main.getInstance().saveResource("messages.yml", false);
        this.file = new File(Main.getInstance().getDataFolder(), "messages.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        loadConfig();
    }

    public void loadConfig() {
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }

        for (String path : get().getKeys(false)) {
            Main.getInstance().getMessages().putIfAbsent(path,
                    Objects.requireNonNull(get().getString(path)).replace("{p}", Objects.requireNonNull(Main.getInstance().getConfig().getString("prefix"))));
        }

    }

    public FileConfiguration get() {
        return this.config;
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MessagesFileManager getInstance() {
        return INSTANCE;
    }
}
