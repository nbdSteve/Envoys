package gg.steve.mc.joshc28.envoy.loot;

import gg.steve.mc.joshc28.envoy.framework.utils.LogUtil;
import gg.steve.mc.joshc28.envoy.framework.yml.Files;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class LootManager {
    private static Map<Double, List<ItemStack>> loot;
    private static Random random;
    private static List<ItemStack> guiLoot;

    public static void onLoad() {
        random = new Random();
        loot = new HashMap<>();
        Map<Double, List<ItemStack>> temp = ItemSerializer.loadItemsFromFile(Files.ENVOY_LOOT.getFile());
        List<Double> chances = new ArrayList<>(temp.keySet());
        Collections.sort(chances);
        for (Double raw : temp.keySet()) {
            for (double chance : chances) {
                if (chance == raw) loot.put(chance, temp.get(raw));
            }
        }
        LogUtil.info("Successfully loaded all envoy loot from file.");
        if (!chances.isEmpty()) chances.clear();
        if (!temp.isEmpty()) temp.clear();
    }

    public static void onShutdown() {
        if (loot != null && !loot.isEmpty()) {
            ItemSerializer.saveItemsToFile(Files.ENVOY_LOOT.getFile(), loot);
            LogUtil.info("Successfully saved all envoy loot to file.");
            loot.clear();
        }
    }

    public static ItemStack getRandomItem() {
        double chance = random.nextDouble();
        double b = 0;
        for (double d : loot.keySet()) {
            if (d >= chance) return loot.get(d).get(random.nextInt(loot.get(d).size()));
            b = d;
        }
        return loot.get(b).get(random.nextInt(loot.get(b).size()));
    }

    public static boolean addLootItem(ItemStack item, double chance) {
        loot.putIfAbsent(chance, new ArrayList<>());
        if (guiLoot != null) guiLoot.add(item);
        return loot.get(chance).add(item);
    }

    public static boolean removeLootItem(ItemStack item) {
        for (double d : loot.keySet()) {
            for (ItemStack stack : loot.get(d)) {
                if (item.equals(stack)) {
                    if (guiLoot != null) guiLoot.remove(item);
                    return loot.get(d).remove(stack);
                }
            }
        }
        return false;
    }

    public static Map<Double, List<ItemStack>> getLoot() {
        return loot;
    }

    public static List<ItemStack> getGuiLoot() {
        if (guiLoot == null) {
            guiLoot = new ArrayList<>();
            for (double d : loot.keySet()) {
                guiLoot.addAll(loot.get(d));
            }
        }
        return guiLoot;
    }

    public static double getChance(ItemStack item) {
        for (double d : loot.keySet()) {
            for (ItemStack stack : loot.get(d)) {
                if (stack.equals(item)) return d;
            }
        }
        return 0d;
    }
}
