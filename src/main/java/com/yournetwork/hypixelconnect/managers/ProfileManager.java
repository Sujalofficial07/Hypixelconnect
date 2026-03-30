package com.yournetwork.hypixelconnect.managers;

import com.yournetwork.hypixelconnect.models.SkyblockPlayer;
import com.yournetwork.hypixelconnect.storage.DataStore;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class ProfileManager {
    private final DataStore dataStore;
    private final ConcurrentHashMap<UUID, SkyblockPlayer> playerCache;

    public ProfileManager(DataStore dataStore) {
        this.dataStore = dataStore;
        this.playerCache = new ConcurrentHashMap<>();
    }

    public CompletableFuture<SkyblockPlayer> loadAndCachePlayer(UUID uuid) {
        return dataStore.loadPlayer(uuid).thenApply(player -> {
            playerCache.put(uuid, player);
            return player;
        });
    }

    public SkyblockPlayer getCachedPlayer(UUID uuid) {
        return playerCache.get(uuid);
    }

    public CompletableFuture<Void> saveAndUnloadPlayer(UUID uuid) {
        SkyblockPlayer player = playerCache.remove(uuid);
        if (player != null) {
            return dataStore.savePlayer(player);
        }
        return CompletableFuture.completedFuture(null);
    }
    
    public void saveAllSync() {
        for (SkyblockPlayer player : playerCache.values()) {
            dataStore.savePlayer(player).join(); // Blocking call for server shutdown
        }
    }
}
