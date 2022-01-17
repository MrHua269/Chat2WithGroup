package server.natural.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import server.natural.events.onGroupMessage;
public class CommandStopMessageTrasForwarding implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player)commandSender;
            if (!onGroupMessage.booleans.get(player)){
                onGroupMessage.booleans.replace(player,true);
                commandSender.sendMessage(ChatColor.GREEN+"Forward started!");
            }else{
                onGroupMessage.booleans.replace(player,false);
                commandSender.sendMessage(ChatColor.GREEN+"Forward stopped!");
            }
        }
        return true;
    }
}
