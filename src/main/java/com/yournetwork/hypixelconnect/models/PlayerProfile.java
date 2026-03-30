package com.yournetwork.hypixelconnect.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerProfile {
    private final UUID profileId;
    private String profileName; // e.g., "Apple", "Mango"
    private double coins;
    
    private final Map<StatType, Double> stats;
    private final Map<SkillType, Integer> skills;
    private final Map<String, Integer> collections;

    public PlayerProfile(UUID profileId, String profileName) {
        this.profileId = profileId;
        this.profileName = profileName;
        this.coins = 0.0;
        this.stats = new HashMap<>();
        this.skills = new HashMap<>();
        this.collections = new HashMap<>();
        
        // Initialize default stats
        stats.put(StatType.HEALTH, 100.0);
        stats.put(StatType.DEFENSE, 0.0);
        stats.put(StatType.STRENGTH, 0.0);
        stats.put(StatType.CRIT_CHANCE, 30.0);
        stats.put(StatType.CRIT_DAMAGE, 50.0);
        stats.put(StatType.INTELLIGENCE, 0.0);
        stats.put(StatType.SPEED, 100.0);
    }

    public UUID getProfileId() { return profileId; }
    public String getProfileName() { return profileName; }
    public double getCoins() { return coins; }
    public void setCoins(double coins) { this.coins = coins; }
    public void addCoins(double amount) { this.coins += amount; }
    
    public double getStat(StatType type) { return stats.getOrDefault(type, 0.0); }
    public void setStat(StatType type, double value) { stats.put(type, value); }

    public int getSkillLevel(SkillType type) { return skills.getOrDefault(type, 0); }
    public void setSkillLevel(SkillType type, int level) { skills.put(type, level); }

    public int getCollectionAmount(String type) { return collections.getOrDefault(type, 0); }
    public void addCollectionAmount(String type, int amount) { 
        collections.put(type, getCollectionAmount(type) + amount); 
    }

    // Used for serialization
    public Map<StatType, Double> getStatsMap() { return stats; }
    public Map<SkillType, Integer> getSkillsMap() { return skills; }
    public Map<String, Integer> getCollectionsMap() { return collections; }
}
