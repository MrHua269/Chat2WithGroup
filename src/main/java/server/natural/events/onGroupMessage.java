package server.natural.events;

import me.albert.amazingbot.events.GroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import server.natural.Utils;


public class onGroupMessage implements Listener {
    @EventHandler
    public void onGroupMessageEvent(GroupMessageEvent event){
        if(Utils.config.getBoolean("EnableGroupToGame")){
        Bukkit.getServer().broadcastMessage(event.getUserID()+":"+event.getRawMessage());
        }
    }

}
