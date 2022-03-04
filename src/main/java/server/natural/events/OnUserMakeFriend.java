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
                    Utils.owner.forEach(owner ->{
                        Bot.getApi().sendPrivateMsg(String.valueOf(owner),"CWG配置文件出现问题");
                        Bot.getApi().sendPrivateMsg(String.valueOf(owner), "请仔细检查'AddFriendSelector'");
                        Bot.getApi().sendPrivateMsg(String.valueOf(owner),"正在自动处理");
                    });
                    e.getEvent().accept();
                    break;
            }
        });

    }
}
