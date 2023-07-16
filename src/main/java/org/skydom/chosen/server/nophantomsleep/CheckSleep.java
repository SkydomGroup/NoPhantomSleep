package org.skydom.chosen.server.nophantomsleep;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.util.HashMap;
import java.util.Map;

public class CheckSleep implements Listener {
    // 一个用来存储已经睡觉的玩家和他们睡觉的时间的映射
    private Map<Player, Long> sleepers = new HashMap<>();

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        // 当一个玩家进入床时，把他们加入到睡觉的玩家映射中，记录当前时间
        Player player = event.getPlayer();
        long time = System.currentTimeMillis();
        sleepers.put(player, time);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        // 当一个实体刷新时，检查是否是幻翼
        if (event.getEntityType() == EntityType.PHANTOM){
            // 获取刷新的实体
            Entity entity = event.getEntity();
            // 对于映射中的每一个玩家
            for (Player player : sleepers.keySet()) {
                // 如果玩家和实体在同一个世界，并且距离小于64格
                if (player.getWorld() == entity.getWorld() && player.getLocation().distance(entity.getLocation()) < 64) {
                    // 获取当前时间
                    long time = System.currentTimeMillis();
                    // 如果时间差小于30分钟（以毫秒为单位）
                    if (time - sleepers.get(player) < 1000 * 60 * 30) {
                        // 取消实体刷新事件
                        event.setCancelled(true);
                    } else if (time - sleepers.get(player) >= 1000 * 60 * 30) {
                        // 否则，如果时间差大于等于30分钟，把玩家从映射中移除
                        sleepers.remove(player);
                    }
                }
            }
        }
    }
}