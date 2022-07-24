package server.natural.chatwithgroup.command;

import me.albert.amazingbot.bot.Bot;
import me.albert.amazingbot.objects.contact.Member;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import server.natural.chatwithgroup.Utils;

public class CommandBind implements CommandExecutor {
    //Date:2022/3/13
    //将其改成群内通告
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(Utils.config.getBoolean("Function.EnableBindInQQ",true)){
            if(sender instanceof Player){
                if(args.length == 1 && Utils.isNumberStrings(args[0])){
                    Utils.executor.runTaskAsynchronously(Utils.plugin,()->{
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
                            f1:
                            for(long l:Utils.group){
                                for (Member member : Bot.getApi().getGroupMemberList(l)){
                                    if (member.getUserID() == QQID){
                                        g[0]=l;
                                        break f1;
                                    }
                                }
                            }
                            long lo = g[0];
                            Bot.getApi().sendGroupMsg(lo,"服务器有人请求与一位QQ用户进行绑定");
                            Bot.getApi().sendGroupMsg(lo,"请求人" + sender.getName());
                            Bot.getApi().sendGroupMsg(lo ,"同意操作请私聊发送或创建临时会话发送" + Utils.config.getString("BindSettings.BindPrefix","同意绑定") + " " + sender.getName());

                            sender.sendMessage(ChatColor.GREEN + "请在您所在的QQ群内完成相关操作");
                        }
                    });
                }else{
                    sender.sendMessage(ChatColor.RED + "格式错误");
                    sender.sendMessage(ChatColor.RED + "正确使用方法: /bind [QQ号]");
                }
            }else{
                sender.sendMessage(ChatColor.RED + "您不是玩家");
            }
        }else{
            sender.sendMessage(ChatColor.RED + "在QQ绑定账户功能暂未开启");
        }
        return true;
    }
}
