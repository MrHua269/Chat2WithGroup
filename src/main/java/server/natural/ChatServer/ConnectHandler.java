package server.natural.ChatServer;

import io.netty.channel.Channel;
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

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import static server.natural.events.AntiChatRepeating.isOutOfLimit;

public class ConnectHandler extends SimpleChannelInboundHandler<String> implements Listener {
    private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private ConcurrentHashMap<Channel,Boolean> loginState = new ConcurrentHashMap<>();
    public ChannelGroup getChannels(){
        return this.channels;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        Bukkit.getLogger().info(ChatColor.GREEN+"Channel connected:"+ctx.channel());
        channels.add(ctx.channel());
        Bukkit.broadcastMessage("Channel connected:"+ctx.channel());
        channels.writeAndFlush("["+new Date() +"]"+"Channel connected:"+ctx.channel());
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        switch (s){
            case "LOGIN":
                if (!loginState.containsValue(ctx.channel())){
                    loginState.put(ctx.channel(),true);
                }
                Bukkit.getLogger().info(loginState.get(ctx.channel()).toString());
                break;
            case "DISCONNECT":
                ctx.disconnect();
                Bukkit.getLogger().info(ChatColor.GREEN+"Channel disconnected:"+ctx.channel());
                channels.writeAndFlush("Channel disconnected:"+ctx.channel());
                Bukkit.broadcastMessage("Channel disconnected:"+ctx.channel());
                break;
            default:
                if (!isOutOfLimit(ctx.channel(), Utils.config.getLong("ChatServer.MessageSlotLimit"))){
                    if (loginState.get(ctx.channel())) {
                        Bukkit.getLogger().info(ChatColor.LIGHT_PURPLE + "[" + new Date() + "]" + "<" + ctx.channel().remoteAddress() + ">" + s);
                        channels.writeAndFlush("[" + new Date() + "]" + "<" + ctx.channel().remoteAddress() + ">" + s);
                        Bukkit.broadcastMessage( "<" + ctx.channel().remoteAddress() + ">" + s);
                        Bot.getApi().getGroup(Utils.group).sendMessage("<" + ctx.channel().remoteAddress() + ">" + s);
                    }else{ctx.disconnect();}
                }else{
                    ctx.writeAndFlush(Utils.config.getString("Texts.MessageOutOfLimit"));
                }
                break;
        }

    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        channels.writeAndFlush("<"+event.getPlayer().getName()+">"+event.getMessage());
    }
    @EventHandler
    public void onGroupChat(GroupMessageEvent event){
        channels.writeAndFlush("<QQ:"+event.getUserID()+">"+event.getMsg());
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        loginState.remove(ctx.channel());
        Bukkit.broadcastMessage("Channel disconnected:"+ctx.channel());
    }
}
