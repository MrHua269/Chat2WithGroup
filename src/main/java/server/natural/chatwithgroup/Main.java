package server.natural.chatwithgroup;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        try {
            BootStrap.initFiles(this);
            getLogger().info(ChatColor.GREEN + "欢迎使用!");
            saveDefaultConfig();
            //让服务器歇3秒
            Thread.sleep(3000);
            //初始化配置文件
            BootStrap.initConfig(this);
            //加载其他缓存或配置文件
            Utils.LoadFile(false);
            Utils.OnFirstRun();
            //初始化事件监听器
            BootStrap.initEventListeners(this);
            //注册命令
            BootStrap.initCommands(this);
            getLogger().info(ChatColor.GREEN + "准备就绪!");
            BootStrap.checkIsConfigOldVersion(this);
            if (Utils.isBetaVersion) {
                getLogger().warning("该版本为测试版本，Bug可能较多，若发现Bug请在Github反馈");
            }
            //更新检查的apiURL必须是常量！
            if (getConfig().getBoolean("CheckUpdate")) {
                Utils.checkUpdate("https://naturalcodeclub.github.io/CWGCheck/CWGCheck.yml",this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDisable() {
        getLogger().info("再见");
    }


}
