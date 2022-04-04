package co.novau233.socketServer.Handlers;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.bukkit.Bukkit;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class AsyncEventHandler {
    public static enum MessageType{
        NORMAL,
        PUBLICMESSAGE,
        PRIVATE
    }
    private static final ConcurrentHashMap<Channel,ConcurrentHashMap<String,Object>> channelManager = new ConcurrentHashMap<>();
    private static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void onChannelRemoved(Channel channel){
        sendChat("Channel "+channelManager.get(channel).get("NickName").toString()+"Disconnected!",MessageType.NORMAL,null);
        channelManager.remove(channel);
        channels.remove(channel);
    }
    public static void handleChat(Channel channel, HashMap message){
        executor.execute(()->{
            if(!message.get("head").equals("CHAT")){
                return;
            }
            String chat_message = message.get("chatmessage").toString();
            if(chat_message==null){return;}
            sendChat(chat_message,MessageType.NORMAL,null);
        });
    }
    public static void sendChat(String msg,MessageType type,Channel channel){
        HashMap msg1 = new HashMap();
        msg1.put("head","GOLBALCHAT");
        switch(type){
            case NORMAL:
                msg1.put("tag","normal");
                break;
            case PUBLICMESSAGE:
                msg1.put("tag","pms");
                break;
            case PRIVATE:
                msg1.put("chatmessage",msg);
                channel.writeAndFlush(msg1);
                return;
            default:
                Bukkit.getLogger().warning("A unchecked message detected!");
                return;
        }
        msg1.put("chatmessage",msg);
        channels.writeAndFlush(msg1);
    }
    public static void handleLogin(Channel channel,HashMap message){
        executor.execute(()->{
            if (channel==null){return;}
            if(!message.get("head").equals("LOGIN")){
                return;
            }
            String login_password = message.get("password").toString();
            String login_username = message.get("username").toString();
            if(login_username==null||login_password==null){return;}
            if(!CacheManager.cacheFileYML.contains(login_username)){
                sendChat("Username or password can't be empty!",MessageType.PRIVATE,channel);
                return;
            }
            if(!CacheManager.cacheFileYML.get(login_username).equals(login_password)){
                sendChat("Wrong password or username!",MessageType.PRIVATE,channel);
                return;
            }
            ConcurrentHashMap<String,Object> channelM = new ConcurrentHashMap<>();
            channelM.put("NickName",login_username);
            channelManager.put(channel,channelM);
            channels.add(channel);
        });
    }

    public static void handleREG(Channel channel,HashMap message){
        executor.execute(()->{
            if (channel==null){return;}
            if(!message.get("head").equals("REG")){
                return;
            }
            String reg_password = message.get("password").toString();
            String reg_username = message.get("username").toString();
            if(reg_username==null||reg_password==null){return;}
            if(CacheManager.cacheFileYML.contains(reg_username)){
                sendChat("User was registed!",MessageType.PRIVATE,channel);
                return;
            }
            CacheManager.cacheFileYML.set(reg_username,reg_password);
            sendChat("Completed!",MessageType.PRIVATE,channel);
            channels.add(channel);
        });
    }

    public static void handleMessage(Channel channel,HashMap message){
        switch(message.get("head").toString()){
            case "LOGIN":
                handleLogin(channel,message);
                break;
            case "CHAT":
                handleChat(channel,message);
                break;
            case "REG":
                handleREG(channel,message);
                break;
        }
    }
}
