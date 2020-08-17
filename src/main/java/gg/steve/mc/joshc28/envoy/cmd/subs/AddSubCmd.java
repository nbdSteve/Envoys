package gg.steve.mc.joshc28.envoy.cmd.subs;

import gg.steve.mc.joshc28.envoy.framework.cmd.SubCommand;
import gg.steve.mc.joshc28.envoy.framework.permission.PermissionNode;
import gg.steve.mc.joshc28.envoy.loot.LootManager;
import org.bukkit.ChatColor;
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
        if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
            player.sendMessage(ChatColor.RED + "You must be holding an item to add it to the envoy loot.");
            return true;
        }
        double chance;
        try {
            chance = Double.parseDouble(args[1]);
            if (chance <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Please enter a valid double that is greater than 0.");
            return true;
        }
        if (LootManager.addLootItem(player.getItemInHand(), chance)) {
            player.sendMessage(ChatColor.GREEN + "You have successfully added that item to the envoy loot table.");
        } else {
            player.sendMessage(ChatColor.RED + "An error occurred while add that item, please check the console for errors.");
        }
        return true;
    }
}
