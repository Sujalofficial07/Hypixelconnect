package com.yournetwork.hypixelconnect.listeners;

import com.yournetwork.hypixelconnect.HypixelConnect;
import com.yournetwork.hypixelconnect.events.SkyblockJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements Listener {
    private final HypixelConnect plugin;

    public PlayerConnectionListener(HypixelConnect plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncPreLogin(AsyncPlayerPreLoginEvent event) {
        // Load data before player fully joins to prevent lag/desync
        try {
            plugin.getProfileManager().loadAndCachePlayer(event.getUniqueId()).join();
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to load profile for " + event.getName());
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Failed to load Skyblock Profile. Please try again.");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Fire custom SkyblockJoinEvent for other plugins to use
        Bukkit.getScheduler().runTask(plugin, () -> {
            SkyblockJoinEvent customEvent = new SkyblockJoinEvent(
                event.getPlayer(),
                plugin.getProfileManager().getCachedPlayer(event.getPlayer().getUniqueId())
            );
            Bukkit.getPluginManager().callEvent(customEvent);
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Save and remove from cache async
        plugin.getProfileManager().saveAndUnloadPlayer(event.getPlayer().getUniqueId());
    }
}
