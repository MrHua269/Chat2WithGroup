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
        Bukkit.broadcastMessage("Channel "+channelManager.get(channel).get("NickName").toString()+"Disconnected!");
        //删除频道
        channelManager.remove(channel);
        channels.remove(channel);
    }
    public static void handleChat(Channel channel, HashMap message){
        executor.execute(()->{
            //二次检查消息头是否正确
            if(!message.get("head").equals("CHAT")){
                return;
            }
            String nickName = channelManager.get(channel).get("NickName").toString();
            //获取发送的消息
            String chat_message = message.get("chatmessage").toString();
            //判断为空
            if(chat_message==null){return;}
            //发送消息
            Bukkit.broadcastMessage("<[C]"+nickName+">"+chat_message);
            sendChat("<[C]"+nickName+">"+chat_message,MessageType.NORMAL,null);
        });
    }
    public static void sendChat(String msg,MessageType type,Channel channel){
        HashMap msg1 = new HashMap();
        //消息头
        msg1.put("head","GOLBALCHAT");
        //标签
        switch(type){
            case NORMAL:
                msg1.put("tag","normal");
                break;
            case PUBLICMESSAGE:
                msg1.put("tag","pms");
                break;
            case PRIVATE:
                //如果私发的话到这直接走完
                msg1.put("chatmessage",msg);
                channel.writeAndFlush(msg1);
                return;
            default:
                Bukkit.getLogger().warning("A unchecked message detected!");
                return;
        }
        //广播消息
        msg1.put("chatmessage",msg);
        channels.writeAndFlush(msg1);
    }
    public static void handleLogin(Channel channel,HashMap message){
        executor.execute(()->{
            //检查频道是否为空，不是则终止
            if (channel==null){return;}
            //二次判断消息头
            if(!message.get("head").equals("LOGIN")){
                return;
            }
            //获取发送过来的用户和密码
            String login_password = message.get("password").toString();
            String login_username = message.get("username").toString();
            if(login_username==null||login_password==null){return;}
            //检索是否有该用户
            if(!CacheManager.cacheFileYML.contains(login_username)){
                sendChat("Username doesn't exists!",MessageType.PRIVATE,channel);
                return;
            }
            //判断密码是否正确
            if(!CacheManager.cacheFileYML.get(login_username).equals(login_password)){
                sendChat("Wrong password or username!",MessageType.PRIVATE,channel);
                return;
            }
            //添加到频道中
            ConcurrentHashMap<String,Object> channelM = new ConcurrentHashMap<>();
            channelM.put("NickName",login_username);
            channelManager.put(channel,channelM);
            Bukkit.broadcastMessage("[C]"+login_username+" logined");
            channels.add(channel);
        });
    }

    public static void handleREG(Channel channel,HashMap message){
        executor.execute(()->{
            //还是检查channel是否为空
            if (channel==null){return;}
            //二次判断消息头
            if(!message.get("head").equals("REG")){
                return;
            }
            //获取注册的用户以及密码
            String reg_password = message.get("password").toString();
            String reg_username = message.get("username").toString();
            if(reg_username==null||reg_password==null){return;}
            //判断是否已经被注册
            if(CacheManager.cacheFileYML.contains(reg_username)){
                sendChat("User was registed!",MessageType.PRIVATE,channel);
                return;
            }
            //设置新的用户
            CacheManager.cacheFileYML.set(reg_username,reg_password);
            sendChat("Completed!",MessageType.PRIVATE,channel);
            //添加到频道
            ConcurrentHashMap<String,Object> channelM = new ConcurrentHashMap<>();
            channelM.put("NickName",reg_username);
            channelManager.put(channel,channelM);
            Bukkit.broadcastMessage("[C]"+reg_username+" logined");
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
