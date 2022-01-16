package server.natural;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import server.natural.ChatServer.ConnectHandler;
import server.natural.ChatServer.InitChatServer;
import server.natural.command.CommandInvite;
import server.natural.command.CommandSMG;
//import server.natural.command.CommandSystemInfo;
import server.natural.command.CommandStopChatServer;
import server.natural.events.AddToTheGroup;
import server.natural.events.RequestSelectorListener;
import server.natural.events.onGroupMessage;


import java.util.Objects;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        try{
         getLogger().info(ChatColor.GREEN + "欢迎使用!");
         saveDefaultConfig();
         Thread.sleep(3000);
         Bukkit.getPluginManager().registerEvents(new ConnectHandler(),this);
         InitChatServer.Init("0.0.0.0",Utils.config.getInt("ChatServerPort"));
         boolean tmp = Boolean.parseBoolean(getConfig().getString("GroupToGame"));
         boolean tmp1 = Boolean.parseBoolean(getConfig().getString("EnableInvite"));
         int cfver = getConfig().getInt("config-ver");
         getLogger().info(ChatColor.LIGHT_PURPLE + "注册插件事件监听器...");
         if(tmp){
             Bukkit.getPluginManager().registerEvents(new onGroupMessage(), this);
         }
         //Check the "config-ver" and advise the owner to update the config.yml
         if(cfver != 1){
             getLogger().warning("ChatWithGroup的配置文件出现了问题，请删除配置文件重新启动服务器，插件会重新生成配置文件");
             getLogger().warning("服务器将在10秒后继续运行");
             Thread.sleep(10000);
         }


         Bukkit.getPluginManager().registerEvents(new RequestSelectorListener(), this);
         Bukkit.getPluginManager().registerEvents(new AddToTheGroup(), this);
         getLogger().info(ChatColor.LIGHT_PURPLE + "注册插件命令");
         Objects.requireNonNull(Bukkit.getPluginCommand("smg")).setExecutor(new CommandSMG());
            Bukkit.getPluginCommand("stopcs").setExecutor(new CommandStopChatServer());
         if(tmp1){
             Objects.requireNonNull(Bukkit.getPluginCommand("botinvite")).setExecutor(new CommandInvite());

         }
         getLogger().info(ChatColor.GREEN + "准备就绪!");
//         getLogger().info(ChatColor.GREEN + "The Version of CWG(ChatWithGroup) is 1.3.2");
//         getLogger().info(ChatColor.GREEN + "This Version is released by Jerry!");
         //Advise the owner to update the config.yml again
         if(cfver != 1){
             getLogger().warning("ChatWithGroup配置出现问题，请尽快修复");
         }
        }catch(Exception ignored){}
    }
    @Override
    public void onDisable() {
        getLogger().info("再见");
        //this.saveConfig();
    }

}
