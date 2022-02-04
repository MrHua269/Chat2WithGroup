package server.natural.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import server.natural.Utils;
import server.natural.events.onGroupMessage;
public class CommandStopMessageTrasForwarding implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player)commandSender;
            if (!Utils.forwardList.containsKey(player.getName())){
                commandSender.sendMessage(ChatColor.GREEN+"Add "+player.getName()+" to the forward list");
                Utils.forwardList.replace(player.getName(),1L); 
                commandSender.sendMessage(ChatColor.GREEN+"Forward started!");
            }else{
                commandSender.sendMessage(ChatColor.GREEN+"Remove "+player.getName()+" in the forward list");
                Utils.forwardList.remove(player.getName());
                commandSender.sendMessage(ChatColor.GREEN+"Forward stopped!");
            }
        }
        return true;
    }
}
