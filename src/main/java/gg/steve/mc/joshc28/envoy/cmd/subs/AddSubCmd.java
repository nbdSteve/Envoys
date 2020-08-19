package gg.steve.mc.joshc28.envoy.cmd.subs;

import gg.steve.mc.joshc28.envoy.framework.cmd.SubCommand;
import gg.steve.mc.joshc28.envoy.framework.message.DebugMessage;
import gg.steve.mc.joshc28.envoy.framework.message.GeneralMessage;
import gg.steve.mc.joshc28.envoy.framework.permission.PermissionNode;
import gg.steve.mc.joshc28.envoy.framework.utils.LogUtil;
import gg.steve.mc.joshc28.envoy.loot.LootManager;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddSubCmd extends SubCommand {

    public AddSubCmd() {
        super("add", 2, 2, true, PermissionNode.ADD);
        addAlias("a");
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        // envoys add 0.5
        Player player = getPlayer(sender);
        if (player.getItemInHand().getType() == Material.AIR) {
            LogUtil.info("Running");
            DebugMessage.MUST_BE_HOLDING_ITEM.message(sender);
            return true;
        }
        double chance;
        try {
            chance = Double.parseDouble(args[1]);
            if (chance <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            DebugMessage.INVALID_AMOUNT.message(sender);
            return true;
        }
        if (LootManager.addLootItem(player.getItemInHand(), chance)) {
            GeneralMessage.ADD_LOOT_ITEM.message(sender);
        } else {
            DebugMessage.UNEXPECTED_ERROR.message(sender);
        }
        return true;
    }
}
