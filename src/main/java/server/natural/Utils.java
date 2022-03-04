package server.natural;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    //一些事件或指令常用的变量可直接在此调用
    public static FileConfiguration config = Bukkit.getPluginManager().getPlugin("ChatWithGroup").getConfig();
    public static final String ver = "${project.version}";
    public static final int configVersion = 5;
    public static long owner = config.getLong("CoreConfig.owner");
    public static String ownerInString = String.valueOf(owner);
    public static long group = config.getLong("CoreConfig.group");
    public static String groupInString = String.valueOf(group);
    public static final boolean isBetaVersion = true;
    //创建线程池
    public static ThreadPoolExecutor executor = null;
    public static void checkUpdate(String apiURL) throws IOException, InvalidConfigurationException, ExecutionException, InterruptedException {
        File update = new File("CWG\\CWGUrlLog");
        DownloadFile(apiURL,update);
        if(update.exists()){
            YamlConfiguration updateConfig= new YamlConfiguration();
            updateConfig.load(update);
            if (updateConfig.getString("version").equals(ver))
            {
                Bukkit.getLogger().info("您当前处于最新版本，无需更新");
            }else{Bukkit.getLogger().info("新的CWG发布了！！");}
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
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
