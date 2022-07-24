package server.natural.chatwithgroup.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import server.natural.chatwithgroup.Utils;

public class CommandPurgeCache implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("cwg.cache.purge")){
            Utils.LoadFile(true);
            Utils.OnFirstRun();
            //TODO 在此重新载入硬盘内缓存yml文件入内存中
            sender.sendMessage(ChatColor.GREEN + "缓存清理完成");
        }else{
            sender.sendMessage(ChatColor.RED + Utils.NoPermission);
        }
        return true;
    }
}
