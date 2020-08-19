package gg.steve.mc.joshc28.envoy.block;

import gg.steve.mc.joshc28.envoy.framework.utils.ItemBuilderUtil;
import gg.steve.mc.joshc28.envoy.framework.yml.Files;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnvoyBlockManager {
    private static Map<UUID, EnvoyBlock> blocks;
    private static ItemStack block;

    public static void onLoad() {
        blocks = new HashMap<>();
        block = ItemBuilderUtil.getBuilderForMaterial(Files.CONFIG.get().getString("block.material"), Files.CONFIG.get().getString("block.data")).getItem();
        if (Files.ENVOY_DATA.get().getConfigurationSection("envoy-blocks") == null) return;
        for (String entry : Files.ENVOY_DATA.get().getConfigurationSection("envoy-blocks").getKeys(false)) {
            UUID blockId = UUID.fromString(entry);
            blocks.put(blockId, new EnvoyBlock(blockId, Files.ENVOY_DATA.get().getConfigurationSection("envoy-blocks." + entry)));
        }
    }

    public static void onShutdown() {
        if (blocks == null || blocks.isEmpty()) return;
        for (UUID blockId : blocks.keySet()) {
            blocks.get(blockId).save();
        }
        blocks.clear();
    }

    public static void spawnAllBlocks() {
        if (blocks == null || blocks.isEmpty()) return;
        for (UUID blockId : blocks.keySet()) {
            blocks.get(blockId).spawn();
        }
    }

    public static void despawnAllBlocks() {
        if (blocks == null || blocks.isEmpty()) return;
        for (UUID blockId : blocks.keySet()) {
            blocks.get(blockId).despawn();
        }
    }

    public static boolean isEnvoyBlock(Block block) {
        if (blocks == null || blocks.isEmpty()) return false;
        for (UUID blockId : blocks.keySet()) {
            if (blocks.get(blockId).isBlock(block)) return true;
        }
        return false;
    }

    public static EnvoyBlock getEnvoyBlockFromBlock(Block block) {
        if (blocks == null || blocks.isEmpty()) return null;
        for (UUID blockId : blocks.keySet()) {
            if (blocks.get(blockId).isBlock(block)) return blocks.get(blockId);
        }
        return null;
    }

    public static boolean isEnvoyBlocksActive() {
        if (blocks == null || blocks.isEmpty()) return false;
        for (UUID blockId : blocks.keySet()) {
            if (blocks.get(blockId).isActive()) return true;
        }
        return false;
    }

    public static boolean removeEnvoyBlock(UUID blockId) {
        blocks.get(blockId).purge();
        return blocks.remove(blockId) != null;
    }

    public static boolean addEnvoyBlock(Location location) {
        if (isEnvoyBlock(location.getBlock())) return false;
        UUID blockId = UUID.randomUUID();
        blocks.put(blockId, new EnvoyBlock(blockId, location));
        return true;
    }

    public static ItemStack getBlock() {
        return block;
    }
}
