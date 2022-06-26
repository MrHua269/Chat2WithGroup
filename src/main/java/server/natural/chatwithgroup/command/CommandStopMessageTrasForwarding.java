package server.natural.chatwithgroup.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import server.natural.chatwithgroup.events.OnGroupMessage;

public class CommandStopMessageTrasForwarding implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player)commandSender;
            if (OnGroupMessage.getForwardMap().get(player)){
                OnGroupMessage.getForwardMap().replace(player,false);
                commandSender.sendMessage(ChatColor.GREEN + "消息转发已关闭");
            }else{
                OnGroupMessage.getForwardMap().replace(player,true);
                commandSender.sendMessage(ChatColor.GREEN + "消息转发已开启");
            }
        }else{
            commandSender.sendMessage("你不是玩家");
        }
        return true;
    }
}
