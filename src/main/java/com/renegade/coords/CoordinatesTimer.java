package com.renegade.coords;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CoordinatesTimer {
    public static void run(Plugin plugin) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player p : plugin.getServer().getOnlinePlayers()) {
                    if ((Utils.checkPlayers(p) && !Utils.getDefaultEnabled()) || (!Utils.checkPlayers(p) && Utils.getDefaultEnabled())) {
                        Utils.sendPlayerCoords(p);
                    }
                }
            }
        }, 0L, Utils.getTicks());
    }
}
