package me.lagggpixel.rankgui.managers;

import me.lagggpixel.rankgui.Main;
import me.lagggpixel.rankgui.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class ConfigDataManager {

    static ConfigDataManager INSTANCE;

    int rankGuiSize = 4*9;
    ItemStack rankGuiFillerItem;
    boolean rankGuiFillerAll;

    public ConfigDataManager() {
        INSTANCE = this;
    }

    public void refreshData() {
        rankGuiFillerAll = Main.getInstance().getConfig().getBoolean("rank-gui-filler-all");

        rankGuiFillerItem = new ItemBuilder(
                Material.getMaterial(
                        Objects.requireNonNull(
                                Main.getInstance().getConfig().getString("rank-gui-filler-item.material")
                        )
                )
        )
                .setDisplayName(Main.getInstance().getConfig().getString("rank-gui-filler-item.name"))
                .build();

        Main.getInstance().getRanks().clear();
        Main.getInstance().getRankVoteReq().clear();
        Main.getInstance().getRankCost().clear();

        ConfigurationSection ranksSection = Main.getInstance().getConfig().getConfigurationSection("ranks");
        for (String rankName : Objects.requireNonNull(ranksSection).getKeys(false)) {
            ConfigurationSection rank = ranksSection.getConfigurationSection(rankName);
            if (rank != null) {
                String groupName = rank.getString("group");
                int voteReq = rank.getInt("vote_req");
                int cost = rank.getInt("cost");
                Main.getInstance().getRanks().add(groupName);
                Main.getInstance().getRankVoteReq().put(groupName, voteReq);
                Main.getInstance().getRankCost().put(groupName, cost);
            }
        }

    }

    public static ConfigDataManager getInstance() {
        return INSTANCE;
    }

}
