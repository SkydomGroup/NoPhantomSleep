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
        Player player = event.getPlayer();
        String playerName = player.getName();

        // 如果玩家已经在列表中，先移除
        if (plugin.getSleepingPlayers().contains(playerName)) {
            plugin.removeSleepingPlayer(playerName);
        }

        // 然后将玩家添加到列表
        plugin.getSleepingPlayers().add(playerName);
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