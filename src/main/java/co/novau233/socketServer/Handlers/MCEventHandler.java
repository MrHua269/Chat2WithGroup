package co.novau233.socketServer.Handlers;

import me.albert.amazingbot.events.message.GroupMessageEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MCEventHandler implements Listener {
    //事件监听器
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        AsyncEventHandler.sendChat("<"+event.getPlayer().getName()+">"+event.getMessage(), AsyncEventHandler.MessageType.NORMAL,null,null);
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        AsyncEventHandler.sendChat("player "+event.getPlayer().getName()+" left game", AsyncEventHandler.MessageType.NORMAL,null,null);
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        AsyncEventHandler.sendChat("player "+event.getPlayer().getName()+" joined game", AsyncEventHandler.MessageType.NORMAL,null,null);
    }
    @EventHandler
    public void onGroupMessage(GroupMessageEvent event){
        AsyncEventHandler.sendChat(ChatColor.BLUE + "[QGroup message forward]" + event.getMember().getDisplayName() + ChatColor.GRAY
                + "(" + event.getUserID() + ")" + ChatColor.GRAY + ">>" + event.getMsg(), AsyncEventHandler.MessageType.NORMAL,null,null);
    }
}
