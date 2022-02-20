package server.natural;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ThreadPoolExecutor;

public class Utils {
    //一些事件或指令常用的变量可直接在此调用
    public static FileConfiguration config = Bukkit.getPluginManager().getPlugin("ChatWithGroup").getConfig();
    public static final String ver = "1.3.5";
    public static final int configVersion = 5;
    public static long owner = config.getLong("CoreConfig.owner");
    public static String ownerInString = String.valueOf(owner);
    public static long group = config.getLong("CoreConfig.group");
    public static String groupInString = String.valueOf(group);
    public static final boolean isBetaVersion = true;
    //创建线程池
    public static ThreadPoolExecutor executor = null;
    public static void checkUpdate(String apiURL) throws IOException, InvalidConfigurationException {
        InputStream stram = new URL(apiURL).openStream();
        FileOutputStream updateFile = new FileOutputStream("plugin\\ChatWithGroup\\updateCache");
        byte[] cache = new byte[1024];
        int length;
        length= stram.read(cache);
        while(length>0){
            length= stram.read(cache);
            updateFile.write(cache,0,length);
        }
        stram.close();
        updateFile.close();
        File update = new File("plugin\\ChatWithGroup\\updateCache");
        if(update.exists()){
            YamlConfiguration updateConfig= new YamlConfiguration();
            updateConfig.load(update);
            if (updateConfig.getString("version").equals(ver))
            {
                Bukkit.getLogger().info("您当前处于最新版本，无需更新");
            }else{Bukkit.getLogger().info("新的CWG发布了！！");}
        }

    }
}
