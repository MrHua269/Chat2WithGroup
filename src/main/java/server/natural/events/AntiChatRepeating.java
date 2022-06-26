package server.natural.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import server.natural.Utils;

import java.util.concurrent.ConcurrentHashMap;

public class AntiChatRepeating implements Listener {
    private static final ConcurrentHashMap<Player, Long> playerCounts = new ConcurrentHashMap<>();
    @EventHandler
    public void onPlayerMessage(AsyncPlayerChatEvent event) {
        if (isOutOfLimit(event.getPlayer(), Utils.config.getLong("MessageLimit.MessageSlotLimit"))) {
            event.getPlayer().sendMessage(Utils.config.getString("Texts.MessageOutOfLimit"));
            event.setCancelled(true);
        }
    }
    public static boolean isOutOfLimit(Player player, long dealy) {
        boolean isOut = false;
        if (!playerCounts.containsKey(player)){
            playerCounts.put(player, System.currentTimeMillis());
            return false;
        }
        if (playerCounts.get(player) + dealy > System.currentTimeMillis()){
            isOut = true;
        }
        playerCounts.replace(player, System.currentTimeMillis());
        return isOut;
    }
}
