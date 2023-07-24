package me.lagggpixel.rankgui.listeners;

import me.lagggpixel.rankgui.Main;
import me.lagggpixel.rankgui.currenices.CurrencyManager;
import me.lagggpixel.rankgui.managers.EconomyManager;
import me.lagggpixel.rankgui.managers.RankManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class InventoryClickListener implements Listener {

    public InventoryClickListener() {
        Main.getInstance().getServer().getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler(ignoreCancelled = true)
    public void InventoryClickEvent(org.bukkit.event.inventory.InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        // Server ranks gui
        if (event.getView().getTitle().equalsIgnoreCase("§x§f§f§0§0§0§0§lS§x§e§6§0§0§1§a§le§x§c§c§0§0§3§3§lr§x§b§3§0§0§4§d§lv§x§9§9§0§0§6§6§le§x§8§0§0§0§8§0§lr §x§6§6§0§0§9§9§lR§x§4§d§0§0§b§3§la§x§3§3§0§0§c§c§ln§x§1§a§0§0§e§6§lk§x§0§0§0§0§f§f§ls")) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null && event.getCurrentItem().getType().equals(Material.NETHER_STAR)
            && event.getCurrentItem().getItemMeta()!= null && event.getCurrentItem().getItemMeta().getDisplayName().contains("rank")) {

                // Code for rankup

                Player player = (Player) event.getWhoClicked();
                UUID uuid = player.getUniqueId();

                int votes = CurrencyManager.getBalance(uuid);
                double balance = EconomyManager.getBalance(player);

                String currentRank = RankManager.getPlayerRank(player);
                if (currentRank == null) {
                    player.sendMessage(Main.getInstance().getMessages().get("issue-getting-current-rank"));
                    return;
                }

                String nextRank = RankManager.getPlayerNextRank(player);

                if (nextRank == null) {
                    player.sendMessage(Main.getInstance().getMessages().get("already-have-top-rank"));
                    return;
                }
                String nextRankName = RankManager.getRankName(nextRank);

                int voteReq = Main.getInstance().getRankVoteReq().get(nextRank);
                int cost = Main.getInstance().getRankCost().get(nextRank);

                if (votes < voteReq) {
                    player.sendMessage(Main.getInstance().getMessages().get("not-enough-votepoints").replace("{0}", nextRankName));
                    return;
                }
                if (balance < cost) {

                    player.sendMessage(Main.getInstance().getMessages().get("not-enough-money").replace("{0}", nextRankName));
                    return;
                }

                if (!EconomyManager.withdraw(player, cost)) {
                    player.sendMessage(Main.getInstance().getMessages().get("error-withdrawing-money"));
                    return;
                }

                RankManager.addPermission(uuid, nextRank);
                RankManager.removePermission(uuid, currentRank);

                player.sendMessage(Main.getInstance().getMessages().get("purchased-rank").replace("{0}", nextRankName));
                player.closeInventory();
            }
        }

    }
}
