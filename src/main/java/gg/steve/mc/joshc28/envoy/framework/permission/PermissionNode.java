package gg.steve.mc.joshc28.envoy.framework.permission;

import gg.steve.mc.joshc28.envoy.framework.yml.Files;
import org.bukkit.command.CommandSender;

public enum PermissionNode {
    // cmd
    RELOAD("command.reload"),
    HELP("command.help"),
    START("command.start"),
    STOP("command.stop"),
    SET("command.set"),
    TIME("command.time"),
    GIVE("command.give"),
    ADD("command.add"),
    VIEW("command.view")
    ;

    private String path;

    PermissionNode(String path) {
        this.path = path;
    }

    public String get() {
        return Files.PERMISSIONS.get().getString(this.path);
    }

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(get());
    }

    public static boolean isPurchasePerms() {
        return Files.PERMISSIONS.get().getBoolean("purchase.enabled");
    }
}
