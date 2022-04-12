package server.natural;

import co.novau233.socketServer.Handlers.MCEventHandler;
import co.novau233.socketServer.SocketServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import server.natural.command.*;
import server.natural.events.AntiChatRepeating;
import server.natural.events.JoinGroupRequestSelectorListener;
import server.natural.events.OnGroupMessage;
import server.natural.events.OnQuitJoinGroupReplyMessageEvent;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class BootStrap {
    //初始化命令
    public static void initCommands(@NotNull JavaPlugin plugin){
        plugin.getLogger().info(ChatColor.LIGHT_PURPLE + "注册插件命令");
        Bukkit.getPluginCommand("smg").setExecutor(new CommandSMG());
        Bukkit.getPluginCommand("cwg").setExecutor(new CommandCWG());
        Bukkit.getPluginCommand("bind").setExecutor(new CommandBind());
        Bukkit.getPluginCommand("messageforwarding").setExecutor(new CommandStopMessageTrasForwarding());
        Bukkit.getPluginCommand("botinvite").setExecutor(new CommandInvite());
        //An uncompleted method
//        Bukkit.getPluginCommand("choosechannel").setExecutor(new CommandChooseChannel());
    }
    //初始化事件监听器
    public static void initEventListeners(@NotNull JavaPlugin plugin){
        boolean enableMessageForward = plugin.getConfig().getBoolean("Function.EnableGroupToGame");
        boolean enableChatServer = plugin.getConfig().getBoolean("Function.EnableChatServer");
        boolean enableNoChatSpam = plugin.getConfig().getBoolean("Function.EnableAntiChatSpam");
        plugin.getLogger().info(ChatColor.LIGHT_PURPLE + "注册插件事件监听器...");
        //如果启用防刷屏则注册这个监听器
        if(enableNoChatSpam){
            Bukkit.getPluginManager().registerEvents(new AntiChatRepeating(),plugin);
        }
        //如果启用聊天室则初始化聊天室
        if(enableChatServer){
            Bukkit.getLogger().info("Start chat server...");
            Main.server = new SocketServer("0.0.0.0",plugin.getConfig().getInt("ChatServer.Port"));
            Main.server.start();
            Bukkit.getPluginCommand("broadcastmusic").setExecutor(new CommandBroadcastMusic());
            Bukkit.getPluginManager().registerEvents(new MCEventHandler(),plugin);
        }
        //如果启用消息转发则注册改监听器
        if(enableMessageForward){
            Bukkit.getPluginManager().registerEvents(new OnGroupMessage(), plugin);
        }
        //必须要注册的监听器
        Bukkit.getPluginManager().registerEvents(new JoinGroupRequestSelectorListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new OnQuitJoinGroupReplyMessageEvent(), plugin);
    }
    //检查配置文件是否为旧版本或其他版本
    public static void checkIsConfigOldVersion(@NotNull JavaPlugin plugin){
        int cfver = plugin.getConfig().getInt("config-ver");
        if(cfver!=Utils.configVersion){
            plugin.getLogger().warning("ChatWithGroup的配置文件出现了问题，请删除配置文件重新启动服务器，插件会重新生成配置文件");
            //太长的滞留时间会触发watchdog
            plugin.getLogger().warning("服务器将在5秒后继续运行");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            plugin.getLogger().warning("ChatWithGroup配置出现问题，请尽快修复");
            Bukkit.getPluginManager().getPlugin("ChatWithGroup").onDisable();
        }
    }
    public static void AutoAddChannel(@NotNull List list){

    }
}
