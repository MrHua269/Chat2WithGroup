package server.natural.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import server.natural.ChatServer.BaseServer;
import server.natural.ChatServer.InitChatServer;
import server.natural.Utils;

public class CommandStopChatServer implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.isOp()){
            //Stop the chat server
            BaseServer.thread.interrupt();
            Bukkit.getLogger().info(ChatColor.RED+"Chat server stopped!");
            //start the chat server after 10 seconds
            new Thread(() -> {
                try {
                    Thread.sleep(100000);
                    InitChatServer.Init("0.0.0.0", Utils.config.getInt("ChatServerPort"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        return true;
    }
}
