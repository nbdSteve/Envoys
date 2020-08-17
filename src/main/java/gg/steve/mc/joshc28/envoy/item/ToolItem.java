package gg.steve.mc.joshc28.envoy.item;

import gg.steve.mc.joshc28.envoy.block.EnvoyBlock;
import gg.steve.mc.joshc28.envoy.block.EnvoyBlockManager;
import gg.steve.mc.joshc28.envoy.framework.nbt.NBTItem;
import gg.steve.mc.joshc28.envoy.framework.utils.ItemBuilderUtil;
import gg.steve.mc.joshc28.envoy.framework.yml.Files;
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

public class ToolItem implements Listener {
    private static NBTItem item;

    public static void onLoad() {
        ConfigurationSection section = Files.CONFIG.get().getConfigurationSection("tool-item");
        ItemBuilderUtil builder = ItemBuilderUtil.getBuilderForMaterial(section.getString("material"), section.getString("data"));
        builder.addName(section.getString("name"));
        builder.addLore(section.getStringList("lore"));
        builder.addEnchantments(section.getStringList("enchantments"));
        builder.addItemFlags(section.getStringList("item-flags"));
        builder.addToolNBT(section);
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
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK)) return;
        if (event.getItem() == null || event.getItem().getType() == Material.AIR) return;
        NBTItem item = new NBTItem(event.getItem());
        if (!item.getBoolean("envoys.tool")) return;
        Block block = event.getClickedBlock();
        event.setCancelled(true);
        switch (event.getAction()) {
            case RIGHT_CLICK_BLOCK:
                if (!EnvoyBlockManager.isEnvoyBlock(block)) return;
                EnvoyBlock envoyBlock = EnvoyBlockManager.getEnvoyBlockFromBlock(block);
                EnvoyBlockManager.removeEnvoyBlock(envoyBlock.getUuid());
                event.getPlayer().sendMessage(ChatColor.RED + "You have successfully removed an envoy block.");
                break;
            case LEFT_CLICK_BLOCK:
                if (EnvoyBlockManager.isEnvoyBlock(block)) return;
                EnvoyBlockManager.addEnvoyBlock(block.getLocation());
                event.getPlayer().sendMessage(ChatColor.GREEN + "You have successfully added an envoy block.");
                break;
        }
    }
}
