package server.natural;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import server.natural.ChatServer.ConnectHandler;
import server.natural.ChatServer.InitChatServer;
import server.natural.command.*;
import server.natural.events.*;
import java.util.Objects;
//todo 注释，现在这里写的东西我已经开始看不懂了 --NaT_Jerry
public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        try{
         boolean tmp = Boolean.parseBoolean(getConfig().getString("GroupToGame"));
         boolean tmp1 = Boolean.parseBoolean(getConfig().getString("EnableInvite"));
         boolean tmp2 = Boolean.parseBoolean(getConfig().getString("EnableChatServer"));
         getLogger().info(ChatColor.GREEN + "欢迎使用!");
            //保存原配置
         saveDefaultConfig();
         Thread.sleep(3000);
         if(tmp2){
             //启动ChatServer
             Bukkit.getPluginManager().registerEvents(new ConnectHandler(),this);
             InitChatServer.Init("0.0.0.0",Utils.config.getInt("ChatServerPort"));
         }
         int cfver = getConfig().getInt("config-ver");
         getLogger().info(ChatColor.LIGHT_PURPLE + "注册插件事件监听器...");
         if(tmp){ 
             Bukkit.getPluginManager().registerEvents(new onGroupMessage(), this);
         }
         //Check the "config-ver" and advise the owner to update the config.yml
         if(cfver != 4){
             getLogger().warning("ChatWithGroup的配置文件出现了问题，请删除配置文件重新启动服务器，插件会重新生成配置文件");
             getLogger().warning("服务器将在10秒后继续运行");
             Thread.sleep(10000);
         }
         Bukkit.getPluginManager().registerEvents(new RequestSelectorListener(), this);
         Bukkit.getPluginManager().registerEvents(new onQuitJoinGroupReplyMessageEvent(), this);
         getLogger().info(ChatColor.LIGHT_PURPLE + "注册插件命令");
         Objects.requireNonNull(Bukkit.getPluginCommand("smg")).setExecutor(new CommandSMG());
         Bukkit.getPluginCommand("cwgversion").setExecutor(new CommandCWGVer());
         Bukkit.getPluginCommand("messageforwarding").setExecutor(new CommandStopMessageTrasForwarding());
         if(tmp1){
             Objects.requireNonNull(Bukkit.getPluginCommand("botinvite")).setExecutor(new CommandInvite());

         }
         getLogger().info(ChatColor.GREEN + "准备就绪!");
         //Advise the owner to update the config.yml again
         if(cfver != 4){
             getLogger().warning("ChatWithGroup配置出现问题，请尽快修复");
         }
        }catch(Exception ignored){}
    }
    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Closing thread pools...");
        //关闭线程池
        Utils.executor2.shutdown();
        Utils.executor.shutdown();
        getLogger().info("再见");
    }

}
