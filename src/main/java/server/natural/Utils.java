package server.natural;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Utils {
    //一些事件或指令常用的变量可直接在此调用
    public static FileConfiguration config = Bukkit.getPluginManager().getPlugin("ChatWithGroup").getConfig();
    public static String ver = "1.3.4";
    public static ConcurrentHashMap<String,Long> forwardList = null;
    public static long owner = config.getLong("owner");
    public static String ownerInString = String.valueOf(owner);
    public static long group = config.getLong("group");
    public static String groupInString = String.valueOf(group);
    public static boolean isOpenChatServer = Boolean.parseBoolean(config.getString("EnableChatServer"));
    //创建线程池
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
