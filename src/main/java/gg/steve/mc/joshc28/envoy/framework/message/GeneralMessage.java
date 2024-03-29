package gg.steve.mc.joshc28.envoy.framework.message;

import gg.steve.mc.joshc28.envoy.framework.utils.ColorUtil;
import gg.steve.mc.joshc28.envoy.framework.utils.actionbarapi.ActionBarAPI;
import gg.steve.mc.joshc28.envoy.framework.yml.Files;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public enum GeneralMessage {
    RELOAD("reload"),
    HELP("help"),
    LOOT_ENVOY_CRATE("loot-envoy-crate"),
    ENVOY_START_PLAYER("envoy-start-player"),
    ADD_ENVOY_BLOCK("add-envoy-block"),
    REMOVE_ENVOY_BLOCK("remove-envoy-block"),
    TIME_BROADCAST("time-broadcast", "{hours}", "{minutes}"),
    START_BROADCAST("start-broadcast"),
    END_BROADCAST("end-broadcast"),
    TIME_QUERY("time-query", "{hours}", "{minutes}"),
    ENVOY_ACTIVE("envoy-active"),
    NO_ENVOY_ACTIVE("no-envoy-active"),
    ADD_LOOT_ITEM("add-loot-item"),
    GIVE_GIVER("give-giver", "{amount}", "{type}", "{target}"),
    GIVE_RECEIVER("give-receiver", "{amount}", "{type}"),
    UPDATE_INTERVAL("update-interval", "{interval}"),
    ;

    private String path;
    private boolean actionBar;
    private List<String> placeholders;

    GeneralMessage(String path, String... placeholders) {
        this.path = path;
        this.placeholders = Arrays.asList(placeholders);
        this.actionBar = Files.MESSAGES.get().getBoolean(this.path + ".action-bar");
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void message(Player receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar) {
            for (String line : Files.MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                if (!ActionBarAPI.sendActionBar(receiver, ColorUtil.colorize(line))) {
                    receiver.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ColorUtil.colorize(line)));
                }
            }
        } else {
            for (String line : Files.MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }

    public void message(CommandSender receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar && receiver instanceof Player) {
            for (String line : Files.MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                if (!ActionBarAPI.sendActionBar((Player) receiver, ColorUtil.colorize(line))) {
                    ((Player) receiver).spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ColorUtil.colorize(line)));
                }
            }
        } else {
            for (String line : Files.MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }

    public void broadcast(JavaPlugin instance, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar) {
            for (String line : Files.MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                ActionBarAPI.sendActionBarToAllPlayers(ColorUtil.colorize(line), instance);
            }
        } else {
            for (String line : Files.MESSAGES.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                Bukkit.broadcastMessage(ColorUtil.colorize(line));
            }
        }
    }

    public static void doMessage(Player receiver, List<String> lines) {
        for (String line : lines) {
            receiver.sendMessage(ColorUtil.colorize(line));
        }
    }
}