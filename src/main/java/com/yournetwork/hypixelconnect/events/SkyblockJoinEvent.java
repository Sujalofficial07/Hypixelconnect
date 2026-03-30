package com.yournetwork.hypixelconnect.events;

import com.yournetwork.hypixelconnect.models.SkyblockPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkyblockJoinEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player bukkitPlayer;
    private final SkyblockPlayer skyblockPlayer;

    public SkyblockJoinEvent(Player bukkitPlayer, SkyblockPlayer skyblockPlayer) {
        this.bukkitPlayer = bukkitPlayer;
        this.skyblockPlayer = skyblockPlayer;
    }

    public Player getBukkitPlayer() { return bukkitPlayer; }
    public SkyblockPlayer getSkyblockPlayer() { return skyblockPlayer; }

    @Override
    public HandlerList getHandlers() { return HANDLERS; }
    public static HandlerList getHandlerList() { return HANDLERS; }
}
