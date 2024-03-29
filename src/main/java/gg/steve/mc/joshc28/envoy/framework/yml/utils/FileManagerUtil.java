package gg.steve.mc.joshc28.envoy.framework.yml.utils;

import gg.steve.mc.joshc28.envoy.framework.yml.PluginFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * Class that handles generating files for the plugin
 */
public class FileManagerUtil {
    //Store a hash map of the plugin files when they are loaded
    private static HashMap<String, PluginFile> files;
    //Store the main instance of the plugin
    private JavaPlugin instance;

    /**
     * Constructor to load the files
     *
     * @param instance Plugin, the main instance
     */
    public FileManagerUtil(JavaPlugin instance) {
        this.instance = instance;
        files = new HashMap<>();
    }

    /**
     * Adds a plugin file to the map of loaded files
     *
     * @param name String, the name of the file (i.e. config)
     * @param path String, the actual path for the file (i.e. config.yml)
     */
    public void add(String name, String path) {
        files.put(name, new YamlFileUtil().load(path, instance));
    }

    /**
     * Gets the respective yaml configuration for the file
     *
     * @param name String, the name of the file
     * @return YamlConfiguration
     */
    public static YamlConfiguration get(String name) {
        if (files.containsKey(name)) return files.get(name).get();
        return null;
    }

    public static PluginFile getFile(String name) {
        if (files.containsKey(name)) return files.get(name);
        return null;
    }

    /**
     * Saves the respective file
     *
     * @param name String, the name of the file
     */
    public static void save(String name) {
        if (files.containsKey(name)) files.get(name).save();
    }

    /**
     * Reloads all of the plugins files
     */
    public static void reload() {
        for (PluginFile file : files.values()) file.reload();
    }

    public static HashMap<String, PluginFile> getFiles() {
        return files;
    }
}
