package server.natural.events;

import me.albert.amazingbot.AmazingBot;
import me.albert.amazingbot.bot.Bot;
import me.albert.amazingbot.events.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import server.natural.Utils;

import java.text.MessageFormat;
import java.util.List;

public class AddToTheGroup implements Listener {
    //当有新群员进群时执行
    @EventHandler
    public void onUserJoinGroup(GroupMemberJoinEvent e) {
        Utils.executor.execute(() -> {
            if (e.getEvent().getGroupId() == Utils.config.getLong("group")) {
                List list = Utils.config.getList("MessageOfJoinGroup");
                for (int i = 0; i < list.size(); i++) {
                    //todo Check bugs and fix
                    String s = MessageFormat.format(String.valueOf(list.get(i)), e.getEvent().getUser().getNameCard(), e.getEvent().getUser().getId());
                    Bot.getApi().sendGroupMsg(String.valueOf(e.getEvent().getGroupId()), s);
                }
            }
        });
    }
    @EventHandler
    public void onReplyMessage(TempMessageEvent e){
        Utils.executor.execute(() -> {
            List list = Utils.config.getList("ReplyMessageOnTemp");
            for (int i=0;i<list.size();i++){
                //todo Check bugs and fix
                e.response(e.getEvent().getSenderName() + list);
            }
        });

    }
    //当群员退群时执行
    @EventHandler
    public void onUserLeaveGroup(GroupMemberLeaveEvent e){
        Utils.executor.execute(() -> {
            if (e.getEvent().getGroupId() == Utils.config.getLong("group")) {
                List list = Utils.config.getList("MessageOfQuitGroup");
                for (int i = 0; i < list.size(); i++) {
                    //todo Check bugs and fix
                    String s = MessageFormat.format(String.valueOf(list.get(i)), e.getEvent().getUser().getNameCard(), e.getEvent().getUser().getId());
                    Bot.getApi().sendGroupMsg(String.valueOf(e.getEvent().getGroupId()), s);
                }
            }
        });
    }
}