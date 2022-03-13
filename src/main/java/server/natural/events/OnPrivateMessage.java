package server.natural.events;

import me.albert.amazingbot.bot.Bot;
import me.albert.amazingbot.events.MessageReceiveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import server.natural.Utils;

import java.util.UUID;

public class OnPrivateMessage implements Listener {
    @EventHandler
    public void PrivateMessage(MessageReceiveEvent event){
        Utils.executor.execute(()->{
            String message = event.getMsg();
            if(!message.startsWith(Utils.config.getString("BindSettings.BindPrefix","同意绑定"))) return;
            String[] arr = message.split("\\s+");
            if(arr.length != 2)return;
            if(event.getEvent().getSender().getId()!=Utils.cacheFile.getLong("players." + arr[1] + ".QQID")||Utils.cacheFile.get("players." + arr[1])==null||
            Utils.cacheFile.getBoolean("players." + arr[1] + ".ActionDone"))
                return;
            if(arr[1].equals(Utils.cacheFile.getString("players." + arr[1] + ".Name"))){
                Object ob = Utils.cacheFile.get("players." + arr[1] + ".UUID");
                UUID uuid = (UUID) ob;
                Bot.getApi().setBind(event.getEvent().getSender().getId(),uuid);
                Utils.cacheFile.set("players." + arr[1] + "ActionDone",true);
                event.response("绑定完成");
            }
        });
    }
}
