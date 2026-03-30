package com.yournetwork.hypixelconnect;

import com.yournetwork.hypixelconnect.api.HypixelConnectAPI;
import com.yournetwork.hypixelconnect.listeners.PlayerConnectionListener;
import com.yournetwork.hypixelconnect.managers.ProfileManager;
import com.yournetwork.hypixelconnect.storage.DataStore;
import com.yournetwork.hypixelconnect.storage.YamlDataStore;
import org.bukkit.plugin.java.JavaPlugin;

public class HypixelConnect extends JavaPlugin {

    private DataStore dataStore;
    private ProfileManager profileManager;
    private HypixelConnectAPI api;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Initialize Storage (Expandable for Mongo in future)
        String storageType = getConfig().getString("storage.type", "YAML");
        if (storageType.equalsIgnoreCase("YAML")) {
            this.dataStore = new YamlDataStore(this);
        } else {
            // Future MongoDB implementation hook
            this.dataStore = new YamlDataStore(this);
            getLogger().warning("Database type not supported yet. Defaulting to YAML.");
        }
        this.dataStore.init();

        // Initialize Managers
        this.profileManager = new ProfileManager(dataStore);
        
        // Initialize API
        this.api = new HypixelConnectAPI(this);

        // Register Listeners
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(this), this);

        getLogger().info("HypixelConnect Engine has been Enabled successfully!");
    }

    @Override
    public void onDisable() {
        if (profileManager != null) {
            getLogger().info("Saving all player profiles...");
            profileManager.saveAllSync();
        }
        if (dataStore != null) {
            dataStore.shutdown();
        }
        getLogger().info("HypixelConnect Engine has been Disabled.");
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }
}
