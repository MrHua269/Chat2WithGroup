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
        Utils.executor.execute(() -> {
            switch (Utils.config.getString("RequestHandler.JoinRequestSelector")){
                case "YES":
                    e.getEvent().accept();
                    break;
                case "NO":
                    e.getEvent().reject();
                    break;
                case "NOTHING":
                    String nick = e.getEvent().getFromNick();
                    String id = String.valueOf(e.getEvent().getGroupId());
                    Bot.getApi().sendPrivateMsg(Utils.ownerInString, "有玩家申请加入群");
                    Bot.getApi().sendPrivateMsg(Utils.ownerInString,nick );
                    Bot.getApi().sendPrivateMsg(Utils.ownerInString,id );
                    break;
                default:
                    Bukkit.getLogger().warning(ChatColor.RED + "错误的配置文件！");
                    Bot.getApi().sendPrivateMsg(Utils.ownerInString, "ChatWithGroup配置文件出现问题");
                    Bot.getApi().sendGroupMsg(Utils.ownerInString, "也许(肯定)是'JoinRequestSelector'的问题");
                    break;
            }
        });
    }
}
