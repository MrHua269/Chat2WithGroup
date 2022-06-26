package server.natural.events;

import me.albert.amazingbot.bot.Bot;
import me.albert.amazingbot.events.request.FriendRequestEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import server.natural.Utils;

public class OnUserMakeFriend implements Listener {
    @EventHandler
    public void onUserMakeBotFriendEvent(FriendRequestEvent e) {
        Utils.executor.runTaskAsynchronously(Utils.plugin,()->{
            switch (Utils.config.getString("RequestHandler.AddFriendSelector")) {
                case "YES":
                    e.approve(true,null);
                    break;
                case "NO":
                    e.approve(false,null);
                    break;
                case "NOTHING":
                    e.getHandlers();
                    break;
                default:
                    Utils.owner.forEach(owner ->{
                        Bot.getApi().sendPrivateMsg(owner,"CWG配置文件出现问题");
                        Bot.getApi().sendPrivateMsg(owner, "请仔细检查'AddFriendSelector'");
                        Bot.getApi().sendPrivateMsg(owner,"正在自动处理");
                    });
                    e.approve(true,null);
                    break;
            }
        });

    }
}
