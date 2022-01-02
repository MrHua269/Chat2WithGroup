package server.natural.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import server.natural.Utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AntiChatRepeating implements Listener {
    //todo complete the codes
    //warning : Beta stat may has bug
    private static Map<Player,Integer> integerHashMap = new ConcurrentHashMap<Player,Integer>();
    private static Map<Player,Boolean> booleanHashMap = new ConcurrentHashMap<Player,Boolean>();
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        this.integerHashMap.put(e.getPlayer(),0);
        this.booleanHashMap.put(e.getPlayer(),false);
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent chatEvent){
        this.integerHashMap.replace(chatEvent.getPlayer(),this.integerHashMap.get(chatEvent.getPlayer())+1);
        int limit = Utils.config.getInt("MessageRepeatLimit");
        int valueNow = this.integerHashMap.get(chatEvent.getPlayer());
        if(valueNow > limit || valueNow == limit){
          if(!this.booleanHashMap.get(chatEvent.getPlayer())){
              this.booleanHashMap.replace(chatEvent.getPlayer(),true);
              Utils.sleepAway(Utils.config.getLong("CoolDownTime"));
          }
        }
    }
}
