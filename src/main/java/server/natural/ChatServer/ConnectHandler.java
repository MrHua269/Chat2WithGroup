package server.natural.ChatServer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
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
        channels.writeAndFlush("["+new Date() +"]"+"Channel connected:"+ctx.channel());
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        switch (s){
            case "LOGIN":
                if (!loginState.contains(ctx.channel()))loginState.put(ctx.channel(),true);
                break;
            case "DISCONNECT":
                ctx.disconnect();
                Bukkit.getLogger().info(ChatColor.GREEN+"Channel disconnected:"+ctx.channel());
                channels.writeAndFlush("Channel disconnected:"+ctx.channel());
                break;
            default:
                if (!isOutOfLimit(ctx.channel(), Utils.config.getLong("ChatServer.MessageSlotLimit"))){
                    if (loginState.contains(s)) {
                        Bukkit.getLogger().info(ChatColor.LIGHT_PURPLE + "[" + new Date() + "]" + "<" + ctx.channel() + ">" + s);
                        channels.writeAndFlush("[" + new Date() + "]" + "<" + ctx.channel() + ">" + s);
                    }else{ctx.disconnect();}
                }else{
                    ctx.writeAndFlush(Utils.config.getString("Texts.MessageOutOfLimit"));
                }
                break;
        }

    }
}
