package server.natural.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;
import server.natural.Utils;

import java.io.File;
import java.io.IOException;

public class CommandCWG implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings[0].toLowerCase() == "version") {
            strings[0] = "ver";
        }
        switch (strings[0].toLowerCase()) {
            case "reload": {
                if (commandSender.hasPermission("cwg.reload")) {
                    commandSender.sendMessage(ChatColor.GREEN + "Reloading Config File...");
                    File configFile = new File("plugins\\ChatWithGroup\\config.yml");
                    if (configFile.exists()) {
                        try {
                            Utils.config.load(configFile);
                        } catch (Exception e) {
                            commandSender.sendMessage(ChatColor.RED + "An Exception happened. Config file can't reload");
                            commandSender.sendMessage(ChatColor.RED + "Exception Message:");
                            commandSender.sendMessage(ChatColor.RED + e.getMessage());
                        }
                    }
                }else {
                    commandSender.sendMessage(ChatColor.RED + "您没有权限");
                }
            }
            case "ver":{
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
                }
                else{
                    commandSender.sendMessage(ChatColor.RED + "你没有权限");
                }
            }
            default:{
                if(commandSender.hasPermission("cwg.reload")||commandSender.hasPermission("cwg.ver")){
                    commandSender.sendMessage(ChatColor.RED + "你隔着说啥呢");
                    commandSender.sendMessage(ChatColor.RED + "正确使用方法: /cwg [ver/reload]");
                }else{
                    commandSender.sendMessage(ChatColor.RED + "您没有权限");
                }

            }
        }
        return true;
    }
}
