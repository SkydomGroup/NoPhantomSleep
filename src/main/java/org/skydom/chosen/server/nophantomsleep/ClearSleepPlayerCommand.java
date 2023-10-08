package org.skydom.chosen.server.nophantomsleep;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ClearSleepPlayerCommand implements CommandExecutor {
    private NoPhantomSleep plugin;

    public ClearSleepPlayerCommand(NoPhantomSleep plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("clearsleepplayer")) {
            // 检查玩家是否具有NoPhantomSleep.clearsleepplayer权限
            if (!sender.hasPermission("NoPhantomSleep.clearsleepplayer")) {
                sender.sendMessage(ChatColor.RED + "§l您没有执行此命令的权限！");
                return true;
            }

            // 执行清除睡觉玩家的逻辑
            long l = System.currentTimeMillis();
            plugin.getSleepingPlayers().clear();
            plugin.saveSleepingPlayers(); // 清除并保存玩家列表
            sender.sendMessage(ChatColor.GREEN + "§l已清除在列表中的玩家，耗时" + (System.currentTimeMillis() - l) + "ms。");
            return true;
        }
        return false;
    }
}