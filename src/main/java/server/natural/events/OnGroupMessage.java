package server.natural.events;

import me.albert.amazingbot.events.GroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import server.natural.Utils;

import java.util.HashMap;

public class OnGroupMessage implements Listener {
    private static HashMap<Player,Boolean> forwardMap = new HashMap<>();
    @EventHandler
    public void onGroupMessageSent(GroupMessageEvent event){
       if (Utils.config.getBoolean("Function.EnableGroupToGame")&&event.getGroupID()==Utils.config.getLong("CoreConfig.group"))
       {
           Bukkit.getLogger().info("<QQ:"+event.getUserID()+">"+event.getMsg());

           forwardMap.forEach((player,value)->{
               if (value){
                   player.sendMessage("<QQ:"+event.getUserID()+">"+event.getMsg());
               }
           });
       }
    }
    public static HashMap getForwardMap(){return forwardMap;}
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){forwardMap.put(event.getPlayer(),true);}
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){forwardMap.remove(event.getPlayer());}
}
