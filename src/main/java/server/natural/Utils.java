package server.natural;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    //一些事件或指令常量可直接在此调用
    public static FileConfiguration config = Bukkit.getPluginManager().getPlugin("ChatWithGroup").getConfig();
    public static Plugin plugin = Bukkit.getPluginManager().getPlugin("ChatWithGroup");
    public static File Cfile = new File(plugin.getDataFolder(),"cache\\cache.yml");
    public static FileConfiguration cacheFile = YamlConfiguration.loadConfiguration(Cfile);
    public static File MFCFile = new File(plugin.getDataFolder(),"MsgForwardingChancel.yml");
    public static File PCCFile = new File(plugin.getDataFolder(),"cache\\PlayerChoosedChancelCache.yml");
    public static FileConfiguration mfcfc = YamlConfiguration.loadConfiguration(MFCFile);
    public static FileConfiguration pccfc = YamlConfiguration.loadConfiguration(PCCFile);
    public static String noPermissionMsg = config.getString("NoPermission","您没有权限");
    public static final String ver = "1.4.1";
    public static final int configVersion = 6;
    public static List<Long> group = (List<Long>) config.getList("group");
    public static List<Long> owner = (List<Long>) config.getList("owner");
    public static final boolean isBetaVersion = true;
    //创建线程池
    public static ThreadPoolExecutor executor = null;
    public static void checkUpdate(final String apiURL) throws IOException, InvalidConfigurationException, ExecutionException, InterruptedException {
        File update = new File("CWG\\CWGUrlLog");
        DownloadFile(apiURL,update);
        if(update.exists()){
            YamlConfiguration updateConfig= new YamlConfiguration();
            updateConfig.load(update);
            if (updateConfig.getString("version").equals(ver))
            {
                Bukkit.getLogger().info("您当前处于最新版本，无需更新");
            }else{
                Bukkit.getLogger().warning("新的CWG发布了！！");
                Bukkit.getLogger().warning("下载地址:https://github.com/NaturalCodeClub/ChatWithGroup/releases");
            }
        }

    }
    public static void DownloadFile(String urlStr, File savefile) throws ExecutionException, InterruptedException {
        Future<Boolean> future = executor.submit(()->{
            try {
                if (savefile.exists()) {
                    savefile.delete();
                }
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
                InputStream inputStream = conn.getInputStream();
                byte[] getData = readInputStream(inputStream);
                File file = savefile;
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(getData);
                fos.close();
                inputStream.close();
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        });
        boolean isFinished = future.get();
        if(isFinished){Bukkit.getLogger().log(Level.FINE,"File downloaded!Url was:{}",urlStr);}
    }
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte['Ѐ'];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
    /*
    * 该方法引用了https://blog.csdn.net/qq_31939617/article/details/88312080中第一条判断字符串是否为数字的方法
    * 在此特别感谢该文章的作者
    */
    public static boolean isNumberStrings(String s){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(s);
        return isNum.matches();
    }
    public static void cacheFileSave() {
        try {
            cacheFile.save(Cfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void PCCCacheFileSave(){
        try {
            pccfc.save(PCCFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void LoadFile(@NotNull boolean b){
        Bukkit.getPluginManager().getPlugin("ChatWithGroup").saveResource("cache/cache.yml",b);
        Bukkit.getPluginManager().getPlugin("ChatWithGroup").saveResource("MsgForwardingChancel.yml",b);
        Bukkit.getPluginManager().getPlugin("ChatWithGroup").saveResource("cache/PlayerChoosedChancelCache.yml",b);
    }
    public static void OnFirstRun(){
        long timestamp = System.currentTimeMillis();
        if(cacheFile.get("Created-Time")==null){
            cacheFile.set("Created-Time",timestamp);
            cacheFileSave();
        }
        if(pccfc.get("Created-Time")==null){
            pccfc.set("Created-Time",timestamp);
            PCCCacheFileSave();
        }
    }
}
