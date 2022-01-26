package server.natural.command;

import me.albert.amazingbot.bot.Bot;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import server.natural.Utils;
//todo add cooldown time
public class CommandSMG implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] args) {
        if(commandSender instanceof Player){
            if(args[0]!=null&&args.length==1){
            Bot.getApi().sendGroupMsg(Utils.groupInString,commandSender.getName() + "->" + args[0]);
            commandSender.sendMessage("消息发送成功!");
            return true;
            }else{
                commandSender.sendMessage(ChatColor.RED + "使用方法错误，正确方法为:");
                return false;
            }
        }else{
            Bot.getApi().sendGroupMsg(Utils.groupInString, "服务器闸总(Console)->" + args[0]);
        }
        return true;
    }
}
