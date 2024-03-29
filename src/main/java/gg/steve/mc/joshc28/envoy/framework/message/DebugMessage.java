package gg.steve.mc.joshc28.envoy.framework.message;

import gg.steve.mc.joshc28.envoy.framework.utils.ColorUtil;
import gg.steve.mc.joshc28.envoy.framework.utils.actionbarapi.ActionBarAPI;
import gg.steve.mc.joshc28.envoy.framework.yml.Files;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public enum DebugMessage {
    INVALID_COMMAND("invalid-command"),
    INCORRECT_ARGS("incorrect-args"),
    INSUFFICIENT_PERMISSION("insufficient-permission", "{node}"),
    BLOCKED_WORLD("blocked-world"),
    MUST_BE_HOLDING_ITEM("must-be-holding-item"),
    INVALID_AMOUNT("invalid-amount"),
    UNEXPECTED_ERROR("unexpected-error"),
    TARGET_NOT_ONLINE("target-not-online"),
    INVALID_TYPE("invalid-type");

    private String path;
    private boolean actionBar;
    private List<String> placeholders;

    DebugMessage(String path, String... placeholders) {
        this.path = path;
        this.placeholders = Arrays.asList(placeholders);
        this.actionBar = Files.MESSAGES.get().getBoolean(this.path + ".action-bar");
    }

    public void message(Player receiver, String... replacements) {
        List<String> data = Arrays.asList(replacements);
        if (this.actionBar) {
            for (String line : Files.DEBUG.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                ActionBarAPI.sendActionBar(receiver, ColorUtil.colorize(line));
            }
        } else {
            for (String line : Files.DEBUG.get().getStringList(this.path + ".text")) {
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
            for (String line : Files.DEBUG.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replaceAll(this.placeholders.get(i), data.get(i));
                }
                ActionBarAPI.sendActionBar((Player) receiver, ColorUtil.colorize(line));
            }
        } else {
            for (String line : Files.DEBUG.get().getStringList(this.path + ".text")) {
                for (int i = 0; i < this.placeholders.size(); i++) {
                    line = line.replace(this.placeholders.get(i), data.get(i));
                }
                receiver.sendMessage(ColorUtil.colorize(line));
            }
        }
    }
}
