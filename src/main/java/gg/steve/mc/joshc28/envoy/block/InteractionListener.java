package gg.steve.mc.joshc28.envoy.block;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractionListener implements Listener {

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block block = event.getClickedBlock();
        if (!EnvoyBlockManager.isEnvoyBlock(block)) return;
        event.setCancelled(true);
        EnvoyBlock envoyBlock = EnvoyBlockManager.getEnvoyBlockFromBlock(block);
        if (envoyBlock.doReward(event.getPlayer())) {
            event.getPlayer().sendMessage(ChatColor.GOLD + "You have looted an envoy crate");
        }
    }
}
