package server.natural.events;

import me.albert.amazingbot.events.GroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.nio.Buffer;

public class onGroupMessage implements Listener {
    @EventHandler
    public void onGroupMessageEvent(GroupMessageEvent event){
        Bukkit.getServer().broadcastMessage(event.getUserID()+":"+event.getRawMessage());
    }

}
