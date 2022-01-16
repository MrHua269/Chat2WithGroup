package server.natural.command;

import me.albert.amazingbot.bot.Bot;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static server.natural.Utils.config;
//This one need to add the CoolDownTime because some silly bitch may use it to send some useless 's message
//todo add cooldowntime
public class CommandInvite implements CommandExecutor {
    String group = String.valueOf(config.getLong("group"));
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        if (sender instanceof Player){
//            Bot.getApi().sendGroupMsg(group,"The Player " + sender.getName() + "invite you guys to our server to play");
            Bot.getApi().sendGroupMsg(group,"玩家" + sender.getName() + "邀请你们去服务器玩");
            return true;

        }else{
            sender.sendMessage(ChatColor.RED + "你不是一个玩家");
            return true;

        }

    }
}
