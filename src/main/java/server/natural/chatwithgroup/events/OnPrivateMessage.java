package server.natural.chatwithgroup.events;

import me.albert.amazingbot.bot.Bot;
import me.albert.amazingbot.events.message.MessageReceiveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import server.natural.chatwithgroup.Utils;

import java.util.UUID;

public class OnPrivateMessage implements Listener {
    @EventHandler
    public void PrivateMessage(MessageReceiveEvent event){
        Utils.executor.runTaskAsynchronously(Utils.plugin,()->{
            String message = event.getMsg();
            if(message.startsWith(Utils.config.getString("BindSettings.BindPrefix","同意绑定"))) {
                String[] arr = message.split("\\s+");
                if(arr.length != 2)return;
                if(event.getUserID()!=Utils.cacheFile.getLong("players." + arr[1] + ".QQID")||Utils.cacheFile.get("players." + arr[1])==null||
                        Utils.cacheFile.getBoolean("players." + arr[1] + ".ActionDone"))
                    return;
                if(arr[1].equals(Utils.cacheFile.getString("players." + arr[1] + ".Name"))){
                    UUID uuid = UUID.fromString(Utils.cacheFile.getString("players." + arr[1] + ".UUID"));
                    Bot.getApi().setBind(event.getUserID(),uuid);
                    Utils.cacheFile.set("players." + arr[1] + "ActionDone",true);
                    event.response("绑定完成");
                }else{
                    StringBuilder str = null;
                    for(Object s:Utils.config.getList("ReplyMessageOnPrivate")){
                            String s1 = String.valueOf(s);
                            if(str!=null){
                                str.append(s1).append("\n");
                            }else{
                                str = new StringBuilder(s1 + "\n");
                            }
                    }
                    event.response(str.toString());
                }
            }

        });
    }
}
