package org.skydom.chosen.server.nophantomsleep;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class NoPhantomSleep extends JavaPlugin{
    @Override
    public void onEnable() {
        // 当插件启用时，调用这个方法
        // 注册事件监听器，传入一个 CheckSleep 类的实例和当前插件的实例
        Bukkit.getPluginManager().registerEvents(new CheckSleep(), this);
    }

    @Override
    public void onDisable() {
        // 当插件禁用时，调用这个方法
    }
}
