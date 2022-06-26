package server.natural.events;

import me.albert.amazingbot.bot.Bot;
import me.albert.amazingbot.events.request.GroupRequestJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import server.natural.Utils;

public class JoinGroupRequestSelectorListener implements Listener {
    //当有用户请求进群时执行
    @EventHandler
    public void onAddGroupRequest(GroupRequestJoinEvent e) {
        String reason = Utils.config.getString("RequestHandler.GroupJoinSelector.RejectReason");
        Utils.executor.runTaskAsynchronously(Utils.plugin,()->{
            switch (Utils.config.getString("RequestHandler.GroupJoinSelector.JoinRequestSelector")) {
                case "YES":
                    e.approve(true,reason);
                    break;
                case "NO":
                    e.approve(false,reason);
                    break;
                case "NOTHING":
                    e.getGroupID();
                    /*
                    下方为过时的代码
                    2022/4/30 17:50 NaT_Jerry
                     */
//                    String nick = e.getEvent().getFromNick();
//                    String ms = e.getEvent().getMessage();
//                    String id = String.valueOf(e.getEvent().getFromId());
//                    boolean isBeenInvited = e.getEvent().getInvitorId()!=null;
//                    Utils.owner.forEach(owner->{
//                        Bot.getApi().sendPrivateMsg(String.valueOf(owner), "有玩家申请加入群");
//                        Bot.getApi().sendPrivateMsg(String.valueOf(owner), nick);
//                        Bot.getApi().sendPrivateMsg(String.valueOf(owner), id);
//                        Bot.getApi().sendPrivateMsg(String.valueOf(owner), "该用户提交的申请内容:" + ms);
//                        Bot.getApi().sendPrivateMsg(String.valueOf(owner), "请核实");
//                        if (isBeenInvited) {
//                            Bot.getApi().sendPrivateMsg(String.valueOf(owner), "邀请人:" + e.getEvent().getInvitorId());
//                        }
//                    });
                    break;
                default:
                    Bukkit.getLogger().warning(ChatColor.RED + "错误的配置文件！");
                    Utils.owner.forEach(owner->{
                        Bot.getApi().sendPrivateMsg(owner, "ChatWithGroup配置文件出现问题");
                        Bot.getApi().sendPrivateMsg(owner, "也许(肯定)是'JoinRequestSelector'的问题");
                    });
                    break;
            }
        });

    }
}
