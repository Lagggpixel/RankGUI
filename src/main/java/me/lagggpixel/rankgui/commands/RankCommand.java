package me.lagggpixel.rankgui.commands;

import me.lagggpixel.rankgui.Main;
import me.lagggpixel.rankgui.managers.RankGuiManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RankCommand implements TabExecutor {

    @SuppressWarnings("DataFlowIssue")
    public RankCommand() {
        Main.getInstance().getCommand("rank").setExecutor(this);
        Main.getInstance().getCommand("rank").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] args) {

        if (commandSender instanceof Player sender) {
            sender.openInventory(RankGuiManager.createGui(sender));
        }
        else {
            commandSender.sendMessage(ChatColor.RED + "Only players can use this command.");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] args) {
        return List.of(" ");
    }
}
