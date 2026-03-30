package com.yournetwork.hypixelconnect.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkyblockPlayer {
    private final UUID uuid;
    private final Map<UUID, PlayerProfile> profiles;
    private UUID activeProfileId;

    public SkyblockPlayer(UUID uuid) {
        this.uuid = uuid;
        this.profiles = new HashMap<>();
    }

    public UUID getUuid() { return uuid; }
    
    public void addProfile(PlayerProfile profile) {
        profiles.put(profile.getProfileId(), profile);
        if (activeProfileId == null) {
            activeProfileId = profile.getProfileId();
        }
    }

    public PlayerProfile getActiveProfile() {
        return profiles.get(activeProfileId);
    }

    public PlayerProfile getProfile(UUID profileId) {
        return profiles.get(profileId);
    }

    public Map<UUID, PlayerProfile> getAllProfiles() {
        return profiles;
    }

    public void setActiveProfileId(UUID activeProfileId) {
        if (profiles.containsKey(activeProfileId)) {
            this.activeProfileId = activeProfileId;
        }
    }
    
    public UUID getActiveProfileId() { return activeProfileId; }
}
