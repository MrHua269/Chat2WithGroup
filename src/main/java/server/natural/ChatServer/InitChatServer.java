package server.natural.ChatServer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class InitChatServer {
    public static void Init(String host,int port){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,1,Long.MAX_VALUE, TimeUnit.DAYS,new ArrayBlockingQueue<>(5));
        executor.execute(new BaseServer(host,port));
        Bukkit.getLogger().info(ChatColor.LIGHT_PURPLE+"Initializing IRC Service");
    }
}
