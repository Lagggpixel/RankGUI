package me.lagggpixel.rankgui;

import lombok.Getter;
import me.lagggpixel.rankgui.commands.RankCommand;
import me.lagggpixel.rankgui.commands.VotePointsCommand;
import me.lagggpixel.rankgui.currenices.BalanceFileManager;
import me.lagggpixel.rankgui.listeners.InventoryClickListener;
import me.lagggpixel.rankgui.listeners.PlayerJoinListener;
import me.lagggpixel.rankgui.managers.ConfigDataManager;
import me.lagggpixel.rankgui.managers.MessagesFileManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public final class Main extends JavaPlugin {

    static Main INSTANCE;

    Economy economy = null;
    Map<UUID, Integer> balances;
    ArrayList<String> ranks = new ArrayList<>();
    HashMap<String, Integer> rankVoteReq = new HashMap<>();
    HashMap<String, Integer> rankCost = new HashMap<>();

    HashMap<String, String> messages = new HashMap<>();

    @Override
    public void onEnable() {

        INSTANCE = this;

        setupConfigs();
        setupCurrency();
        setupListeners();
        setupCommand();

        setupEconomy();
    }

    @Override
    public void onDisable() {
        BalanceFileManager.getInstance().saveBalances();
    }

    private void setupCommand() {
        new RankCommand();
        new VotePointsCommand();
    }
    private void setupConfigs() {
        Main.getInstance().getConfig().options().copyDefaults(true);
        Main.getInstance().saveDefaultConfig();
        new MessagesFileManager();
        MessagesFileManager.getInstance().loadConfig();
        new ConfigDataManager();
        new BalanceFileManager();
        ConfigDataManager.getInstance().refreshData();
    }
    private void setupListeners() {
        new PlayerJoinListener();
        new InventoryClickListener();
    }
    private void setupCurrency() {
        balances = new HashMap<>();
        BalanceFileManager.getInstance().loadBalances();
    }
    private void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        economy = rsp.getProvider();
    }
    public static Main getInstance() {
        return INSTANCE;
    }
}
