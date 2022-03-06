package co.novau233.socketServer.Handlers;

import co.novau233.socketServer.EnumUserState;
import co.novau233.socketServer.Packets.Packet;
import co.novau233.socketServer.Packets.Server.GlobalMSGPacket;
import co.novau233.socketServer.Packets.Server.ServerLoginStateReplyPacket;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageHandler extends SimpleChannelInboundHandler<Packet> implements Listener {
    public static List<Channel> loginedChannel = Collections.synchronizedList(new ArrayList<>());
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,Packet packet) {
        switch (packet.getHead()) {
            case "LOGINREQUEST":
                //Index 0 is username,1 is password commit.
                String[] attachMsg = (String[]) packet.getAttachMsg();
                if(CacheManager.cacheFileYML.contains(attachMsg[0])){
                    if (attachMsg[1].equals(CacheManager.cacheFileYML.getString(attachMsg[0]))){
                        loginedChannel.add(channelHandlerContext.channel());
                        ServerLoginStateReplyPacket packet1 = new ServerLoginStateReplyPacket();
                        packet1.setAttachMsg(EnumUserState.JOINED);
                        channelHandlerContext.writeAndFlush(packet1);
                        channels.add(channelHandlerContext.channel());
                        channels.writeAndFlush("Channel " + channelHandlerContext.channel() + " joined the group.");
                        Bukkit.broadcastMessage("Channel " + channelHandlerContext.channel() + " joined the group.");
                    }else{
                        ServerLoginStateReplyPacket replyPacket = new ServerLoginStateReplyPacket();
                        replyPacket.setAttachMsg(EnumUserState.KICKED);
                        channelHandlerContext.writeAndFlush(replyPacket);
                    }
                }else{
                    GlobalMSGPacket gp = new GlobalMSGPacket();
                    gp.setAttachMsg("You are not registered!Send packet CLIENTREGREQUEST to reg");
                    channelHandlerContext.writeAndFlush(gp);
                }
                break;
            case "CHAT":
                String msg = packet.getAttachMsg().toString();
                if (loginedChannel.contains(channelHandlerContext.channel())) {
                    GlobalMSGPacket p = new GlobalMSGPacket();
                    p.setAttachMsg("<" + channelHandlerContext.channel().remoteAddress() + ">" + msg);
                    Bukkit.broadcastMessage("<" + channelHandlerContext.channel().remoteAddress() + ">" + msg);
                    Utils.group.forEach(group->{
                        Bot.getApi().sendGroupMsg(String.valueOf(group),"<" + channelHandlerContext.channel().remoteAddress() + ">" + msg);
                    });
                    channels.writeAndFlush(p);
                }
                break;
            case "REG":
                String[] profile = (String[]) packet.getAttachMsg();
                if(!CacheManager.cacheFileYML.contains(profile[0])){
                    CacheManager.cacheFileYML.set(profile[0],profile[1]);
                    loginedChannel.add(channelHandlerContext.channel());
                    channels.add(channelHandlerContext.channel());
                    GlobalMSGPacket gp = new GlobalMSGPacket();
                    gp.setAttachMsg("Reg finished!");
                    channelHandlerContext.writeAndFlush(gp);
                }else{
                    GlobalMSGPacket gp = new GlobalMSGPacket();
                    gp.setAttachMsg("User was exists!");
                    channelHandlerContext.writeAndFlush(gp);
                }
                break;
            default:
                break;
        }
    }
    @EventHandler
    public void onGroupMsg(GroupMessageEvent event){
        GlobalMSGPacket p = new GlobalMSGPacket();
        p.setAttachMsg(ChatColor.BLUE + "[Message Forward]" + event.getEvent().getSenderName() + ChatColor.GRAY
                + "(" + event.getUserID() + ")" + ChatColor.GRAY + ">>" + event.getMsg());
        channels.writeAndFlush(p);
    }
    @EventHandler
    public void onPlayerMsg(AsyncPlayerChatEvent event){
        GlobalMSGPacket p = new GlobalMSGPacket();
        p.setAttachMsg(event.getMessage());
        channels.writeAndFlush(p);
    }
}
