package server.natural.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import server.natural.ChatServer.BaseServer;

public class CommandStopChatServer implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.isOp()){
            //Stop the chat server
            BaseServer.thread.interrupt();
            Utils.executor2.shutdown();
            commandSender.sendMessage(ChatColor.RED+"聊天服务器暂停!");
        }
        return true;
    }
}
