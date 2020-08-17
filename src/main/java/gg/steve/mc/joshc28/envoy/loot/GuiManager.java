package gg.steve.mc.joshc28.envoy.loot;

import gg.steve.mc.joshc28.envoy.framework.yml.Files;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GuiManager {
    private static Map<Integer, LootGui> pages;

    public static void openPage(Player player, int page) {
        if (pages == null) pages = new HashMap<>();
        pages.putIfAbsent(page, new LootGui(Files.LOOT_GUI.get(), page));
        pages.get(page).refresh();
        pages.get(page).open(player);
    }

    public static void onShutdown() {
        if (pages != null && !pages.isEmpty()) pages.clear();
    }
}
