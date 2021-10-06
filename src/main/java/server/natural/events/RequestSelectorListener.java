package server.natural.events;
import me.albert.amazingbot.bot.Bot;
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
            Bot.getApi().sendGroupMsg(Utils.group,"ChatWithGroup配置文件出现问题，请腐竹修改！");
            Bukkit.getLogger().warning(ChatColor.RED+"错误的配置文件！请更改配置文件，已自动确认邀请");
            e.getEvent().accept();
        }
    }
}
