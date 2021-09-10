package server.natural.command;

import me.albert.amazingbot.bot.Bot;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import server.natural.Utils;

public class CommandSMG implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] args) {
        if(commandSender instanceof Player){
            if(args[0]!=null&&args.length==1){
            Bot.getApi().sendGroupMsg(String.valueOf(Utils.config.getLong("group")),commandSender.getName() + "->" + args[0]);
            commandSender.sendMessage("消息发送成功!");
            return true;
            }else{
                commandSender.sendMessage("错误的用法,正确用法:");
                return false;
            }
        }else{commandSender.sendMessage(ChatColor.RED+"您不是人:)");}
        return true;
    }
}
