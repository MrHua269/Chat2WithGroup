package server.natural.command;

import me.albert.amazingbot.bot.Bot;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import server.natural.Utils;

import static server.natural.Utils.config;
//This one need to add the CoolDownTime because some silly bitch may use it to send some useless 's message
//todo add cooldowntime
public class CommandInvite implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        if (sender instanceof Player){
            Bot.getApi().sendGroupMsg(Utils.groupInString,"玩家" + sender.getName() + "邀请你们去服务器玩");
            sender.sendMessage("邀请已成功发送至群");
            return true;
        }else{
            sender.sendMessage(ChatColor.RED + "你不是一个玩家");
            return true;

        }

    }
}
