package server.natural.command;
import me.albert.amazingbot.bot.Bot;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import server.natural.Utils;

//todo add cooldowntime
//todo 使用命令前判断该玩家的QQ是否被禁言
public class CommandInvite implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        if(Utils.config.getBoolean("EnableInvite",true)){
            if (sender instanceof Player){
                if(Bot.getApi().getUser(((Player) sender).getUniqueId())!=null){
                    Utils.executor.runTaskAsynchronously(Utils.plugin,()-> Utils.group.forEach(group-> Bot.getApi().sendGroupMsg(group,"玩家" + ((Player) sender).getDisplayName() + "邀请你们去服务器玩")));
                    sender.sendMessage("邀请已成功发送至群");
                }else{
                    sender.sendMessage(ChatColor.RED + "无法发送邀请信息至群聊");
                    sender.sendMessage(ChatColor.RED + "因为您未与你的QQ绑定");
                    sender.sendMessage(ChatColor.RED + "请在群聊用QQ发送'绑定 [您的游戏ID]'后在服务器内进行相关操作后绑定");
                    sender.sendMessage(ChatColor.RED + "绑定后即可发送邀请信息");
                }

            }else{
                sender.sendMessage(ChatColor.RED + "你不是一个玩家");

            }
        }else{
            sender.sendMessage(ChatColor.RED + "邀请功能尚未开启");
        }

        return true;
    }
}
