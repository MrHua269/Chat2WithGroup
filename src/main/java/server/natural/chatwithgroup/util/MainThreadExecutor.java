package server.natural.chatwithgroup.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;

public class MainThreadExecutor implements Executor {
    private final Queue<Runnable> tasks = new ConcurrentLinkedDeque<>();

    @Override
    public void execute(@NotNull Runnable command) {
        tasks.add(command);
    }

    public MainThreadExecutor(Plugin pluginIn){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(pluginIn,()->{
            for (;;){
                try {
                    Runnable task = tasks.poll();
                    if (task!=null){
                        task.run();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },0,1);
    }
}
