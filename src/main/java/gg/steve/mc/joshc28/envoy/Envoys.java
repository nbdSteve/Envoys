package gg.steve.mc.joshc28.envoy;

import gg.steve.mc.joshc28.envoy.framework.SetupManager;
import gg.steve.mc.joshc28.envoy.framework.utils.LogUtil;
import gg.steve.mc.joshc28.envoy.framework.yml.utils.FileManagerUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;

public final class Envoys extends JavaPlugin {
    private static Envoys instance;
    private static DecimalFormat numberFormat = new DecimalFormat("#,###.##");

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        LogUtil.setInstance(instance, true);
        SetupManager.setupFiles(new FileManagerUtil(instance));
        SetupManager.registerCommands(instance);
        SetupManager.registerEvents(instance);
        SetupManager.loadPluginCache();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        SetupManager.shutdownPluginCache();
    }

    public static Envoys getInstance() {
        return instance;
    }

    public static String formatNumber(double amount) {
        return numberFormat.format(amount);
    }
}
