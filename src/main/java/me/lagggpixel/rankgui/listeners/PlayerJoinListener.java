package me.lagggpixel.rankgui.listeners;

import me.lagggpixel.rankgui.Main;
import me.lagggpixel.rankgui.currenices.CurrencyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    public PlayerJoinListener() {
        Main.getInstance().getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler(ignoreCancelled = true)
    public void PlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!Main.getInstance().getBalances().containsKey(uuid)) {
            CurrencyManager.createBalance(uuid);
        }
    }
}
