package server.natural.events;

import me.albert.amazingbot.bot.Bot;
import me.albert.amazingbot.events.FriendRequestEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import server.natural.Utils;

public class OnUserMakeFriend implements Listener {
    @EventHandler
    public void onUserMakeBotFriendEvent(FriendRequestEvent e) {
        Utils.executor.execute(()->{
            switch (Utils.config.getString("RequestHandler.AddFriendSelector")) {
                case "YES":
                    e.getEvent().accept();
                    break;
                case "NO":
                    e.getEvent().reject(Utils.config.getBoolean("RequestHandler.AddFriendSelector.BlackList", false));
                    break;
                case "NOTHING":
                    e.getEvent().getEventId();
                    break;
                default:
                    Bot.getApi().sendPrivateMsg(Utils.ownerInString, "CWG配置文件出现了问题");
                    Bot.getApi().sendPrivateMsg(Utils.ownerInString, "请在config.yml中仔细检查'AddFriendSelector'");
                    Bot.getApi().sendPrivateMsg(Utils.ownerInString, "正在自动处理...");
                    e.getEvent().accept();
                    break;
            }
        });

    }
}
