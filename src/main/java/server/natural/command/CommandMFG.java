package server.natural.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandMFG implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player){
            commandSender.sendMessage(ChatColor.GREEN + "本指令仍在开发...");
            return true;
        }else{
            commandSender.sendMessage(ChatColor.RED + "你不是玩家");
            return false;
        }

    }
}
