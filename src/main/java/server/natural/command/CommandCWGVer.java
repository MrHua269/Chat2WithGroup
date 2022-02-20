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
        if(commandSender.hasPermission("cwg.ver")){
            commandSender.sendMessage("Server Version: " + Bukkit.getVersion());
            commandSender.sendMessage("Server Port: " + Bukkit.getPort());
            commandSender.sendMessage("CWG Version: " + Utils.ver);
            commandSender.sendMessage("Is it a Beta Version: " + Utils.isBetaVersion);
            commandSender.sendMessage("The config-ver in the config.yml of CWG is：" + Utils.config.getInt("config-ver"));
            commandSender.sendMessage("The Group I listen: " + Utils.config.getLong("CoreConfig.group"));
            commandSender.sendMessage("The owner of this robot: " + Utils.config.getLong("CoreConfig.owner"));
            commandSender.sendMessage("The Thread Number I register: " + Utils.executor.getCorePoolSize());
            commandSender.sendMessage("The Task I Done(Only Count Event Task): " + Utils.executor.getTaskCount());
            commandSender.sendMessage("ChatWithGroup v" + Utils.ver + " was made by NaT_Jerry and wangxyper");
            return true;
        }
        else{
            commandSender.sendMessage(ChatColor.RED + "你没有权限");
            return false;
        }

    }
}
