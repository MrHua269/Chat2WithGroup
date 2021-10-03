package server.natural.command;

import me.albert.amazingbot.bot.Bot;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static server.natural.Utils.config;

public class CommandInvite implements CommandExecutor {
    String group = String.valueOf(config.getLong("group"));
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        if (sender instanceof Player){
            Bot.getApi().sendGroupMsg(group,"玩家" + sender.getName() + "邀请你们来服务器玩耍");
            return true;
        }else{
            sender.sendMessage(ChatColor.RED + "你不是人");
            return false;

        }

    }
}
