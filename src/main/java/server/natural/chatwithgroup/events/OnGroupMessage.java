package server.natural.chatwithgroup.events;

import me.albert.amazingbot.events.message.GroupMessageEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import server.natural.chatwithgroup.Utils;

import java.util.concurrent.ConcurrentHashMap;

public class OnGroupMessage implements Listener {
    //Thread safe
    private static ConcurrentHashMap<Player,Boolean> forwardMap = new ConcurrentHashMap<>();
    @EventHandler
    public void onGroupMessageSent(GroupMessageEvent event) {
        Utils.executor.runTaskAsynchronously(Utils.plugin,()->{
            if (Utils.config.getBoolean("Function.EnableGroupToGame") && Utils.group.contains(event.getGroupID())) {
                forwardMap.forEach((player, value) -> {
                    if (value) {
                        player.sendMessage(ChatColor.BLUE + "[QQ群消息转发]" + event.getMember().getDisplayName() + ChatColor.GRAY
                                + "(" + event.getUserID() + ")" + ChatColor.GRAY + ">>" + event.getMsg());
                    }
                });
            }
        });

    }
    public static ConcurrentHashMap<Player, Boolean> getForwardMap(){
        return forwardMap;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Utils.executor.runTaskAsynchronously(Utils.plugin,()-> forwardMap.put(event.getPlayer(),true));
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Utils.executor.runTaskAsynchronously(Utils.plugin,()-> forwardMap.remove(event.getPlayer()));
    }
}
