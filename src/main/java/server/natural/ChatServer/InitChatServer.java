package server.natural.ChatServer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class InitChatServer {
    public static void Init(String host,int port){
        BaseServer server = new BaseServer(host,port);
        Bukkit.getLogger().info(ChatColor.LIGHT_PURPLE+"Initializing IRC Service");
        server.start();
    }
}
