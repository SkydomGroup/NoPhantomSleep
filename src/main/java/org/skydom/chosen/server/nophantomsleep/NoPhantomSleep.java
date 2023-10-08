package org.skydom.chosen.server.nophantomsleep;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class NoPhantomSleep extends JavaPlugin {
    private List<String> sleepingPlayers = new ArrayList<>(); // 存储玩家名称的列表

    @Override
    public void onEnable() {
        // 注册事件监听器
        Bukkit.getPluginManager().registerEvents(new CheckSleep(this), this);

        // 加载玩家列表
        loadSleepingPlayers();
    }

    @Override
    public void onDisable() {
        // 保存玩家列表
        saveSleepingPlayers();
    }

    // 获取玩家列表
    public List<String> getSleepingPlayers() {
        return sleepingPlayers;
    }

    // 加载玩家列表
    private void loadSleepingPlayers() {
        FileConfiguration config = getConfig();
        sleepingPlayers = config.getStringList("sleepingPlayers");
    }

    // 保存玩家列表
    private void saveSleepingPlayers() {
        FileConfiguration config = getConfig();
        config.set("sleepingPlayers", sleepingPlayers);
        saveConfig();
    }

    // 获取玩家的睡觉时间
    public long getSleepingTime(String playerName) {
        // 在这里实现根据玩家名称获取睡觉时间的逻辑，你可以自定义存储方式
        // 例如：通过配置文件、数据库等方式来获取睡觉时间
        // 返回玩家的睡觉时间（以毫秒为单位）
        return 0;
    }

    // 移除睡觉玩家
    public void removeSleepingPlayer(String playerName) {
        // 在这里实现移除睡觉玩家的逻辑，你可以自定义存储方式
        // 例如：从列表中移除、更新数据库等方式来移除睡觉玩家
        sleepingPlayers.remove(playerName);
    }
}