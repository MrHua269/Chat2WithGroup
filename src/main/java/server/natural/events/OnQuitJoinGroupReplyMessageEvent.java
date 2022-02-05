package server.natural.events;

import me.albert.amazingbot.bot.Bot;
import me.albert.amazingbot.events.GroupMemberJoinEvent;
import me.albert.amazingbot.events.GroupMemberLeaveEvent;
import me.albert.amazingbot.events.TempMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import server.natural.Main;
import server.natural.Utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class OnQuitJoinGroupReplyMessageEvent implements Listener {
    //当有新群员进群时执行
    @EventHandler
    public void onUserJoinGroup(GroupMemberJoinEvent e) {
        Utils.executor.execute(() -> {
            if (e.getEvent().getGroupId() == Utils.group) {
                List<String> list = (List<String>)Utils.config.getList("Texts.MessageOfJoinGroup");
                for (String messages:list) {
                    //todo Check bugs and fix
                    String s = MessageFormat.format(messages, e.getEvent().getUser().getNameCard(), e.getEvent().getUser().getId());
                    Bot.getApi().sendGroupMsg(String.valueOf(e.getEvent().getGroupId()), s);
                }
            }
        });
    }
    @EventHandler
    public void onReplyMessage(TempMessageEvent e){
        Utils.executor.execute(() -> {
            List<String> list = (List<String>) Utils.config.getList("Texts.ReplyMessageOnTemp");
            for (String messages:list) {
                //todo Check bugs and fix
                String s = MessageFormat.format(messages, e.getEvent().getSender().getNameCard(), e.getEvent().getSender().getId());
                e.response(e.getEvent().getSenderName()+s);
            }
        });

    }
    //当群员退群时执行
    @EventHandler
    public void onUserLeaveGroup(GroupMemberLeaveEvent e){
        Utils.executor.execute(() -> {
            if (e.getEvent().getGroupId() == Utils.group) {
                List<String> list = (List<String>)Utils.config.getList("Texts.MessageOfQuitGroup");
                for (String messages:list) {
                    //todo Check bugs and fix
                    String s = MessageFormat.format(messages, e.getEvent().getUser().getNameCard(), e.getEvent().getUser().getId());
                    Bot.getApi().sendGroupMsg(String.valueOf(e.getEvent().getGroupId()), s);
                }
            }
        });
    }
}