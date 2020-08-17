package gg.steve.mc.joshc28.envoy.framework;

import gg.steve.mc.joshc28.envoy.block.EnvoyBlock;
import gg.steve.mc.joshc28.envoy.block.EnvoyBlockManager;
import gg.steve.mc.joshc28.envoy.block.InteractionListener;
import gg.steve.mc.joshc28.envoy.cmd.EnvoyCmd;
import gg.steve.mc.joshc28.envoy.framework.gui.GuiClickListener;
import gg.steve.mc.joshc28.envoy.framework.yml.Files;
import gg.steve.mc.joshc28.envoy.framework.yml.utils.FileManagerUtil;
import gg.steve.mc.joshc28.envoy.item.EnvoyItem;
import gg.steve.mc.joshc28.envoy.item.ToolItem;
import gg.steve.mc.joshc28.envoy.loot.GuiManager;
import gg.steve.mc.joshc28.envoy.loot.LootManager;
import gg.steve.mc.joshc28.envoy.timer.EnvoyTimerManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class that handles setting up the plugin on start
 */
public class SetupManager {
    private static FileManagerUtil fileManager;

    private SetupManager() throws IllegalAccessException {
        throw new IllegalAccessException("Manager class cannot be instantiated.");
    }

    /**
     * Loads the files into the file manager
     */
    public static void setupFiles(FileManagerUtil fm) {
        fileManager = fm;
        Files.CONFIG.load(fm);
        Files.PERMISSIONS.load(fm);
        Files.DEBUG.load(fm);
        Files.MESSAGES.load(fm);
        Files.ENVOY_DATA.load(fm);
        Files.ENVOY_LOOT.load(fm);
        Files.LOOT_GUI.load(fm);
    }

    public static void registerCommands(JavaPlugin instance) {
        instance.getCommand("envoy").setExecutor(new EnvoyCmd());
    }

    /**
     * Register all of the events for the plugin
     *
     * @param instance Plugin, the main plugin instance
     */
    public static void registerEvents(JavaPlugin instance) {
        PluginManager pm = instance.getServer().getPluginManager();
        pm.registerEvents(new GuiClickListener(), instance);
        pm.registerEvents(new EnvoyItem(), instance);
        pm.registerEvents(new ToolItem(), instance);
        pm.registerEvents(new InteractionListener(), instance);
    }

    public static void registerEvent(JavaPlugin instance, Listener listener) {
        instance.getServer().getPluginManager().registerEvents(listener, instance);
    }

    public static void loadPluginCache() {
        EnvoyTimerManager.onLoad();
        EnvoyBlockManager.onLoad();
        EnvoyItem.onLoad();
        ToolItem.onLoad();
        LootManager.onLoad();
    }

    public static void shutdownPluginCache() {
        GuiManager.onShutdown();
        LootManager.onShutdown();
        EnvoyBlockManager.onShutdown();
        EnvoyTimerManager.onShutdown();
    }

    public static FileManagerUtil getFileManager() {
        return fileManager;
    }
}
