package gg.steve.mc.joshc28.envoy.cmd.subs;

import gg.steve.mc.joshc28.envoy.framework.cmd.SubCommand;
import gg.steve.mc.joshc28.envoy.framework.permission.PermissionNode;
import gg.steve.mc.joshc28.envoy.timer.EnvoyTimerManager;
import org.bukkit.ChatColor;
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
            sender.sendMessage(ChatColor.RED + "Error, please enter a valid integer that is more than 0.");
            return true;
        }
        EnvoyTimerManager.setTime(interval);
        sender.sendMessage(ChatColor.GREEN + "You have updated the interval time, envoys will now spawn every " + args[1] + " minutes.");
        return true;
    }
}
