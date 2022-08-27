package server.natural.chatwithgroup;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    //一些事件或指令常量可直接在此调用
    public static FileConfiguration config;
    public static Plugin plugin;
    public static File Cfile;
    public static FileConfiguration cacheFile;
    public static File MFFile;
    public static File PCCFile;
    public static FileConfiguration mffc;
    public static FileConfiguration pccfc;
    public static String NoPermission;
    public static List<Long> group;
    public static List<Long> owner;
    public static ConcurrentHashMap<Integer,Long> ChannelID;
    public static final String ver = "1.4.1";
    public static final int configVersion = 7;
    public static final boolean isBetaVersion = true;
    public static BukkitScheduler executor = Bukkit.getScheduler();

    /**
     * 检查更新
     * @param apiURL
     * @param main
     * @throws IOException
     * @throws InvalidConfigurationException
     */
    public static void checkUpdate(final String apiURL, Main main) throws IOException, InvalidConfigurationException{
        File update = new File(plugin.getDataFolder(),"CWG/CWGUrlLog.yml");
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

    /**
     * 下载文件
     * @param urlStr
     * @param savefile
     */
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

    public static void LoadFile(@NotNull boolean b){
        Bukkit.getPluginManager().getPlugin("ChatWithGroup").saveResource("cache/cache.yml",b);
        Bukkit.getPluginManager().getPlugin("ChatWithGroup").saveResource("MsgForwardingChancel.yml",b);
        Bukkit.getPluginManager().getPlugin("ChatWithGroup").saveResource("cache/PlayerChoosedChancelCache.yml",b);
    }

    public static void OnFirstRun(@NotNull boolean isForce){
        long timestamp = System.currentTimeMillis();
        if(cacheFile.get("Created-Time")==null||isForce){
            cacheFile.set("Created-Time",timestamp);
            cacheFileSave();
        }
        if(pccfc.get("Created-Time")==null||isForce){
            pccfc.set("Created-Time",timestamp);
            PCCCacheFileSave();
        }
    }
    public static String reloadCache(){
        String error = null;
        File cacheFile = Utils.Cfile;
//        commandSender.sendMessage(ChatColor.GREEN + "Reloading Cache File...");
        if (cacheFile.exists()) {
            try {
                Utils.cacheFile.load(cacheFile);
//                commandSender.sendMessage(ChatColor.GREEN + "Cache File Reloaded Completed!");
            } catch (Exception e) {
//                commandSender.sendMessage(ChatColor.RED + "An Exception happened. Cache file can't reload");
//                commandSender.sendMessage(ChatColor.RED + "Exception Message:");
//                commandSender.sendMessage(ChatColor.RED + e.getMessage());
                error="Cache File cannot reload successfully,A Exception Message is Caught:" + e.getMessage();
            }
        }
        File MFFile = Utils.MFFile;
        if (MFFile.exists()) {
            try {
                Utils.mffc.load(MFFile);
//                commandSender.sendMessage(ChatColor.GREEN + "Cache File Reloaded Completed");
            } catch (Exception e) {
//                commandSender.sendMessage(ChatColor.RED + "An Exception happened. Message Forwarding Cache file can't reload");
//                commandSender.sendMessage(ChatColor.RED + "Exception Message:");
//                commandSender.sendMessage(ChatColor.RED + e.getMessage());
                error = error + "Message Forwarding Cache File cannot reload successfully,A Exception Message is Caught:" + e.getMessage();
            }
        }
        File PCCFile = Utils.PCCFile;
        if (PCCFile.exists()) {
            try {
                Utils.pccfc.load(PCCFile);
//                commandSender.sendMessage(ChatColor.GREEN + "Cache File Reloaded Completed");
            } catch (Exception e) {
//                commandSender.sendMessage(ChatColor.RED + "An Exception happened. Player Chancel Cache file can't reload");
//                commandSender.sendMessage(ChatColor.RED + "Exception Message:");
//                commandSender.sendMessage(ChatColor.RED + e.getMessage());
                error = error + "Player Channel Cache File cannot reload successfully,A Exception Message is Caught:" + e.getMessage();
            }
        }
        if(error!=null){
            return "Cache File reload successfully";
        }else{
            return error;
        }
    }
    public static void InitChannelData(@NotNull Plugin p){
        executor.runTaskAsynchronously(p,()->{
            int time = 1;
            for(Long i : group){
                if(config.get("group." + i)!=null){
                    ChannelID.put(mffc.getInt("group." + i + ".id"),i);
                }else{
                    mffc.set("group." + i + ".id",time);
                    mffc.set("group." + i + ".name","Channel " + time);
                    ChannelID.put(time,i);
                    try {
                        mffc.save(MFFile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                time++;
            }
        });
    }
}
