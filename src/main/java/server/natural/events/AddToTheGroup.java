package server.natural.events;

import me.albert.amazingbot.AmazingBot;
import me.albert.amazingbot.bot.Bot;
import me.albert.amazingbot.events.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import server.natural.Utils;
public class AddToTheGroup implements Listener {
    //当有新群员进群时执行
    @EventHandler
    public void onNewMemberJoinGroup(GroupMemberJoinEvent e) {
        String newUserID = e.getEvent().getUser().getNameCard();
        if(e.getEvent().getGroupId() == Utils.groupID){
        Bot.getApi().sendGroupMsg(String.valueOf(e.getEvent().getGroupId()), "欢迎我们的一位新成员！" + newUserID);
        Bot.getApi().sendGroupMsg(String.valueOf(e.getEvent().getGroupId()), "ta的QQ号为" + e.getEvent().getUser().getId());
        Bot.getApi().sendGroupMsg(String.valueOf(e.getEvent().getGroupId()), "获取白名单请咨询群主!! ");//指定群的新成员入群的处理方法
        }else {
            Bot.getApi().sendGroupMsg(String.valueOf(e.getEvent().getGroupId()), "欢迎我们的一位新成员！" + newUserID);
            Bot.getApi().sendGroupMsg(String.valueOf(e.getEvent().getGroupId()), "ta的QQ号为" + e.getEvent().getUser().getId());
                //其他非指定群的加群处理方法
        }
    }
    @EventHandler
    public void onReplyMessage(TempMessageEvent e){
        e.response(e.getEvent().getSenderName() + "有什么事不能到群里问吗，你这么找我这个傻逼机器人我很为难的好吧");
    }
    //当群员退群时执行
    @EventHandler
    public void onMemberLeaveGroup(GroupMemberLeaveEvent e){
        if(e.getEvent().getGroupId() == Utils.groupID) {
            Bot.getApi().sendGroupMsg(String.valueOf(e.getEvent().getGroupId()), "糟糕了，我们的群丢了一名宝贵的群员，请我们珍惜我们在这个群的每时每秒....");
            Bot.getApi().sendGroupMsg(String.valueOf(e.getEvent().getGroupId()), "离群人的名字" + e.getEvent().getUser().getNameCard());
            Bot.getApi().sendGroupMsg(String.valueOf(e.getEvent().getGroupId()), "离群人的QQ" + e.getEvent().getUser().getId());
        }
    }
}