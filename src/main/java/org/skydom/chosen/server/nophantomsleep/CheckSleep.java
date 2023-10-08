package org.skydom.chosen.server.nophantomsleep;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class CheckSleep implements Listener {
    private NoPhantomSleep plugin;

    public CheckSleep(NoPhantomSleep plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        // 当一个玩家进入床时，将他们的名称添加到列表中
        Player player = event.getPlayer();
        plugin.getSleepingPlayers().add(player.getName());
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        // 在这里处理幻翼刷新事件，可以访问plugin.getSleepingPlayers()来获取玩家列表
        if (event.getEntityType() == EntityType.PHANTOM) {
            Entity entity = event.getEntity();
            for (String playerName : plugin.getSleepingPlayers()) {
                Player player = plugin.getServer().getPlayerExact(playerName);
                if (player != null && player.getWorld() == entity.getWorld()
                        && player.getLocation().distance(entity.getLocation()) < 64) {
                    long time = System.currentTimeMillis();
                    long sleepingTime = plugin.getSleepingTime(playerName); // 获取睡觉时间
                    if (time - sleepingTime < 1000 * 60 * 30) {
                        event.setCancelled(true);
                    } else {
                        plugin.removeSleepingPlayer(playerName); // 移除睡觉玩家
                    }
                }
            }
        }
    }
}