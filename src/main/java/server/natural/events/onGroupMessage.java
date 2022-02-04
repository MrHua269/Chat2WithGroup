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
import java.util.concurrent.ConcurrentMap;
public class onGroupMessage implements Listener {
    public static ConcurrentMap<String,Long> longs = new ConcurrentHashMap<>();
    @EventHandler
    public void onGroupMessageEvent(GroupMessageEvent event){
       Utils.executor.execute(()->{
           if(Utils.group==event.getGroupID()){
               if(Utils.config.getBoolean("EnableGroupToGame")){
                   List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
                   for(Player player:players){
                       if(longs.get(player.getName())==1) {
                           player.sendMessage(ChatColor.BLUE + "[QQ]" + event.getEvent().getSenderName() + "(" + event.getUserID() + ")" + ":" + ChatColor.GRAY + event.getMsg());
                       }
                   }
               }
           }

       });
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(!longs.containsKey(e.getPlayer().getName())){
            longs.put(e.getPlayer().getName(),1);
        }
       
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
       longs.remove(e.getPlayer().getName());
    }

}
