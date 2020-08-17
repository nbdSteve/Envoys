package gg.steve.mc.joshc28.envoy.cmd.subs;

import gg.steve.mc.joshc28.envoy.framework.cmd.SubCommand;
import gg.steve.mc.joshc28.envoy.framework.permission.PermissionNode;
import gg.steve.mc.joshc28.envoy.timer.EnvoyTimerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class StopSubCmd extends SubCommand {

    public StopSubCmd() {
        super("stop", 1, 1, false, PermissionNode.STOP);
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!EnvoyTimerManager.stop()) {
            sender.sendMessage(ChatColor.RED + "There is no envoy active.");
        } else {
            sender.sendMessage(ChatColor.YELLOW + "You have stopped the envoy.");
        }
        return true;
    }
}
