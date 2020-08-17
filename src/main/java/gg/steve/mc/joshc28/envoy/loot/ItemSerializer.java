package gg.steve.mc.joshc28.envoy.loot;

import gg.steve.mc.joshc28.envoy.framework.yml.PluginFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSerializer {
    private static int maxSlot;

    public static void saveItemsToFile(PluginFile file, Map<Double, List<ItemStack>> items) {
        YamlConfiguration config = file.get();
        for (String entry : config.getKeys(false)) {
            config.set(entry, null);
        }
        int slot = 0;
        for (double d : items.keySet()) {
            for (ItemStack item : items.get(d)) {
                config.set(slot + ".item", item);
                config.set(slot + ".chance", d);
                slot++;
            }
        }
        file.save();
    }

    public static Map<Double, List<ItemStack>> loadItemsFromFile(PluginFile file) {
        Map<Double, List<ItemStack>> items = new HashMap<>();
        YamlConfiguration config = file.get();
        int slot = 0;
        for (String entry : config.getKeys(false)) {
            double chance = config.getDouble(entry + ".chance");
            items.putIfAbsent(chance, new ArrayList<>());
            items.get(chance).add(config.getItemStack(slot + ".item", null));
            slot++;
        }
        maxSlot = slot;
        return items;
    }

    public static int getMaxSlot() {
        return maxSlot;
    }
}
