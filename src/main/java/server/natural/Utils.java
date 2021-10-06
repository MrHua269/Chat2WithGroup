package server.natural;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
public class Utils {
    public static FileConfiguration config = Bukkit.getPluginManager().getPlugin("ChatWithGroup").getConfig();
    public static long groupID = config.getLong("group");
    public static String group = String.valueOf(config.getLong("group"));
    public static boolean enableinvite = Boolean.valueOf(config.getString("EnableInvite"));
    public static boolean gametogroup = Boolean.valueOf(config.getString("GameToGroup"));
}
