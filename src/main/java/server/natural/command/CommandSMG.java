package server.natural.command;

import me.albert.amazingbot.bot.Bot;
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
            Bot.getApi().sendGroupMsg(String.valueOf(Utils.config.getLong("group")),commandSender.getName() + "->" + args[0]);
            commandSender.sendMessage("Message send finished!");
            return true;
            }else{
                //When player uses the command in a wrong way ,we will advise him like this
                commandSender.sendMessage("Wrong way to use,the right way is:");
                return false;
            }
        }else{
            Bot.getApi().sendGroupMsg(String.valueOf(Utils.config.getLong("group")),  "Server Manager(Console)->" + args[0]);
        }
        return true;
    }
}
