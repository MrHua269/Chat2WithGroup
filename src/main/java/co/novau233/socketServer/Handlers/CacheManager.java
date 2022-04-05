package co.novau233.socketServer.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import server.natural.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CacheManager {
    private static File cacheFile = new File("plugins\\ChatWithGroup\\UserCaches");
    public static YamlConfiguration cacheFileYML = new YamlConfiguration();
    private static ScheduledThreadPoolExecutor executor = null;
    public static void load(){
        try {
            if(!cacheFile.exists()){cacheFile.createNewFile();}
            cacheFileYML.load(cacheFile);
            if (!cacheFileYML.contains("Head")) {
                cacheFileYML.set("Version", Utils.ver);
            }
            Bukkit.getLogger().info("User cache file loaded!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor=new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(() -> {
            try {
                cacheFileYML.save(cacheFile);
                Bukkit.getLogger().info("User cache saved!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 10, TimeUnit.MINUTES);
    }
    public static void unLoad(){
        executor.shutdown();
    }
}
