package org.skydom.chosen.server.nophantomsleep;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.util.ArrayList;
import java.util.List;

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
        if (event.getEntityType() == EntityType.PHANTOM) {
            Entity entity = event.getEntity();
            List<String> sleepingPlayersCopy = new ArrayList<>(plugin.getSleepingPlayers()); // 创建副本
            for (String playerName : sleepingPlayersCopy) {
                Player player = plugin.getServer().getPlayerExact(playerName);
                if (player != null && player.getWorld() == entity.getWorld()
                        && player.getLocation().distance(entity.getLocation()) < 64) {
                    long time = System.currentTimeMillis();
                    long sleepingTime = plugin.getSleepingTime(playerName);
                    if (time - sleepingTime < 1000 * 60 * 30) {
                        event.setCancelled(true);
                    } else {
                        plugin.removeSleepingPlayer(playerName);
                    }
                }
            }
        }
    }
}