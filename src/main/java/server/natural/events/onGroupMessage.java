package server.natural.events;

import me.albert.amazingbot.events.GroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import server.natural.Utils;

// TODO 接受群内信息
public class onGroupMessage implements Listener {
    @EventHandler
    public void onGroupMessageEvent(GroupMessageEvent e){
        if(e.getGroupID() == Utils.groupID){
            Bukkit.broadcastMessage(ChatColor.BLUE + e.getEvent().getSenderName() + ">>" + ChatColor.GRAY + e.getMsg());

        }


    }


}
