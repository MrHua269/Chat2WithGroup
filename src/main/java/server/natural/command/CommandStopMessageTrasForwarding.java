package server.natural.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import server.natural.events.onGroupMessage;

import java.util.concurrent.ConcurrentHashMap;

public class CommandStopMessageTrasForwarding implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player)commandSender;
            ConcurrentHashMap<Player,Boolean> forwardMap = onGroupMessage.getForwardMap();
            if (forwardMap.get(player)){
                forwardMap.replace(player,false);
            }else{
                forwardMap.replace(player,true);
            }
        }
        return true;
    }
}
