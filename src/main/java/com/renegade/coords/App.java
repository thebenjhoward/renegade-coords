package com.renegade.coords;

import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin
{
    @Override
    public void onEnable() {
        getLogger().info("RenegadeCoords Enabled");
        saveDefaultConfig();
        Utils.readConfig(getConfig(), this);
        this.getCommand("coordinates").setExecutor(new Coordinates());
        CoordinatesTimer.run(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("RenegadeCoords Disabled");
    }
}
