package gg.steve.mc.joshc28.envoy.loot;

import gg.steve.mc.joshc28.envoy.framework.gui.AbstractGui;
import gg.steve.mc.joshc28.envoy.framework.gui.utils.GuiItemUtil;
import gg.steve.mc.joshc28.envoy.framework.utils.ItemBuilderUtil;
import gg.steve.mc.joshc28.envoy.framework.utils.LogUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class LootGui extends AbstractGui {
    private static HashMap<Integer, LootGui> pages;
    private ConfigurationSection section;
    private int page;

    /**
     * Constructor the create a new Gui
     */
    public LootGui(ConfigurationSection section, int page) {
        super(section, section.getString("type"), section.getInt("size"), page + 1);
        this.section = section;
        this.page = page;
    }

    @Override
    public void refresh() {
        getInventory().clear();
        List<Integer> fillerSlots = section.getIntegerList("fillers.slots");
        ItemStack filler = GuiItemUtil.createItem(section.getConfigurationSection("fillers"));
        for (Integer slot : fillerSlots) {
            setItemInSlot(slot, filler, (player, clickType) -> {
            });
        }
        List<Integer> lootSlots = section.getIntegerList("envoy-item.slots");
        int start = this.page * lootSlots.get(lootSlots.size() - 1);
        int end = this.page * lootSlots.get(lootSlots.size() - 1) + lootSlots.get(lootSlots.size() - 1);
        for (Integer slot : lootSlots) {
            if (start > end) break;
            try {
                LootManager.getGuiLoot().get(start);
            } catch (Exception e) {
                break;
            }
            ItemBuilderUtil builder = new ItemBuilderUtil(new ItemStack(LootManager.getGuiLoot().get(start)));
            builder.setLorePlaceholders("{chance}");
            builder.addLore(section.getStringList("envoy-item.additional-lore"),
                    String.valueOf(LootManager.getChance(LootManager.getGuiLoot().get(start))));
            int finalStart = start;
            setItemInSlot(slot, builder.getItem(), (player, click) -> {
                LootManager.removeLootItem(LootManager.getGuiLoot().get(finalStart));
                refresh();
            });
            start++;
        }
        for (String entry : section.getKeys(false)) {
            try {
                Integer.parseInt(entry);
            } catch (NumberFormatException e) {
                continue;
            }
            ItemStack item;
            List<Integer> slots = section.getIntegerList(entry + ".slots");
            switch (section.getString(entry + ".action")) {
                case "previous":
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry));
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, (player, click) -> {
                            if (page > 0) {
                                GuiManager.openPage(player, page - 1);
                            }
                        });
                    }
                    break;
                case "next":
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry));
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, (player, click) -> {
                            try {
                                LootManager.getGuiLoot().get(end + 1);
                            } catch (Exception e) {
                                return;
                            }
                            GuiManager.openPage(player, this.page + 1);
                        });
                    }
                    break;
                case "close":
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry));
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, (player, click) -> {
                            player.closeInventory();
                        });
                    }
                    break;
                case "none":
                default:
                    item = GuiItemUtil.createItem(section.getConfigurationSection(entry));
                    for (Integer slot : slots) {
                        setItemInSlot(slot, item, (player, click) -> {
                        });
                    }
                    break;
            }
        }
    }
}
