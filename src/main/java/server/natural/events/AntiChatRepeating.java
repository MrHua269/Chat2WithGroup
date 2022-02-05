package server.natural.events;

import io.netty.channel.Channel;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import server.natural.Utils;

import java.util.concurrent.ConcurrentHashMap;

public class AntiChatRepeating implements Listener {
    private static ConcurrentHashMap<Player, Long> playerCounts = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Channel, Long> channelCounts = new ConcurrentHashMap<>();
    @EventHandler
    public void onPlayerMessage(AsyncPlayerChatEvent event) {
        if (isOutOfLimit(event.getPlayer(), Utils.config.getLong("MessageLimit.MessageSlotLimit"))) {
            event.getPlayer().sendMessage(new StringFormattedMessage(Utils.config.getString("Texts.MessageOutOfLimit")).getFormat());
            event.setCancelled(true);
        }
    }

    public static boolean isOutOfLimit(Player player, long dealy) {
        boolean isOut = false;
        if (!playerCounts.contains(player)) playerCounts.put(player, System.currentTimeMillis());
        if (playerCounts.get(player) + dealy > System.currentTimeMillis()) isOut = true;
        playerCounts.replace(player, System.currentTimeMillis());
        return isOut;
    }
    public static boolean isOutOfLimit(Channel channel, long dealy) {
        boolean isOut = false;
        if (!channelCounts.contains(channel)) channelCounts.put(channel, System.currentTimeMillis());
        if (channelCounts.get(channel) + dealy > System.currentTimeMillis()) isOut = true;
        channelCounts.replace(channel, System.currentTimeMillis());
        return isOut;
    }
}
