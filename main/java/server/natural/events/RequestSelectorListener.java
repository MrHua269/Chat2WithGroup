package server.natural.events;
import me.albert.amazingbot.events.GroupRequestJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import server.natural.Utils;
import java.util.Objects;
public class RequestSelectorListener implements Listener {
    //当有用户请求进群时执行
    @EventHandler
    public void onAddGroupRequest(GroupRequestJoinEvent e) {
        if(Objects.equals(Utils.config.getString("JoinRequestSelector"), "ACCEPT")){
            e.getEvent().accept();
        }else if(Objects.equals(Utils.config.getString("JoinRequestSelector"), "DROP")){
            e.getEvent().reject();
        }else{
            Bukkit.getLogger().warning(ChatColor.RED+"错误的配置文件！");
        }
    }
}
