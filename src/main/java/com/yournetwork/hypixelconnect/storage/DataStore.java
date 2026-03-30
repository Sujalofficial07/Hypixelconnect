package com.yournetwork.hypixelconnect.storage;

import com.yournetwork.hypixelconnect.models.SkyblockPlayer;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface DataStore {
    void init();
    CompletableFuture<SkyblockPlayer> loadPlayer(UUID uuid);
    CompletableFuture<Void> savePlayer(SkyblockPlayer player);
    void shutdown();
}
