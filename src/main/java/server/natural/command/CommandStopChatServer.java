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
            commandSender.sendMessage(ChatColor.RED+"聊天服务器暂停!");
            //start the chat server then start
            new Thread(() -> {
                try {
                    int sleeptime = Utils.config.getInt("StartAfterStop") * 1000;
                    Thread.sleep(sleeptime);
                    InitChatServer.Init("0.0.0.0", Utils.config.getInt("ChatServerPort"));
                    commandSender.sendMessage(ChatColor.GREEN + "聊天服务器重新开启");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        return true;
    }
}
