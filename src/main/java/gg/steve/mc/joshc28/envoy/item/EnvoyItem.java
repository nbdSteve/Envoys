package gg.steve.mc.joshc28.envoy.item;

import gg.steve.mc.joshc28.envoy.framework.nbt.NBTItem;
import gg.steve.mc.joshc28.envoy.framework.utils.ItemBuilderUtil;
import gg.steve.mc.joshc28.envoy.framework.yml.Files;
import gg.steve.mc.joshc28.envoy.timer.EnvoyTimerManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EnvoyItem implements Listener {
    private static NBTItem item;

    public static void onLoad() {
        ConfigurationSection section = Files.CONFIG.get().getConfigurationSection("flare-item");
        ItemBuilderUtil builder = ItemBuilderUtil.getBuilderForMaterial(section.getString("material"), section.getString("data"));
        builder.addName(section.getString("name"));
        builder.addLore(section.getStringList("lore"));
        builder.addEnchantments(section.getStringList("enchantments"));
        builder.addItemFlags(section.getStringList("item-flags"));
        builder.addItemNBT(section);
        item = builder.getNbtItem();
    }

    public static ItemStack getItem() {
        return item.getItem();
    }

    public static boolean giveItem(Player player, int amount) {
        if (player.getInventory().firstEmpty() == -1) return false;
        for (int i = 0; i < amount; i++) {
            player.getInventory().addItem(item.getItem());
        }
        return true;
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player player = event.getPlayer();
        if (player.getItemInHand().getType() == Material.AIR) return;
        NBTItem item = new NBTItem(player.getItemInHand());
        if (!item.getBoolean("envoys.item")) return;
        Block block = event.getClickedBlock();
        event.setCancelled(true);
        if (Files.CONFIG.get().getStringList("blocked-worlds").contains(block.getWorld().getName())) {
            player.sendMessage("World blocked.");
            return;
        }
        if (player.getItemInHand().getAmount() > 1) {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        } else {
            player.setItemInHand(new ItemStack(Material.AIR));
        }
        player.updateInventory();
        EnvoyTimerManager.start();
        player.sendMessage(ChatColor.YELLOW + "You have started an envoy!");
    }
}
