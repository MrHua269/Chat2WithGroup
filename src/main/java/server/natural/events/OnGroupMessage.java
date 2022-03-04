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

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class OnGroupMessage implements Listener {
    //Thread safe
    private static ConcurrentHashMap<Player,Boolean> forwardMap = new ConcurrentHashMap<>();
    @EventHandler
    public void onGroupMessageSent(GroupMessageEvent event) {
        Utils.executor.execute(()->{
            if (Utils.config.getBoolean("Function.EnableGroupToGame") && Utils.group.contains(event.getGroupID())) {
                forwardMap.forEach((player, value) -> {
                    if (value) {
                        player.sendMessage(ChatColor.BLUE + "[QQ群消息转发]" + event.getEvent().getSenderName() + ChatColor.GRAY
                                + "(" + event.getUserID() + ")" + ChatColor.GRAY + ">>" + event.getMsg());
                    }
                });
            }
        });

    }
    public static ConcurrentHashMap getForwardMap(){
        return forwardMap;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Utils.executor.execute(()->{
            forwardMap.put(event.getPlayer(),true);
        });
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Utils.executor.execute(()->{
            forwardMap.remove(event.getPlayer());
        });
    }
}
