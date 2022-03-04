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
            if(args.length == 1&& args[0]!=null && Utils.isNumberStrings(args[0])){
                long QQID = Long.parseLong(args[0]);
                if(sender.hasPermission("cwg.bind.force")){
                    Bot.getApi().setBind(QQID,((Player) sender).getUniqueId());
                    sender.sendMessage(ChatColor.GREEN + "绑定成功");
                }else{

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
