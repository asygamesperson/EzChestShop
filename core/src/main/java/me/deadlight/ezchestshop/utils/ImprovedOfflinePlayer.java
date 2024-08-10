package me.deadlight.ezchestshop.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.logging.Level;

public abstract class ImprovedOfflinePlayer {

    protected OfflinePlayer player;
    protected boolean isOnline;
    protected boolean exists;

    public static ImprovedOfflinePlayer improvedOfflinePlayer;

    static {
        String serverImplVersion = Bukkit.getVersion();
        String minecraftVersion = serverImplVersion.substring(0, serverImplVersion.indexOf('-'));

        try {
            if (minecraftVersion.equals("1.21") || minecraftVersion.equals("1.21.1")) {
                improvedOfflinePlayer = (ImprovedOfflinePlayer) Class.forName("me.deadlight.ezchestshop.utils.ImprovedOfflinePlayer_v1_21_R1").newInstance();
            } else if (minecraftVersion.equals("1.20.6")) {
                improvedOfflinePlayer = (ImprovedOfflinePlayer) Class.forName("me.deadlight.ezchestshop.utils.ImprovedOfflinePlayer_v1_20_R4").newInstance();
            } else if (minecraftVersion.equals("1.20.4")) {
                improvedOfflinePlayer = (ImprovedOfflinePlayer) Class.forName("me.deadlight.ezchestshop.utils.ImprovedOfflinePlayer_v1_20_R3").newInstance();
            } else {
                String packageName = Utils.class.getPackage().getName();
                String internalsName = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
                improvedOfflinePlayer = (ImprovedOfflinePlayer) Class.forName(packageName + ".ImprovedOfflinePlayer_" + internalsName).newInstance();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "EzChestShop could not find a valid implementation for this server version.");
        }
    }

    public ImprovedOfflinePlayer() {
    }

    public ImprovedOfflinePlayer(OfflinePlayer player) {
        this.player = player;
        this.isOnline = player.isOnline();
        if (!isOnline) {
            exists = loadPlayerData();
        }
    }

    public boolean isOnline() {
        return isOnline;
    }

    public boolean hasPlayedBefore() {
        return player.hasPlayedBefore();
    }

    public abstract ImprovedOfflinePlayer fromOfflinePlayer(OfflinePlayer player);

    public abstract int getLevel();

    public abstract void setLevel(int level);

    public abstract float getExp();

    public abstract void setExp(float exp);

    public abstract int getExpToLevel();

    public abstract boolean loadPlayerData();

    public abstract boolean savePlayerData();

}
