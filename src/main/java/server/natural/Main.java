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
         getLogger().info(ChatColor.GREEN + "Welcome!");
         saveDefaultConfig();
         Thread.sleep(3000);
         Bukkit.getPluginManager().registerEvents(new ConnectHandler(),this);
         InitChatServer.Init("0.0.0.0",Utils.config.getInt("ChatServerPort"));
         boolean tmp = Boolean.parseBoolean(getConfig().getString("GroupToGame"));
         boolean tmp1 = Boolean.parseBoolean(getConfig().getString("EnableInvite"));
         int cfver = getConfig().getInt("config-ver");
         getLogger().info(ChatColor.LIGHT_PURPLE + "Register Event Listener...");
         if(tmp){
             Bukkit.getPluginManager().registerEvents(new onGroupMessage(), this);
         }
         //Check the "config-ver" and advise the owner to update the config.yml
         if(cfver != 1){
             getLogger().warning("ChatWithGroup get some problem on config.yml , plz delete it and the plugin will fix it automatically");
             getLogger().warning("plugin will start after 10 seconds");
             Thread.sleep(10000);
         }


         Bukkit.getPluginManager().registerEvents(new RequestSelectorListener(), this);
         Bukkit.getPluginManager().registerEvents(new AddToTheGroup(), this);
         getLogger().info(ChatColor.LIGHT_PURPLE + "Register the Command of this Plugin");
         Objects.requireNonNull(Bukkit.getPluginCommand("smg")).setExecutor(new CommandSMG());
            Bukkit.getPluginCommand("stopcs").setExecutor(new CommandStopChatServer());
         if(tmp1){
             Objects.requireNonNull(Bukkit.getPluginCommand("botinvite")).setExecutor(new CommandInvite());

         }
         getLogger().info(ChatColor.GREEN + "Ready!");
         getLogger().info(ChatColor.GREEN + "The Version of CWG(ChatWithGroup) is 1.3.2");
         getLogger().info(ChatColor.GREEN + "This Version is released by Jerry!");
         //Advise the owner to update the config.yml again
         if(cfver != 1){
             getLogger().warning("My config may have some problem, plz fix it...");
         }
        }catch(Exception ignored){}
    }
    @Override
    public void onDisable() {
        getLogger().info("Goodbye and See You Next Time");
        //this.saveConfig();
    }

}
