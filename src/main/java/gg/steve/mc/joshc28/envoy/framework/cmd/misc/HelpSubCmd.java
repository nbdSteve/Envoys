package gg.steve.mc.joshc28.envoy.framework.cmd.misc;

import gg.steve.mc.joshc28.envoy.framework.cmd.SubCommand;
import gg.steve.mc.joshc28.envoy.framework.message.GeneralMessage;
import gg.steve.mc.joshc28.envoy.framework.permission.PermissionNode;
import org.bukkit.command.CommandSender;

public class HelpSubCmd extends SubCommand {

    public HelpSubCmd() {
        super("help", 1, 1, false, PermissionNode.HELP);
        addAlias("h");
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        GeneralMessage.HELP.message(sender);
        return false;
    }
}
