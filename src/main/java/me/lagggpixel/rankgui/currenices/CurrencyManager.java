package me.lagggpixel.rankgui.currenices;

import me.lagggpixel.rankgui.Main;

import java.util.UUID;

public class CurrencyManager {
    public static void deposit(UUID uuid, int amount) {
        int balance = getBalance(uuid);
        balance += amount;
        Main.getInstance().getBalances().remove(uuid);
        Main.getInstance().getBalances().put(uuid, balance);
        BalanceFileManager.getInstance().saveBalance(uuid);
    }

    public static void withdraw(UUID uuid, int amount) {
        int balance = getBalance(uuid);
        balance -= amount;
        Main.getInstance().getBalances().remove(uuid);
        Main.getInstance().getBalances().put(uuid, balance);
        BalanceFileManager.getInstance().saveBalance(uuid);
    }

    public static void set(UUID uuid, int amount) {
        Main.getInstance().getBalances().remove(uuid);
        Main.getInstance().getBalances().put(uuid, amount);
        BalanceFileManager.getInstance().saveBalance(uuid);
    }

    public static int getBalance(UUID uuid) {
        return Main.getInstance().getBalances().getOrDefault(uuid, 0);
    }

    public static void createBalance(UUID uuid) {
        Main.getInstance().getBalances().putIfAbsent(uuid, 0);
        BalanceFileManager.getInstance().saveBalance(uuid);
    }
}
