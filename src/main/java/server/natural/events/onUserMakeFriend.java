package server.natural.events;

import me.albert.amazingbot.bot.Bot;
import me.albert.amazingbot.events.FriendRequestEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import server.natural.Utils;

public class onUserMakeFriend implements Listener {
    @EventHandler
    public void onUserMakeBotFriendEvent(FriendRequestEvent e){
        Utils.executor.execute(() -> {
            if(Utils.config.getString("AddFriendSelector").equals("YES")){
                e.getEvent().accept();
            }else if (Utils.config.getString("AddFriendSelector").equals("NO")){
                e.getEvent().reject(false);
            }else if (Utils.config.getString("AddFriendSelector").equals("NOTHING")){
                e.getEvent().getEventId();
            }else{
                Bot.getApi().sendPrivateMsg(Utils.ownerInString, "CWG配置文件出现了问题");
                Bot.getApi().sendPrivateMsg(Utils.ownerInString, "请在config.yml中仔细检查'AddFriendSelector'");
                Bot.getApi().sendPrivateMsg(Utils.ownerInString, "正在自动处理...");
                e.getEvent().accept();

            }
        });


    }
}
