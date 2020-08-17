package gg.steve.mc.joshc28.envoy.cmd.subs;

import gg.steve.mc.joshc28.envoy.framework.cmd.SubCommand;
import gg.steve.mc.joshc28.envoy.framework.permission.PermissionNode;
import gg.steve.mc.joshc28.envoy.framework.utils.TimeUtil;
import gg.steve.mc.joshc28.envoy.timer.EnvoyTimerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TimeSubCmd extends SubCommand {

    public TimeSubCmd() {
        super("time", 0, 1, false, PermissionNode.TIME);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (EnvoyTimerManager.isActive()) {
            sender.sendMessage(ChatColor.GOLD + "There is an envoy happening right now!");
        } else {
            TimeUtil time = EnvoyTimerManager.getTime();
            sender.sendMessage(ChatColor.GOLD + "A new envoy will spawn in " + time.getHours() + " hours, " + time.getMinutes() + " minutes.");
        }
        return true;
    }
}
