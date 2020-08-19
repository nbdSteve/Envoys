package gg.steve.mc.joshc28.envoy.cmd.subs;

import gg.steve.mc.joshc28.envoy.framework.cmd.SubCommand;
import gg.steve.mc.joshc28.envoy.framework.message.DebugMessage;
import gg.steve.mc.joshc28.envoy.framework.message.GeneralMessage;
import gg.steve.mc.joshc28.envoy.framework.permission.PermissionNode;
import gg.steve.mc.joshc28.envoy.item.EnvoyItem;
import gg.steve.mc.joshc28.envoy.item.ToolItem;
import org.bukkit.Bukkit;
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
            if (target == null || !target.isOnline()) throw new Exception();
        } catch (Exception e) {
            DebugMessage.TARGET_NOT_ONLINE.message(sender);
            return true;
        }
        int amt = 1;
        if (args.length == 4) {
            try {
                amt = Integer.parseInt(args[3]);
                if (amt <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                DebugMessage.INVALID_AMOUNT.message(sender);
                return true;
            }
        }
        if (args[2].equalsIgnoreCase("flare") || args[2].equalsIgnoreCase("f")) {
            EnvoyItem.giveItem(target, amt);
            GeneralMessage.GIVE_RECEIVER.message(target, String.valueOf(amt), "flare");
            if (sender instanceof Player && sender != target) {
                GeneralMessage.GIVE_GIVER.message(sender, String.valueOf(amt), "flare", target.getName());
            }
        } else if (args[2].equalsIgnoreCase("tool") || args[2].equalsIgnoreCase("t")) {
            ToolItem.giveItem(target, amt);
            GeneralMessage.GIVE_RECEIVER.message(target, String.valueOf(amt), "editor tool");
            if (sender instanceof Player && sender != target) {
                GeneralMessage.GIVE_GIVER.message(sender, String.valueOf(amt), "editor tool", target.getName());
            }
        } else {
            DebugMessage.INVALID_TYPE.message(sender);
            return true;
        }
        return true;
    }
}
