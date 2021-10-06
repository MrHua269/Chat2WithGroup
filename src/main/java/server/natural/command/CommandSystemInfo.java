package server.natural.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

public class CommandSystemInfo implements CommandExecutor {
    private final MemoryMXBean MemMX = ManagementFactory.getMemoryMXBean();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.isOp()){
            sender.sendMessage("##################################################");
            sender.sendMessage("Memory used : "+MemMX.getHeapMemoryUsage().getUsed());
            sender.sendMessage("Memory committed : "+MemMX.getHeapMemoryUsage().getCommitted());
            sender.sendMessage("Memory heap : "+MemMX.getHeapMemoryUsage().getMax());
            sender.sendMessage("Memory init"+MemMX.getHeapMemoryUsage().getInit());
            sender.sendMessage("服务器负载状态 : "+ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage());
            sender.sendMessage("服务器版本 : "+ Bukkit.getServer().getVersion());
            sender.sendMessage("视距 : "+Bukkit.getServer().getViewDistance());
            sender.sendMessage("##################################################");

        }else{
            sender.sendMessage(ChatColor.RED+"您没有权限执行该命令");
        }
        return true;
    }
}
