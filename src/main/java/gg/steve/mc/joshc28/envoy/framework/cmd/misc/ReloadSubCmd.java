package gg.steve.mc.joshc28.envoy.framework.cmd.misc;

import gg.steve.mc.joshc28.envoy.Envoys;
import gg.steve.mc.joshc28.envoy.framework.cmd.SubCommand;
import gg.steve.mc.joshc28.envoy.framework.message.GeneralMessage;
import gg.steve.mc.joshc28.envoy.framework.permission.PermissionNode;
import gg.steve.mc.joshc28.envoy.framework.yml.Files;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ReloadSubCmd extends SubCommand {

    public ReloadSubCmd() {
        super("reload", 1, 1, false, PermissionNode.RELOAD);
        addAlias("r");
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        Files.reload();
        Bukkit.getPluginManager().disablePlugin(Envoys.getInstance());
        Bukkit.getPluginManager().enablePlugin(Envoys.getInstance());
        GeneralMessage.RELOAD.message(sender);
        return false;
    }
}
