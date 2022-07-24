package server.natural.chatwithgroup;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    //一些事件或指令常量可直接在此调用
    public static FileConfiguration config;
    public static Plugin plugin;
    public static File Cfile;
    public static FileConfiguration cacheFile;
    public static File MFCFile;
    public static File PCCFile;
    public static FileConfiguration mfcfc;
    public static FileConfiguration pccfc;
    public static String NoPermission;
    public static List<Long> group;
    public static List<Long> owner;
    public static final String ver = "1.4.1";
    public static final int configVersion = 7;
    public static final boolean isBetaVersion = true;
    public static BukkitScheduler executor = Bukkit.getScheduler();

    public static void checkUpdate(final String apiURL, Main main) throws IOException, InvalidConfigurationException{
        File update = new File("CWG/CWGUrlLog");
        DownloadFile(apiURL,update);
        if(update.exists()){
            YamlConfiguration updateConfig= new YamlConfiguration();
            updateConfig.load(update);
            if (updateConfig.getString("version").equals(ver))
            {
                Bukkit.getLogger().info("您当前处于最新版本，无需更新");
            }else{
                Bukkit.getLogger().warning("新的CWG发布了！！");
                Bukkit.getLogger().warning("版本号为:" + updateConfig.getString("version"));
                Bukkit.getLogger().warning("下载地址:https://github.com/NaturalCodeClub/ChatWithGroup/releases");
            }
        }

    }

    public static void DownloadFile(String urlStr, File savefile) {
        executor.runTaskAsynchronously(Utils.plugin,()->{
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
                Bukkit.getLogger().log(Level.FINE,"File downloaded!Url was:{}",urlStr);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
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
