package server.natural.command;

import co.novau233.socketServer.Handlers.AsyncEventHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandBroadcastMusic implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender.isOp()){
            if(strings.length==1){
                AsyncEventHandler.broadcastMusic(strings[0]);
            }
        }
        return true;
    }
}
