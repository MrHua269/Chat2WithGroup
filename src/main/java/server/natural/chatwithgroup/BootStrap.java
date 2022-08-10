package server.natural.chatwithgroup;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import server.natural.chatwithgroup.command.*;
import server.natural.chatwithgroup.events.AntiChatRepeating;
import server.natural.chatwithgroup.events.JoinGroupRequestSelectorListener;
import server.natural.chatwithgroup.events.OnGroupMessage;
import server.natural.chatwithgroup.events.OnQuitJoinGroupReplyMessageEvent;

import java.io.File;
import java.util.List;

public class BootStrap {
    //初始化命令
    public static void initCommands(@NotNull JavaPlugin plugin){
        plugin.getLogger().info(ChatColor.LIGHT_PURPLE + "注册插件命令");
        Bukkit.getPluginCommand("smg").setExecutor(new CommandSMG());
        Bukkit.getPluginCommand("cwg").setExecutor(new CommandCWG());
        Bukkit.getPluginCommand("bind").setExecutor(new CommandBind());
        Bukkit.getPluginCommand("messageforwarding").setExecutor(new CommandStopMessageTrasForwarding());
        Bukkit.getPluginCommand("botinvite").setExecutor(new CommandInvite());
        Bukkit.getPluginCommand("purgecache").setExecutor(new CommandPurgeCache());
        //An uncompleted method
        //TODO Complete the unfinished code block
        //Bukkit.getPluginCommand("choosechannel").setExecutor(new CommandChooseChannel());
    }
    public static void ChannelAddAutomatic(@NotNull JavaPlugin plugin){
        //todo 思考思路中
    }

    //初始化事件监听器
    public static void initEventListeners(@NotNull JavaPlugin plugin){
        boolean enableMessageForward = plugin.getConfig().getBoolean("Function.EnableGroupToGame");
        boolean enableNoChatSpam = plugin.getConfig().getBoolean("Function.EnableAntiChatSpam");
        plugin.getLogger().info(ChatColor.LIGHT_PURPLE + "注册插件事件监听器...");
        //如果启用防刷屏则注册这个监听器
        if(enableNoChatSpam){
            Bukkit.getPluginManager().registerEvents(new AntiChatRepeating(),plugin);
        }
        //如果启用消息转发则注册改监听器
        if(enableMessageForward){
            Bukkit.getPluginManager().registerEvents(new OnGroupMessage(), plugin);
        }
        //必须要注册的监听器
        Bukkit.getPluginManager().registerEvents(new JoinGroupRequestSelectorListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new OnQuitJoinGroupReplyMessageEvent(), plugin);
    }

    public static void initConfig(JavaPlugin plugin){
        FileConfiguration currentConfig = plugin.getConfig();
        Utils.config = currentConfig;
        Utils.plugin = plugin;
        Utils.NoPermission = currentConfig.getString("Text.NoPermission");
        Utils.group = (List<Long>) currentConfig.getList("CoreConfig.group");
        Utils.owner = (List<Long>) currentConfig.getList("CoreConfig.owner");
    }

    public static void initFiles(JavaPlugin plugin){
        Utils.Cfile = new File(plugin.getDataFolder(),"cache/cache.yml");
        Utils.cacheFile = YamlConfiguration.loadConfiguration(Utils.Cfile);
        Utils.MFFile = new File(plugin.getDataFolder(),"MsgForwardingChancel.yml");
        Utils.PCCFile = new File(plugin.getDataFolder(),"cache/PlayerChoosedChancelCache.yml");
        Utils.mffc = YamlConfiguration.loadConfiguration(Utils.MFFile);
        Utils.pccfc = YamlConfiguration.loadConfiguration(Utils.PCCFile);
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
}
