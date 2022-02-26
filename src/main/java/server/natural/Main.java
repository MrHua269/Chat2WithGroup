package server.natural;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import server.natural.ChatServer.BaseServer;
import server.natural.ChatServer.ConnectHandler;
import server.natural.command.*;
import server.natural.events.*;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//todo 注释，现在这里写的东西我已经开始看不懂了 --NaT_Jerry
public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        try{
         getLogger().info(ChatColor.GREEN + "欢迎使用!");
         saveDefaultConfig();
         Thread.sleep(3000);
         Utils.checkUpdate(getConfig().getString("UpdateURL"));
         Utils.executor = new ThreadPoolExecutor(getConfig().getInt("CoreConfig.ThreadCount")+1,Integer.MAX_VALUE,Long.MAX_VALUE, TimeUnit.DAYS,new LinkedBlockingDeque<>());
         boolean tmp = getConfig().getBoolean("Function.EnableGroupToGame");
         boolean tmp1 = getConfig().getBoolean("Function.EnableInvite");
         boolean tmp2 = getConfig().getBoolean("Function.EnableChatServer");
         boolean tmp3 = getConfig().getBoolean("Function.EnableAntiChatSpam");
         if(tmp3)Bukkit.getPluginManager().registerEvents(new AntiChatRepeating(),this);
         if(tmp2){
             Bukkit.getLogger().info("Start chat server...");
             Utils.executor.execute(new BaseServer("0.0.0.0",Utils.config.getInt("ChatServer.Port")));
             Bukkit.getPluginManager().registerEvents(new ConnectHandler(),this);
         }
         int cfver = getConfig().getInt("config-ver");
         getLogger().info(ChatColor.LIGHT_PURPLE + "注册插件事件监听器...");
         if(tmp){
             Bukkit.getPluginManager().registerEvents(new OnGroupMessage(), this);
         }
         Bukkit.getPluginManager().registerEvents(new JoinGroupRequestSelectorListener(), this);
         Bukkit.getPluginManager().registerEvents(new OnQuitJoinGroupReplyMessageEvent(), this);
         getLogger().info(ChatColor.LIGHT_PURPLE + "注册插件命令");
         Bukkit.getPluginCommand("smg").setExecutor(new CommandSMG());
         Bukkit.getPluginCommand("cwg").setExecutor(new CommandCWG());
         Bukkit.getPluginCommand("messageforwarding").setExecutor(new CommandStopMessageTrasForwarding());
         if(tmp1) Bukkit.getPluginCommand("botinvite").setExecutor(new CommandInvite());
         getLogger().info(ChatColor.GREEN + "准备就绪!");
         //todo should disable the plugin on other version config file
         ////Advise the owner to update the config.yml again
         //Not again
         if(cfver!=Utils.configVersion){
             getLogger().warning("ChatWithGroup的配置文件出现了问题，请删除配置文件重新启动服务器，插件会重新生成配置文件");
             getLogger().warning("服务器将在10秒后继续运行");
             Thread.sleep(10000);
             getLogger().warning("ChatWithGroup配置出现问题，请尽快修复");
         }
         if(Utils.isBetaVersion){
             getLogger().warning("该版本为测试版本，Bug可能较多，若发现Bug请在Github反馈");
         }
        }catch(Exception ignored){}
    }
    @Override
    public void onDisable() {
        Utils.executor.shutdown();
        getLogger().info("再见");
    }

}
