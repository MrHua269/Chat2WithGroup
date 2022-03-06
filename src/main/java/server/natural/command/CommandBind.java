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
    //todo Complete it
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
                    Utils.cacheFileSave();
                    if(Bot.getApi().getBot().getFriendOrFail(QQID) != null){
                        Bot.getApi().sendPrivateMsg(String.valueOf(QQID),"服务器内有人请求与您的QQ进行绑定");
                        Bot.getApi().sendPrivateMsg(String.valueOf(QQID),"请求人" + sender.getName());
                        Bot.getApi().sendPrivateMsg(String.valueOf(QQID),"同意操作请输入: " + Utils.config.getString("BindPrefix","同意绑定") +
                                " " + sender.getName());
                    }
                    sender.sendMessage(ChatColor.RED + "请在QQ内完成相关操作");
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
