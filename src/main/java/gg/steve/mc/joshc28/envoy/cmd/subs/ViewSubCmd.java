package gg.steve.mc.joshc28.envoy.cmd.subs;

import gg.steve.mc.joshc28.envoy.framework.cmd.SubCommand;
import gg.steve.mc.joshc28.envoy.framework.permission.PermissionNode;
import gg.steve.mc.joshc28.envoy.loot.GuiManager;
import org.bukkit.command.CommandSender;

public class ViewSubCmd extends SubCommand {

    public ViewSubCmd() {
        super("view", 1, 1, true, PermissionNode.VIEW);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        GuiManager.openPage(getPlayer(sender), 0);
        return true;
    }
}
