package me.lagggpixel.rankgui.managers;

import me.lagggpixel.rankgui.Main;
import me.lagggpixel.rankgui.builders.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

public class RankGuiManager {
    public static Inventory createGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, ConfigDataManager.getInstance().rankGuiSize, "§x§f§f§0§0§0§0§lS§x§e§6§0§0§1§a§le§x§c§c§0§0§3§3§lr§x§b§3§0§0§4§d§lv§x§9§9§0§0§6§6§le§x§8§0§0§0§8§0§lr §x§6§6§0§0§9§9§lR§x§4§d§0§0§b§3§la§x§3§3§0§0§c§c§ln§x§1§a§0§0§e§6§lk§x§0§0§0§0§f§f§ls");

        for (int i = 0; i <= 9; i++) {
            inv.setItem(i, ConfigDataManager.getInstance().rankGuiFillerItem);
        }
        inv.setItem(17, ConfigDataManager.getInstance().rankGuiFillerItem);
        inv.setItem(18, ConfigDataManager.getInstance().rankGuiFillerItem);
        for (int i = 26; i <= 35; i++) {
            inv.setItem(i, ConfigDataManager.getInstance().rankGuiFillerItem);
        }

        ArrayList<ItemStack> rankItems = new ArrayList<>();

        ConfigurationSection ranksSection = Main.getInstance().getConfig().getConfigurationSection("ranks");
        for (String rankName : Objects.requireNonNull(ranksSection).getKeys(false)) {
            ConfigurationSection rank = ranksSection.getConfigurationSection(rankName);
            if (rank != null) {
                String groupName = rank.getString("group");
                int voteReq = rank.getInt("vote_req");
                int cost = rank.getInt("cost");
                ItemStack item = HeadManager.createSkull(Objects.requireNonNull(rank.getString("item.skull-id")));
                ItemMeta meta = item.getItemMeta();

                assert meta != null;
                meta.setDisplayName(rank.getString("item.name"));

                item.setItemMeta(meta);
                rankItems.add(item);
            }
        }

        int i = 0;
        for (ItemStack item : rankItems) {
            while (true) {
                if (inv.getItem(i) != null) {
                    i++;
                } else {
                    inv.setItem(i, item);
                    break;
                }
            }
        }
        String nextRank = RankManager.getRankName(RankManager.getPlayerNextRank(player));
        ItemStack rankupItem;
        if (nextRank != null) {
            int voteReq = Main.getInstance().getRankVoteReq().get(RankManager.getPlayerNextRank(player));
            int cost = Main.getInstance().getRankCost().get(RankManager.getPlayerNextRank(player));
            ArrayList<String> lore = new ArrayList<>();
            ArrayList<String> originalLore = (ArrayList<String>) Main.getInstance().getConfig().getList("rank-up-item.lore");
            if (originalLore != null) {
                for (String s : originalLore) {
                    String modifiedString = s
                            .replace("%cost%", String.valueOf(cost))
                            .replace("%vote_req%", String.valueOf(voteReq));
                    lore.add(modifiedString);
                }
            }
            rankupItem = new ItemBuilder(Material.getMaterial(Objects.requireNonNull(Main.getInstance().getConfig().getString("rank-up-item-top-rank.material"))))
                    .setDisplayName(Objects.requireNonNull(Main.getInstance().getConfig().getString("rank-up-item.name")).replace("%next_rank%", nextRank))
                    .setLore(lore)
                    .build();
        } else {
            rankupItem = new ItemBuilder(Material.getMaterial(Objects.requireNonNull(Main.getInstance().getConfig().getString("rank-up-item-top-rank.material"))))
                    .setDisplayName(Main.getInstance().getConfig().getString("rank-up-item-top-rank.name"))
                    .setLore((ArrayList<String>) Main.getInstance().getConfig().getList("rank-up-item-top-rank.lore"))
                    .build();
        }
        inv.setItem(31, rankupItem);


        return inv;
    }

}
