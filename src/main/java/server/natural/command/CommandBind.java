package server.natural.command;

import me.albert.amazingbot.bot.Bot;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import server.natural.Utils;


public class CommandBind implements CommandExecutor {
    //Date:2022/3/13
    //将其改成群内通告
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            if(args.length == 1&& args[0]!=null && Utils.isNumberStrings(args[0])&&args.length>0){
                long QQID = Long.parseLong(args[0]);
                if(sender.hasPermission("cwg.bind.force")){
                    Bot.getApi().setBind(QQID,((Player) sender).getUniqueId());
                    sender.sendMessage(ChatColor.GREEN + "绑定成功");
                }else{
                    Utils.cacheFile.set("players." + sender.getName() + ".QQID",QQID);
                    Utils.cacheFile.set("players." + sender.getName() + ".ActionDone",false);
                    Utils.cacheFile.set("players." + sender.getName() + ".UUID",((Player) sender).getUniqueId().toString());
                    Utils.cacheFileSave();
                    final long[] g = {0};
                    for(long l:Utils.group){
                        if(Bot.getApi().getGroupMemberList(l).contains(QQID)){
                            g[0]=l;
                            break;
                        }
                    }
                    long lo = g[0];
                    Bot.getApi().sendGroupMsg(lo,"服务器有人请求与一位QQ用户进行绑定");
                    Bot.getApi().sendGroupMsg(lo,"请求人" + sender.getName());
                    Bot.getApi().sendGroupMsg(lo ,"同意操作请私聊发送或创建临时会话发送" + Utils.config.getString("BindSettings.BindPrefix","同意绑定") +
                            " " + sender.getName());

                    sender.sendMessage(ChatColor.GREEN + "请在您所在的QQ群内完成相关操作");
                }
            }else{
                sender.sendMessage(ChatColor.RED + "格式错误");
                sender.sendMessage(ChatColor.RED + "正确使用方法: /bind [QQ号]");
            }
        }else{
            sender.sendMessage(ChatColor.RED + "您不是玩家");
        }

        return true;
    }
}
