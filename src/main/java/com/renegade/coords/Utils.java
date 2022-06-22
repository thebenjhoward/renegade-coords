package com.renegade.coords;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Utils {
    
    /** List of players with coords currently enabled (from config.yml) */
    private static List<String> players;
    /** Loadable configuration file (defaults in resources/config.yml) */
    private static FileConfiguration config;
    /** Plugin object for this plugin */
    private static Plugin plugin;
    /** Number of ticks between updates of coordinates (from config.yml) */
    private static int ticks;
    /** Whether to default to all players having coordinates on by default (from config.yml) */ 
    private static boolean defaultEnabled;

    /**
     * Gets the configuration for the plugin and populates class with current configuration
     * @param config config object loaded by calling 'getConfig()' from the plugin
     * @param plugin plugin object this class belongs to
     */
    public static void readConfig(FileConfiguration config, Plugin plugin) {
        Utils.config = config;
        Utils.plugin = plugin;
        ticks = Integer.parseInt(config.getString("ticks"));
        defaultEnabled = config.getBoolean("default-enabled");
        players = config.getStringList("players");
    }

    public static int getTicks() {
        return ticks;
    }

    public static boolean getDefaultEnabled() {
        return defaultEnabled;
    }

    public static List<String> getPlayers() {
        if (players == null) {
            return new ArrayList<>();
        } else {
            return players;
        }
    }

    /**
     * Checks whether a player currently has coordinates enabled
     * @param player the player being checked
     * @return true if the player has coordinates enabled
     */
    public static boolean checkPlayers(Player player) {
        return getPlayers().contains(player.getName());
    }

    /**
     * Enables coordinates for a player and saves current configuration
     * @param player the player being added
     */
    public static void addPlayer(Player player) {
        if (!getPlayers().contains(player.getName())) {
            players.add(player.getName());
        }

        config.set("players", players);
        plugin.saveConfig();
    }

    /**
     * Disables coordinates for a player and saves current configuration
     * @param player the player being added
     */
    public static void removePlayer(Player player) {
        players.remove(player.getName());

        config.set("players", players);
        plugin.saveConfig();
    }
    
    
    public static void sendMsg(CommandSender player, String msg) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void sendPlayerCoords(Player player) {
        Location l = player.getLocation();

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, 
                new ComponentBuilder(ChatColor.GOLD + "X: " + ChatColor.WHITE + l.getBlockX() + " " +
                        ChatColor.GOLD + "Y: " + ChatColor.WHITE + l.getBlockY() + " " +
                        ChatColor.GOLD + "Z: " + ChatColor.WHITE + l.getBlockZ() + " " +
                        ChatColor.GREEN + String.format("%-10s", rpGetPlayerDirection(player))).create());
    }

    public static String rpGetPlayerDirection(Player playerSelf){
        String dir = "";
        float y = playerSelf.getLocation().getYaw();
        if( y < 0 ){y += 360;}
        y %= 360;
        int i = (int)((y+8) / 22.5);
        if(i == 4){dir = "W";}
        else if(i == 5){dir = "WNW";}
        else if(i == 6){dir = "NW";}
        else if(i == 7){dir = "NNW";}
        else if(i == 8){dir = "N";}
        else if(i == 9){dir = "NNE";}
        else if(i == 10){dir = "NE";}
        else if(i == 11){dir = "ENE";}
        else if(i == 12){dir = "E";}
        else if(i == 13){dir = "ESE";}
        else if(i == 14){dir = "SE";}
        else if(i == 15){dir = "SSE";}
        else if(i == 0){dir = "S";}
        else if(i == 1){dir = "SSW";}
        else if(i == 2){dir = "SW";}
        else if(i == 3){dir = "WSW";}
        else {dir = "S";}
        return dir;
   }
}
