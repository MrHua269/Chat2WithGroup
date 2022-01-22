package server.natural;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Utils {
    public static FileConfiguration config = Bukkit.getPluginManager().getPlugin("ChatWithGroup").getConfig();
    public static String ver = "1.3.3";
    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(config.getInt("ThreadCount"),Integer.MAX_VALUE,Long.MAX_VALUE, TimeUnit.DAYS,new ArrayBlockingQueue<>(100));
    public static ThreadPoolExecutor executor2 = new ThreadPoolExecutor(config.getInt("ThreadCount"),Integer.MAX_VALUE,Long.MAX_VALUE, TimeUnit.DAYS,new ArrayBlockingQueue<>(100));
    public static void sleepAway(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
