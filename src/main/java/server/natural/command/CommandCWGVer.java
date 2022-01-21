package server.natural.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import server.natural.Utils;

public class CommandCWGVer implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender.isOp()){
            commandSender.sendMessage("Server Version: " + Bukkit.getVersion());
            commandSender.sendMessage("Server Port: " + Bukkit.getPort());
            commandSender.sendMessage("CWG Version: " + Utils.ver);
            commandSender.sendMessage("ChatWithGroup v1.3.3 was made by Jerry and wangxyper");
            return true;
        }
        else{
            commandSender.sendMessage(ChatColor.RED + "你不是管理员");
            return false;
        }

    }
}
