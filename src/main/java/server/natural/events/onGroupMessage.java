package server.natural.events;

import me.albert.amazingbot.events.GroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import server.natural.Utils;

//todo make player can open it or stop it
public class onGroupMessage implements Listener {
    @EventHandler
    public void onGroupMessageEvent(GroupMessageEvent event){
        if(Utils.config.getBoolean("EnableGroupToGame")){
        Bukkit.getServer().broadcastMessage(ChatColor.BLUE + "[QQ}" + event.getEvent().getSenderName() +"(" + event.getUserID()
                + ")" +":"+ ChatColor.GRAY + event.getRawMessage());
        }
    }

}
