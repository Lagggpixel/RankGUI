package me.lagggpixel.rankgui.managers;

import me.lagggpixel.rankgui.Main;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.Node;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RankManager {

    public static LuckPerms luckPerms = LuckPermsProvider.get();

    public static String getPlayerRank(Player player) {
        for (int i = Main.getInstance().getRanks().size() - 1; i >= 0; i--) {
            if (player.hasPermission(Main.getInstance().getRanks().get(i))) return Main.getInstance().getRanks().get(i);
        }
        return null;
    }

    public static String getPlayerNextRank(Player player) {
        String currentRank = getPlayerRank(player);
        if (currentRank == null) return null;
        int i = Main.getInstance().getRanks().indexOf(currentRank);
        if (i+1==Main.getInstance().getRanks().size()) return null;
        return Main.getInstance().getRanks().get(i+1);
    }

    public static String getPlayerRankName(Player player) {
        String rank = getPlayerRank(player);
        if (rank == null) return null;
        rank = rank.substring(6);
        return rank;
    }

    public static String getRankName(String rank) {
        if (rank == null) return null;
        rank = rank.substring(6);
        return rank;
    }

    public static void addPermission(UUID uuid, String permission) {
        luckPerms.getUserManager().modifyUser(uuid, user -> {
            user.data().add(Node.builder(permission).build());
        });
    }

    public static void removePermission(UUID uuid, String permission) {
        luckPerms.getUserManager().modifyUser(uuid, user -> {
            user.data().remove(Node.builder(permission).build());
        });
    }
}
