package gg.steve.mc.joshc28.envoy.cmd.subs;

import gg.steve.mc.joshc28.envoy.framework.cmd.SubCommand;
import gg.steve.mc.joshc28.envoy.framework.message.GeneralMessage;
import gg.steve.mc.joshc28.envoy.framework.permission.PermissionNode;
import gg.steve.mc.joshc28.envoy.framework.utils.TimeUtil;
import gg.steve.mc.joshc28.envoy.timer.EnvoyTimerManager;
import org.bukkit.command.CommandSender;

public class TimeSubCmd extends SubCommand {

    public TimeSubCmd() {
        super("time", 0, 1, false, PermissionNode.TIME);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (EnvoyTimerManager.isActive()) {
            GeneralMessage.ENVOY_ACTIVE.message(sender);
        } else {
            TimeUtil time = EnvoyTimerManager.getTime();
            GeneralMessage.TIME_QUERY.message(sender, time.getHours(), time.getMinutes());
        }
        return true;
    }
}
