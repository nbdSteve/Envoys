package gg.steve.mc.joshc28.envoy.cmd.subs;

import gg.steve.mc.joshc28.envoy.framework.cmd.SubCommand;
import gg.steve.mc.joshc28.envoy.framework.permission.PermissionNode;
import gg.steve.mc.joshc28.envoy.item.EnvoyItem;
import gg.steve.mc.joshc28.envoy.item.ToolItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveSubCmd extends SubCommand {

    public GiveSubCmd() {
        super("give", 3, 4, false, PermissionNode.GIVE);
        addAlias("g");
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        // /envoy give player flare 1
        Player target;
        try {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) throw new Exception();
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Error, that player is not online.");
            return true;
        }
        int amt = 1;
        if (args.length == 4) {
            try {
                amt = Integer.parseInt(args[3]);
                if (amt <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Error, please enter a valid integer that is more than 0.");
                return true;
            }
        }
        if (args[2].equalsIgnoreCase("flare") || args[2].equalsIgnoreCase("f")) {
            EnvoyItem.giveItem(target, amt);
            target.sendMessage(ChatColor.GOLD + "You have received " + amt + "x envoy flare(s).");
        } else if (args[2].equalsIgnoreCase("tool") || args[2].equalsIgnoreCase("t")) {
            ToolItem.giveItem(target, amt);
            target.sendMessage(ChatColor.GOLD + "You have received " + amt + "x envoy editor tool(s).");
        } else {
            sender.sendMessage(ChatColor.RED + "Error, invalid item type, try 'tool' or 'flare'.");
            return true;
        }
        return true;
    }
}
