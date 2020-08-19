package gg.steve.mc.joshc28.envoy.cmd.subs;

import gg.steve.mc.joshc28.envoy.framework.cmd.SubCommand;
import gg.steve.mc.joshc28.envoy.framework.message.GeneralMessage;
import gg.steve.mc.joshc28.envoy.framework.permission.PermissionNode;
import gg.steve.mc.joshc28.envoy.timer.EnvoyTimerManager;
import org.bukkit.command.CommandSender;

public class StartSubCmd extends SubCommand {

    public StartSubCmd() {
        super("start", 1, 1, false, PermissionNode.START);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!EnvoyTimerManager.start(false)) {
            GeneralMessage.ENVOY_ACTIVE.message(sender);
        }
        return true;
    }
}
