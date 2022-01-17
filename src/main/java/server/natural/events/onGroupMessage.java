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

//todo make player can open it or stop it
public class onGroupMessage implements Listener {
    public static ConcurrentMap<Player,Boolean> booleans = new ConcurrentHashMap<>();
    @EventHandler
    public void onGroupMessageEvent(GroupMessageEvent event){
       Utils.executor.execute(()->{
           if(Utils.config.getBoolean("EnableGroupToGame")){
               List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
               for(Player player:players){
                   if(booleans.get(player)) {
                       /*
                       getRawMessage方法已过时
                       我已更换 -- NaT_Jerry
                        */
                       player.sendMessage(ChatColor.BLUE + "[QQ]" + event.getEvent().getSenderName() + "(" + event.getUserID() + ")" + ":" + ChatColor.GRAY + event.getMsg());
                   }
               }
           }
       });
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        booleans.put(e.getPlayer(),true);
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        booleans.remove(e.getPlayer());
    }

}
