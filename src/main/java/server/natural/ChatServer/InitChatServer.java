package server.natural.ChatServer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import server.natural.Utils;
//todo 汉化
public class InitChatServer {
    //A simple class can init the server
    public static void Init(String host,int port){
        if(Utils.isOpenChatServer){
            Utils.executor2.execute(new BaseServer(host,port));
            Bukkit.getLogger().info(ChatColor.LIGHT_PURPLE+"Initializing IRC Service");
        }

    }
}
