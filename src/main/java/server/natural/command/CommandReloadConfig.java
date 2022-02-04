package server.natural.command;

import org.bukkit.ChatColor;
import org.bukkit.Utility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import server.natural.Utils;

import java.io.File;

public class CommandReloadConfig implements CommandExecutor {
    //todo 将CWGVer和其整合在一起
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.isOp()){
            if(strings[0].toLowerCase().equals("reload")&&strings.length==1){
                try {
                    commandSender.sendMessage(ChatColor.GREEN+"Reloading config file...");
                    File configFile = new File("plugins\\ChatWithGroup\\config.yml");
                    if (configFile.exists()) {
                        Utils.config.load(configFile);
                    }
                    commandSender.sendMessage(ChatColor.GREEN+"ConfigFile reloaded!");
                }catch (Exception e){
                    commandSender.sendMessage(ChatColor.RED+"An Exception happened.Config file can't reload.Exception message:"+e.getMessage());}
            }

        }
        return true;
    }
}
