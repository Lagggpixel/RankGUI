package me.lagggpixel.rankgui.managers;

import me.lagggpixel.rankgui.Main;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;

public class EconomyManager {
    static net.milkbowl.vault.economy.Economy economy = Main.getInstance().getEconomy();

    public static int getBalance(Player player) {
        return (int) economy.getBalance(player);
    }

    public static boolean withdraw(Player player, int amount) {
        EconomyResponse economyResponse = economy.withdrawPlayer(player, amount);
        return economyResponse.transactionSuccess();
    }

    public static boolean deposit(Player player, String amount) {
        EconomyResponse economyResponse = economy.depositPlayer(player, Double.parseDouble(amount));
        return economyResponse.transactionSuccess();
    }
}
