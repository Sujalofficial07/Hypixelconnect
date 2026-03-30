package com.yournetwork.hypixelconnect.api;

import com.yournetwork.hypixelconnect.HypixelConnect;
import com.yournetwork.hypixelconnect.models.PlayerProfile;
import com.yournetwork.hypixelconnect.models.SkyblockPlayer;
import com.yournetwork.hypixelconnect.models.SkillType;
import com.yournetwork.hypixelconnect.models.StatType;

import java.util.UUID;

/**
 * The central API bridge for all Skyblock plugins.
 */
public class HypixelConnectAPI {
    
    private static HypixelConnectAPI instance;
    private final HypixelConnect plugin;

    public HypixelConnectAPI(HypixelConnect plugin) {
        this.plugin = plugin;
        instance = this;
    }

    public static HypixelConnectAPI getInstance() {
        if (instance == null) {
            throw new IllegalStateException("HypixelConnectAPI is not initialized!");
        }
        return instance;
    }

    public PlayerProfile getActiveProfile(UUID playerUuid) {
        SkyblockPlayer player = plugin.getProfileManager().getCachedPlayer(playerUuid);
        return player != null ? player.getActiveProfile() : null;
    }

    public double getStat(UUID playerUuid, StatType stat) {
        PlayerProfile profile = getActiveProfile(playerUuid);
        return profile != null ? profile.getStat(stat) : 0.0;
    }
    
    public void setStat(UUID playerUuid, StatType stat, double value) {
        PlayerProfile profile = getActiveProfile(playerUuid);
        if (profile != null) profile.setStat(stat, value);
    }

    public void addCoins(UUID playerUuid, double amount) {
        PlayerProfile profile = getActiveProfile(playerUuid);
        if (profile != null) profile.addCoins(amount);
    }

    public int getSkillLevel(UUID playerUuid, SkillType skill) {
        PlayerProfile profile = getActiveProfile(playerUuid);
        return profile != null ? profile.getSkillLevel(skill) : 0;
    }

    public int getCollection(UUID playerUuid, String type) {
        PlayerProfile profile = getActiveProfile(playerUuid);
        return profile != null ? profile.getCollectionAmount(type) : 0;
    }
}
