package server.natural.command;

import me.albert.amazingbot.bot.Bot;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import server.natural.Utils;
//todo add cool-down time
//todo 使用命令前判断该玩家的QQ是否被禁言
//Go to fuck yourself , silly cool-down time
public class CommandSMG implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] args) {
        if(commandSender instanceof Player){
            if(Bot.getApi().getUser(((Player) commandSender).getUniqueId()) != null){
                if(args.length>0){
                    if(args[0]!=null&&args.length==1){
                        Bot.getApi().sendGroupMsg(Utils.groupInString,((Player) commandSender).getDisplayName() + "->" + args[0]);
                        commandSender.sendMessage("消息发送成功!");
                    }else{
                        commandSender.sendMessage(ChatColor.RED + "使用方法错误，正确方法为:");
                        commandSender.sendMessage(ChatColor.RED + "/smg [要说的话]");
                    }
                }else{
                    commandSender.sendMessage(ChatColor.RED + "格式错误");
                    commandSender.sendMessage(ChatColor.RED + "正确方法为: /smg [要说的话]");
                }
            }else{
                commandSender.sendMessage(ChatColor.RED + "无法发送消息至群聊");
                commandSender.sendMessage(ChatColor.RED + "因为您未与你的QQ绑定");
                commandSender.sendMessage(ChatColor.RED + "请在群聊用QQ发送'绑定 [您的游戏ID]'后在服务器内进行相关操作后绑定");
                commandSender.sendMessage(ChatColor.RED + "绑定后即可发送消息");
            }
        }else{
            Bot.getApi().sendGroupMsg(Utils.groupInString, "服务器闸总(Console)->" + args[0]);
        }
        return true;
    }
}
