package me.lagggpixel.rankgui.commands;

import me.lagggpixel.rankgui.Main;
import me.lagggpixel.rankgui.currenices.CurrencyManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class VotePointsCommand implements TabExecutor {

    @SuppressWarnings("DataFlowIssue")
    public VotePointsCommand() {
        Main.getInstance().getCommand("votepoints").setExecutor(this);
        Main.getInstance().getCommand("votepoints").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {


        if (commandSender instanceof Player sender) {
            if (args.length == 0) {
                UUID uuid = sender.getUniqueId();
                if (!Main.getInstance().getBalances().containsKey(uuid)) {
                    CurrencyManager.createBalance(uuid);
                }
                commandSender.sendMessage(Main.getInstance().getMessages().get("self-votepoint")
                        .replace("{0}", String.valueOf(CurrencyManager.getBalance(uuid))));
                return true;
            }
            if (args.length == 1) {
                if (!sender.hasPermission("rankgui.command.votepoint.others")) {
                    sender.sendMessage(Main.getInstance().getMessages().get("no-permission"));
                    return true;
                }
                @SuppressWarnings("deprecation") OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                if (!offlinePlayer.hasPlayedBefore()) {
                    commandSender.sendMessage(Main.getInstance().getMessages().get("player-never-joined"));
                    return true;
                }
                UUID uuid = offlinePlayer.getUniqueId();
                if (!Main.getInstance().getBalances().containsKey(uuid)) {
                    commandSender.sendMessage(Main.getInstance().getMessages().get("player-never-joined"));
                    return true;
                }
                commandSender.sendMessage(Main.getInstance().getMessages().get("other-votepoint")
                        .replace("{0}", String.valueOf(CurrencyManager.getBalance(uuid)))
                        .replace("{1}", Objects.requireNonNull(offlinePlayer.getName())));
                return true;
            }
            if (args.length == 3) {
                if (!sender.hasPermission("rankgui.command.votepoint.admin")) {
                    sender.sendMessage(Main.getInstance().getMessages().get("no-permission"));
                    return true;
                }
                @SuppressWarnings("deprecation") OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                if (!offlinePlayer.hasPlayedBefore()) {
                    commandSender.sendMessage(Main.getInstance().getMessages().get("player-never-joined"));
                    return true;
                }
                UUID uuid = offlinePlayer.getUniqueId();
                if (!Main.getInstance().getBalances().containsKey(uuid)) {
                    commandSender.sendMessage(Main.getInstance().getMessages().get("player-never-joined"));
                    return true;
                }
                if (args[0].equalsIgnoreCase("add")) {
                    try {
                        int amount = Integer.parseInt(args[2]);
                        CurrencyManager.deposit(uuid, amount);
                        sender.sendMessage(Main.getInstance().getMessages().get("new-votepoint")
                                .replace("{0}", String.valueOf(CurrencyManager.getBalance(uuid)))
                                .replace("{1}", Objects.requireNonNull(offlinePlayer.getName())));
                    } catch (NumberFormatException e) {
                        commandSender.sendMessage(Main.getInstance().getMessages().get("invalid-amount"));
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("remove")) {
                    try {
                        int amount = Integer.parseInt(args[2]);
                        CurrencyManager.withdraw(uuid, amount);
                        sender.sendMessage(Main.getInstance().getMessages().get("new-votepoint")
                                .replace("{0}", String.valueOf(CurrencyManager.getBalance(uuid)))
                                .replace("{1}", Objects.requireNonNull(offlinePlayer.getName())));
                    } catch (NumberFormatException e) {
                        commandSender.sendMessage(Main.getInstance().getMessages().get("invalid-amount"));
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("set")) {
                    try {
                        int amount = Integer.parseInt(args[2]);
                        CurrencyManager.set(uuid, amount);
                        sender.sendMessage(Main.getInstance().getMessages().get("new-votepoint")
                                .replace("{0}", String.valueOf(CurrencyManager.getBalance(uuid)))
                                .replace("{1}", Objects.requireNonNull(offlinePlayer.getName())));
                    } catch (NumberFormatException e) {
                        commandSender.sendMessage(Main.getInstance().getMessages().get("invalid-amount"));
                    }
                    return true;
                }
                sender.sendMessage(Main.getInstance().getMessages().get("invalid-arguments"));
                sender.sendMessage(Main.getInstance().getMessages().get("display-usage"));
                return true;
            }
            sender.sendMessage(Main.getInstance().getMessages().get("invalid-arguments"));
        }
        // Console executing command
        else {
            if (args.length == 0) {
                commandSender.sendMessage("Invalid arguments.");
                return true;
            }
            if (args.length == 1) {
                @SuppressWarnings("deprecation") OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);
                if (!offlinePlayer.hasPlayedBefore()) {
                    commandSender.sendMessage(Main.getInstance().getMessages().get("player-never-joined"));
                    return true;
                }
                UUID uuid = offlinePlayer.getUniqueId();
                if (!Main.getInstance().getBalances().containsKey(uuid)) {
                    commandSender.sendMessage(Main.getInstance().getMessages().get("player-never-joined"));
                    return true;
                }
                commandSender.sendMessage(Main.getInstance().getMessages().get("other-votepoint")
                        .replace("{0}", String.valueOf(CurrencyManager.getBalance(uuid)))
                        .replace("{1}", Objects.requireNonNull(offlinePlayer.getName())));
                return true;
            }
            if (args.length == 3) {
                @SuppressWarnings("deprecation") OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                if (!offlinePlayer.hasPlayedBefore()) {
                    commandSender.sendMessage(Main.getInstance().getMessages().get("player-never-joined"));
                    return true;
                }
                UUID uuid = offlinePlayer.getUniqueId();
                if (!Main.getInstance().getBalances().containsKey(uuid)) {
                    commandSender.sendMessage(Main.getInstance().getMessages().get("player-never-joined"));
                    return true;
                }
                if (args[0].equalsIgnoreCase("add")) {
                    try {
                        int amount = Integer.parseInt(args[2]);
                        CurrencyManager.deposit(uuid, amount);
                        commandSender.sendMessage(Main.getInstance().getMessages().get("new-votepoint")
                                .replace("{0}", String.valueOf(CurrencyManager.getBalance(uuid)))
                                .replace("{1}", Objects.requireNonNull(offlinePlayer.getName())));
                    } catch (NumberFormatException e) {
                        commandSender.sendMessage(Main.getInstance().getMessages().get("invalid-amount"));
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("remove")) {
                    try {
                        int amount = Integer.parseInt(args[2]);
                        CurrencyManager.withdraw(uuid, amount);
                        commandSender.sendMessage(Main.getInstance().getMessages().get("new-votepoint")
                                .replace("{0}", String.valueOf(CurrencyManager.getBalance(uuid)))
                                .replace("{1}", Objects.requireNonNull(offlinePlayer.getName())));
                    } catch (NumberFormatException e) {
                        commandSender.sendMessage(Main.getInstance().getMessages().get("invalid-amount"));
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("set")) {
                    try {
                        int amount = Integer.parseInt(args[2]);
                        CurrencyManager.set(uuid, amount);
                        commandSender.sendMessage(Main.getInstance().getMessages().get("new-votepoint")
                                .replace("{0}", String.valueOf(CurrencyManager.getBalance(uuid)))
                                .replace("{1}", Objects.requireNonNull(offlinePlayer.getName())));
                    } catch (NumberFormatException e) {
                        commandSender.sendMessage(Main.getInstance().getMessages().get("invalid-amount"));
                    }
                    return true;
                }
                commandSender.sendMessage(Main.getInstance().getMessages().get("invalid-arguments"));
                commandSender.sendMessage(Main.getInstance().getMessages().get("display-usage"));
                return true;
            }
            commandSender.sendMessage(Main.getInstance().getMessages().get("invalid-arguments"));
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return List.of(" ");
    }
}
