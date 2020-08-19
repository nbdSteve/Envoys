package gg.steve.mc.joshc28.envoy.timer;

import gg.steve.mc.joshc28.envoy.Envoys;
import gg.steve.mc.joshc28.envoy.block.EnvoyBlockManager;
import gg.steve.mc.joshc28.envoy.framework.message.GeneralMessage;
import gg.steve.mc.joshc28.envoy.framework.utils.TimeUtil;
import gg.steve.mc.joshc28.envoy.framework.yml.Files;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class EnvoyTimerManager {
    private static int time;
    private static boolean active;
    private static BukkitTask task;

    public static void onLoad() {
        time = Files.ENVOY_DATA.get().getInt("time");
        active = Files.ENVOY_DATA.get().getBoolean("active");
        task = Bukkit.getScheduler().runTaskTimer(Envoys.getInstance(), () -> {
            if (active) return;
            switch (time) {
                case 30:
                case 10:
                case 5:
                case 1:
                    GeneralMessage.TIME_BROADCAST.broadcast(Envoys.getInstance(), getTime().getHours(), getTime().getMinutes());
                    break;
                case 0:
                    start(false);
                    break;
                default:
                    break;
            }
            time--;
        }, 0L, 1800L);
    }

    public static void onShutdown() {
        if (task != null && !task.isCancelled()) task.cancel();
        Files.ENVOY_DATA.get().set("active", active);
        Files.ENVOY_DATA.get().set("time", time);
        Files.ENVOY_DATA.save();
    }

    public static boolean start(boolean flare) {
        if (active) return false;
        EnvoyBlockManager.despawnAllBlocks();
        EnvoyBlockManager.spawnAllBlocks();
        active = true;
        if (!flare) time = Files.CONFIG.get().getInt("interval");
        GeneralMessage.START_BROADCAST.broadcast(Envoys.getInstance());
        return true;
    }

    public static boolean stop() {
        if (!active) return false;
        EnvoyBlockManager.despawnAllBlocks();
        active = false;
        //time = Files.CONFIG.get().getInt("interval");
        GeneralMessage.END_BROADCAST.broadcast(Envoys.getInstance());
        return true;
    }

    public static void setTime(int interval) {
        time = interval;
        Files.CONFIG.get().set("interval", interval);
        Files.CONFIG.save();
    }

    public static boolean isActive() {
        return active;
    }

    public static TimeUtil getTime() {
        return new TimeUtil(time * 60);
    }
}
