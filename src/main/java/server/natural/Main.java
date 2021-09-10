package server.natural;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import server.natural.ChatServer.ConnectHandler;
import server.natural.ChatServer.InitChatServer;
import server.natural.command.CommandSMG;
import server.natural.command.CommandSystemInfo;
import server.natural.events.AddToTheGroup;
import server.natural.events.RequestSelectorListener;


import java.util.Objects;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        try{
         getLogger().info(ChatColor.GREEN + "欢迎使用ChatWithGroup");
         saveDefaultConfig();
         Thread.sleep(3000);
         Bukkit.getPluginManager().registerEvents(new ConnectHandler(),this);
         InitChatServer.Init("0.0.0.0",Utils.config.getInt("ChatServerPort"));
         getLogger().info(ChatColor.LIGHT_PURPLE + "注册事件监听器...");
         Bukkit.getPluginManager().registerEvents(new RequestSelectorListener(), this);
         Bukkit.getPluginManager().registerEvents(new AddToTheGroup(), this);
         getLogger().info(ChatColor.LIGHT_PURPLE + "注册相关指令");
         Objects.requireNonNull(Bukkit.getPluginCommand("smg")).setExecutor(new CommandSMG());
         Objects.requireNonNull(Bukkit.getPluginCommand("systeminfo")).setExecutor(new CommandSystemInfo());
         getLogger().info(ChatColor.GREEN + "一切都准备好了！");
        }catch(Exception ignored){}
    }
    @Override
    public void onDisable() {
        getLogger().info("欢迎再次使用ChatWithGroup");
        this.saveConfig();
    }
}
