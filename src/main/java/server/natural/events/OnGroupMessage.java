package server.natural.events;

import me.albert.amazingbot.events.GroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import server.natural.Utils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class OnGroupMessage implements Listener {
    private static ConcurrentHashMap<Player,Boolean> forwardMap = new ConcurrentHashMap<>();
    @EventHandler
    public void onGroupMessageSent(GroupMessageEvent event){
       if (Utils.config.getBoolean("EnableGroupToGame")&&event.getGroupID()==Utils.config.getLong("group"))
       {
           Bukkit.getLogger().info("<QQ:"+event.getUserID()+">"+event.getMsg());
           forwardMap.forEach((player,value)->{
               if (value){
                   player.sendMessage("<QQ:"+event.getUserID()+">"+event.getMsg());
               }
           });
       }
    }
    public static ConcurrentHashMap getForwardMap(){return forwardMap;}
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){forwardMap.put(event.getPlayer(),true);}
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){forwardMap.remove(event.getPlayer());}
}
