package server.natural.chatwithgroup.events;

import me.albert.amazingbot.bot.Bot;
import me.albert.amazingbot.events.notice.group.GroupMemberDecreaseEvent;
import me.albert.amazingbot.events.notice.group.GroupMemberIncreaseEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import server.natural.chatwithgroup.Utils;

import java.text.MessageFormat;

public class OnQuitJoinGroupReplyMessageEvent implements Listener {
    //当有新群员进群时执行
    @EventHandler
    public void onUserJoinGroup(GroupMemberIncreaseEvent e) {
        Utils.executor.runTaskAsynchronously(Utils.plugin,()->{
            if (Utils.group.contains(e.getGroupID())) {
                Utils.config.getList("Texts.MessageOfJoinGroup").forEach(message -> {
                    String s = MessageFormat.format((String) message, e.getMember().getCard(), e.getMember().getUserID());
                    Bot.getApi().sendGroupMsg(e.getGroupID(), s);
                });
            }
        });
    }

    //留着防止bug发生不能修复
   /*@EventHandler
    public void onReplyMessage(MessageReceiveEvent e) {
        Utils.executor.execute(()->{
            if(Utils.owner.contains(e.getEvent().getSender().getId()))
            Utils.config.getList("Texts.ReplyMessageOnTemp").forEach(msg -> {
                String s = MessageFormat.format((String) msg, e.getEvent().getSender().getNick(), e.getEvent().getSender().getId());
                e.response(e.getEvent().getSenderName() + s);
            });
        });
    }*/
    //当群员退群时执行

    @EventHandler
    public void onUserLeaveGroup(GroupMemberDecreaseEvent e) {
        Utils.executor.runTaskAsynchronously(Utils.plugin,()-> Utils.config.getList("Texts.MessageOfQuitGroup").forEach(msg -> {
            String s = MessageFormat.format((String) msg, e.getUserID());
            Bot.getApi().sendGroupMsg(e.getGroupID(), s);
        }));

    }
}