package server.natural;

import co.novau233.socketServer.Handlers.CacheManager;
import co.novau233.socketServer.Handlers.MessageHandler;
import co.novau233.socketServer.SocketServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import server.natural.command.*;
import server.natural.events.*;

import java.io.File;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//todo 注释，现在这里写的东西我已经开始看不懂了 --NaT_Jerry
public class Main extends JavaPlugin {
    SocketServer server = null;
    @Override
    public void onEnable() {
        try{
         getLogger().info(ChatColor.GREEN + "欢迎使用!");
         saveDefaultConfig();
         //加载其他缓存或配置文件
         Utils.LoadFile();
         //让服务器歇3秒
         Thread.sleep(3000);
         //初始化线程池
         Utils.executor = new ThreadPoolExecutor(getConfig().getInt("CoreConfig.ThreadCount"),Integer.MAX_VALUE,Long.MAX_VALUE, TimeUnit.DAYS,new LinkedBlockingDeque<>());
         //一些临时变量
         boolean tmp = getConfig().getBoolean("Function.EnableGroupToGame");
         boolean tmp2 = getConfig().getBoolean("Function.EnableChatServer");
         boolean tmp3 = getConfig().getBoolean("Function.EnableAntiChatSpam");
         int cfver = getConfig().getInt("config-ver");
         getLogger().info(ChatColor.LIGHT_PURPLE + "注册插件事件监听器...");
         //如上面info所说
            if(tmp3)Bukkit.getPluginManager().registerEvents(new AntiChatRepeating(),this);
            if(tmp2){
                Bukkit.getLogger().info("Start chat server...");
                server.start();
                server = new SocketServer("0.0.0.0",Utils.config.getInt("ChatServer.Port"));
                Bukkit.getPluginManager().registerEvents(new MessageHandler(),this);
            }
         if(tmp){Bukkit.getPluginManager().registerEvents(new OnGroupMessage(), this);}
         Bukkit.getPluginManager().registerEvents(new JoinGroupRequestSelectorListener(), this);
         Bukkit.getPluginManager().registerEvents(new OnQuitJoinGroupReplyMessageEvent(), this);
         getLogger().info(ChatColor.LIGHT_PURPLE + "注册插件命令");
         Bukkit.getPluginCommand("smg").setExecutor(new CommandSMG());
         Bukkit.getPluginCommand("cwg").setExecutor(new CommandCWG());
         //尚未完成
//         Bukkit.getPluginCommand("bind").setExecutor(new CommandBind());
         Bukkit.getPluginCommand("messageforwarding").setExecutor(new CommandStopMessageTrasForwarding());
         Bukkit.getPluginCommand("botinvite").setExecutor(new CommandInvite());
         getLogger().info(ChatColor.GREEN + "准备就绪!");
         if(cfver!=Utils.configVersion){
             getLogger().warning("ChatWithGroup的配置文件出现了问题，请删除配置文件重新启动服务器，插件会重新生成配置文件");
             getLogger().warning("服务器将在10秒后继续运行");
             Thread.sleep(10000);
             getLogger().warning("ChatWithGroup配置出现问题，请尽快修复");
             Bukkit.getPluginManager().getPlugin("ChatWithGroup").onDisable();
         }
         if(Utils.isBetaVersion){getLogger().warning("该版本为测试版本，Bug可能较多，若发现Bug请在Github反馈");}
         Utils.checkUpdate("https://jlnpehub.mc66.club/CWGCheck.yml");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onDisable() {
        SocketServer.interrupted();
        Utils.executor.shutdown();
        CacheManager.unLoad();
        getLogger().info("再见");
    }

}
