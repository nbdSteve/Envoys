package gg.steve.mc.joshc28.envoy.cmd.subs;

import gg.steve.mc.joshc28.envoy.framework.cmd.SubCommand;
import gg.steve.mc.joshc28.envoy.framework.message.DebugMessage;
import gg.steve.mc.joshc28.envoy.framework.message.GeneralMessage;
import gg.steve.mc.joshc28.envoy.framework.permission.PermissionNode;
import gg.steve.mc.joshc28.envoy.timer.EnvoyTimerManager;
import org.bukkit.command.CommandSender;

public class SetSubCmd extends SubCommand {

    public SetSubCmd() {
        super("set", 2, 2, false, PermissionNode.SET);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        int interval;
        try {
            interval = Integer.parseInt(args[1]);
            if (interval <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            DebugMessage.INVALID_AMOUNT.message(sender);
            return true;
        }
        EnvoyTimerManager.setTime(interval);
        GeneralMessage.UPDATE_INTERVAL.message(sender, args[1]);
        return true;
    }
}
