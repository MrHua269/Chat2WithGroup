package server.natural.chatwithgroup;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Emmm,我想不出任何关于这个类的东西了，或许我们应该把它分开?
 */
public class Utils {
    //一些事件或指令常量可直接在此调用
    public static FileConfiguration config = null;
    public static Plugin plugin = null;
    public static File Cfile = null;
    public static FileConfiguration cacheFile = null;
    public static File MFCFile = null;
    public static File PCCFile = null;
    public static FileConfiguration mfcfc = null;
    public static FileConfiguration pccfc = null;
    public static final String ver = "1.4.1";
    public static final int configVersion = 6;
    public static List<Long> group = null;
    public static List<Long> owner = null;
    public static final boolean isBetaVersion = true;
    public static BukkitScheduler executor = Bukkit.getScheduler();
    public static String noNoPermission = null;

    public static void checkUpdate(final String apiURL, JavaPlugin plugin1) throws IOException, InvalidConfigurationException{
        executor.runTaskAsynchronously(plugin1,()->{
            try{
                File update = new File("CWG\\CWGUrlLog");
                downloadFile(apiURL,update);
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

            }catch (Exception e){
                Bukkit.getLogger().warning("Error in checking update:"+e.getMessage());
            }
        });
    }

    public static void downloadFile(String urlStr, File savefile) {
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
            FileOutputStream fos = new FileOutputStream(savefile);
            fos.write(getData);
            fos.close();
            inputStream.close();
            Bukkit.getLogger().log(Level.FINE, "File downloaded!Url was:{}", urlStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte['Ѐ'];
        int len;
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

    public static void LoadFile(boolean b){
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
