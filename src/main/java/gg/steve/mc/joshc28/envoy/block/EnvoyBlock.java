package gg.steve.mc.joshc28.envoy.block;

import gg.steve.mc.joshc28.envoy.framework.yml.Files;
import gg.steve.mc.joshc28.envoy.loot.LootManager;
import gg.steve.mc.joshc28.envoy.timer.EnvoyTimerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EnvoyBlock {
    private UUID uuid;
    private World world;
    private int x, y, z;
    private Location location;
    private boolean active;

    public EnvoyBlock(UUID uuid, Location location) {
        this.uuid = uuid;
        this.world = location.getWorld();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.location = location;
        this.active = EnvoyTimerManager.isActive();
    }

    public EnvoyBlock(UUID uuid, ConfigurationSection config) {
        this.uuid = uuid;
        this.active = config.getBoolean("active");
        this.world = Bukkit.getWorld(config.getString("world"));
        this.x = config.getInt("x");
        this.y = config.getInt("y");
        this.z = config.getInt("z");
        this.location = new Location(this.world, this.x, this.y, this.z);
    }

    public void save() {
        if (Files.ENVOY_DATA.get().getConfigurationSection("envoy-blocks") == null) {
            Files.ENVOY_DATA.get().createSection("envoy-blocks");
        }
        ConfigurationSection config = Files.ENVOY_DATA.get().getConfigurationSection("envoy-blocks");
        if (config.getConfigurationSection(String.valueOf(this.uuid)) == null) {
            config.createSection(String.valueOf(this.uuid));
        }
        config = config.getConfigurationSection(String.valueOf(this.uuid));
        config.set("active", this.active);
        config.set("world", this.world.getName());
        config.set("x", this.x);
        config.set("y", this.y);
        config.set("z", this.z);
        Files.ENVOY_DATA.save();
    }

    public void purge() {
        ConfigurationSection config = Files.ENVOY_DATA.get().getConfigurationSection("envoy-blocks");
        if (config.getConfigurationSection(String.valueOf(this.uuid)) == null) return;
        config.set(String.valueOf(this.uuid), null);
        Files.ENVOY_DATA.save();
    }

    public void spawn() {
        Location spawn = this.location;
        spawn.setY(Files.CONFIG.get().getInt("spawn-height"));
        this.world.spawnFallingBlock(spawn, EnvoyBlockManager.getBlock().getType(), (byte) EnvoyBlockManager.getBlock().getDurability());
        this.active = true;
    }

    public void despawn() {
        this.active = false;
        this.world.getBlockAt(this.x, this.y, this.z).setType(Material.AIR);
    }

    public boolean doReward(Player player) {
        if (!this.active) return false;
        for (int i = 0; i < Files.CONFIG.get().getInt("items-per-envoy"); i++) {
            if (player.getInventory().firstEmpty() == -1) {
                player.getWorld().dropItemNaturally(player.getLocation(), LootManager.getRandomItem());
            } else {
                player.getInventory().addItem(LootManager.getRandomItem());
            }
        }
        despawn();
        if (!EnvoyBlockManager.isEnvoyBlocksActive()) EnvoyTimerManager.stop();
        return true;
    }

    public boolean isBlock(Block block) {
        return this.x == block.getX() && this.y == block.getY() && this.z == block.getZ();
    }

    public boolean isActive() {
        return active;
    }

    public UUID getUuid() {
        return uuid;
    }
}
