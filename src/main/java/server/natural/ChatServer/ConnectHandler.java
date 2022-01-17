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

import java.util.concurrent.*;

//todo 汉化
//todo 摆烂了，不汉化了 -- wangxyper
public class ConnectHandler extends SimpleChannelInboundHandler<String> implements Listener {
    //todo I don't why I used too many HashMaps
    //todo Check bugs and try make it simple
    public static ConcurrentMap<ChannelHandlerContext,Integer> channelMessageCounts = new ConcurrentHashMap();
    public static ConcurrentMap<ChannelHandlerContext,Boolean> channelAntiTrigger = new ConcurrentHashMap();
    public static ConcurrentMap<ChannelHandlerContext,Boolean> channelAntiTrigger2 = new ConcurrentHashMap();
    public static ConcurrentMap<ChannelHandlerContext,Boolean> hasCoolDowning = new ConcurrentHashMap<>();
    public static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(4);
    //Channels
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    //On channel message sent
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        channelMessageCounts.replace(ctx,channelMessageCounts.get(ctx)+1);
        if(channelMessageCounts.get(ctx) >= Utils.config.getLong("MessageLimitPer1M")){
            if(!channelAntiTrigger.get(ctx)){
                channelAntiTrigger.replace(ctx,true);
                channelAntiTrigger2.replace(ctx,true);
            }else{
                ctx.writeAndFlush("You are send to many message,Please wait a minute");
                new Thread(()->{
                    if(channelAntiTrigger2.get(ctx)) {
                        channelAntiTrigger2.replace(ctx, false);
                        try {
                            Thread.sleep(Utils.config.getLong("MessageLimitSleepTime"));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }else {
            Bukkit.getLogger().info(ChatColor.LIGHT_PURPLE + "Channel sent message: " + "<" + ctx.channel().remoteAddress().toString() + ">" + msg);
            channels.writeAndFlush("<" + ctx.channel().remoteAddress().toString() + ">" + msg);
            Bukkit.getServer().broadcastMessage("<client:" + ctx.channel().remoteAddress().toString() + ">" + msg);
            Bot.getApi().sendGroupMsg(String.valueOf(Utils.config.getLong("group")), "<client:" + ctx.channel().remoteAddress().toString() + ">" + msg);

        }
    }
    //On group user chat sent
    @EventHandler
    public void onUserChat(GroupMessageEvent e){
        if(e.getGroupID() == Utils.config.getLong("group")){
            channels.writeAndFlush("<QQ" + e.getEvent().getSenderName()+"("+ e.getUserID()+")"+">" + e.getMsg());
        }
    }
    //On player chat sent
    @EventHandler
    public void OnMessage(AsyncPlayerChatEvent e){
        channels.writeAndFlush("<"+"Player:"+e.getPlayer().getName()+">"+e.getMessage());
    }
    //On channel inited
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        executor.schedule(()->{
            if (!channelAntiTrigger.get(ctx)){
                channelMessageCounts.replace(ctx,0);
            }
        },1, TimeUnit.MINUTES);
        channelMessageCounts.put(ctx,0);
        channelAntiTrigger2.put(ctx,false);
        hasCoolDowning.put(ctx,false);
        channelAntiTrigger.put(ctx,false);
        Bukkit.getLogger().info(ChatColor.LIGHT_PURPLE +"Channel connected. Address: "+ctx.channel().remoteAddress().toString());
        channels.add(ctx.channel());
        channels.write("["+ctx.channel().remoteAddress().toString()+"]"+"Connected to the server");
    }

}
