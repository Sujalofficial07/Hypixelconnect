package com.yournetwork.hypixelconnect.storage;

import com.yournetwork.hypixelconnect.HypixelConnect;
import com.yournetwork.hypixelconnect.models.PlayerProfile;
import com.yournetwork.hypixelconnect.models.SkyblockPlayer;
import com.yournetwork.hypixelconnect.models.StatType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class YamlDataStore implements DataStore {
    private final HypixelConnect plugin;
    private final File dataFolder;

    public YamlDataStore(HypixelConnect plugin) {
        this.plugin = plugin;
        this.dataFolder = new File(plugin.getDataFolder(), "userdata");
    }

    @Override
    public void init() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }

    @Override
    public CompletableFuture<SkyblockPlayer> loadPlayer(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            File file = new File(dataFolder, uuid.toString() + ".yml");
            SkyblockPlayer sbPlayer = new SkyblockPlayer(uuid);

            if (!file.exists()) {
                // Create default profile for new player
                PlayerProfile defaultProfile = new PlayerProfile(UUID.randomUUID(), "Apple");
                sbPlayer.addProfile(defaultProfile);
                return sbPlayer;
            }

            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            String activeProfileStr = config.getString("active_profile");
            
            if (config.getConfigurationSection("profiles") != null) {
                for (String profileIdStr : config.getConfigurationSection("profiles").getKeys(false)) {
                    UUID profileId = UUID.fromString(profileIdStr);
                    String path = "profiles." + profileIdStr + ".";
                    
                    PlayerProfile profile = new PlayerProfile(profileId, config.getString(path + "name"));
                    profile.setCoins(config.getDouble(path + "coins", 0.0));
                    
                    // Load stats
                    if (config.getConfigurationSection(path + "stats") != null) {
                        for (String statStr : config.getConfigurationSection(path + "stats").getKeys(false)) {
                            try {
                                StatType type = StatType.valueOf(statStr);
                                profile.setStat(type, config.getDouble(path + "stats." + statStr));
                            } catch (IllegalArgumentException ignored) {}
                        }
                    }
                    sbPlayer.addProfile(profile);
                }
            }

            if (activeProfileStr != null) {
                sbPlayer.setActiveProfileId(UUID.fromString(activeProfileStr));
            }

            return sbPlayer;
        });
    }

    @Override
    public CompletableFuture<Void> savePlayer(SkyblockPlayer player) {
        return CompletableFuture.runAsync(() -> {
            File file = new File(dataFolder, player.getUuid().toString() + ".yml");
            FileConfiguration config = new YamlConfiguration();

            config.set("active_profile", player.getActiveProfileId().toString());

            for (PlayerProfile profile : player.getAllProfiles().values()) {
                String path = "profiles." + profile.getProfileId().toString() + ".";
                config.set(path + "name", profile.getProfileName());
                config.set(path + "coins", profile.getCoins());
                
                profile.getStatsMap().forEach((stat, val) -> config.set(path + "stats." + stat.name(), val));
                profile.getSkillsMap().forEach((skill, val) -> config.set(path + "skills." + skill.name(), val));
                profile.getCollectionsMap().forEach((col, val) -> config.set(path + "collections." + col, val));
            }

            try {
                config.save(file);
            } catch (IOException e) {
                plugin.getLogger().severe("Could not save data for " + player.getUuid());
            }
        });
    }

    @Override
    public void shutdown() {
        // Handle any necessary cleanup
    }
}
