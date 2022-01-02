package server.natural.ChatServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import me.albert.amazingbot.bot.Bot;
import me.albert.amazingbot.events.GroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import server.natural.Utils;

public class ConnectHandler extends SimpleChannelInboundHandler<String> implements Listener {
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        Bukkit.getLogger().info(ChatColor.LIGHT_PURPLE +"Channel sent message: "+"<"+ctx.channel().remoteAddress().toString()+">"+msg);
        channels.writeAndFlush("<"+ctx.channel().remoteAddress().toString()+">"+msg);
        Bukkit.getServer().broadcastMessage("<client:"+ctx.channel().remoteAddress().toString()+">"+msg);
        Bot.getApi().sendGroupMsg(String.valueOf(Utils.config.getLong("group")),"<client:"+ctx.channel().remoteAddress().toString()+">"+msg);
    }
    @EventHandler
    public void onUserChat(GroupMessageEvent e){
        if(e.getGroupID() == Utils.config.getLong("group")){
            channels.writeAndFlush("<QQ" + e.getEvent().getSenderName()+"("+ e.getUserID()+")"+">" + e.getMsg());
        }
    }
    @EventHandler
    public void OnMessage(AsyncPlayerChatEvent e){
        channels.writeAndFlush("<"+"Player:"+e.getPlayer().getName()+">"+e.getMessage());
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        Bukkit.getLogger().info(ChatColor.LIGHT_PURPLE +"Channel connected. Address: "+ctx.channel().remoteAddress().toString());
        channels.add(ctx.channel());
        channels.write("["+ctx.channel().remoteAddress().toString()+"]"+"Connected to the server");
    }
}
