package gg.steve.mc.joshc28.envoy.framework.yml;

import gg.steve.mc.joshc28.envoy.framework.yml.utils.FileManagerUtil;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public enum Files {
    // generic
    CONFIG("envoys.yml"),
    // permissions
    PERMISSIONS("permissions.yml"),
    // lang
    DEBUG("lang" + File.separator + "debug.yml"),
    MESSAGES("lang" + File.separator + "messages.yml"),
    // gui
    LOOT_GUI("gui.yml"),
    //
    ENVOY_DATA("data" + File.separator + "envoy-data.yml"),
    ENVOY_LOOT("data" + File.separator + "envoy-loot.yml")
    ;

    private final String path;

    Files(String path) {
        this.path = path;
    }

    public void load(FileManagerUtil fileManager) {
        fileManager.add(name(), this.path);
    }

    public YamlConfiguration get() {
        return FileManagerUtil.get(name());
    }

    public PluginFile getFile() {
        return FileManagerUtil.getFile(name());
    }

    public void save() {
        FileManagerUtil.save(name());
    }

    public static void reload() {
        FileManagerUtil.reload();
    }
}
