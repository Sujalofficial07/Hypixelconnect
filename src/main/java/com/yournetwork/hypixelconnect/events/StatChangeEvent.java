package com.yournetwork.hypixelconnect.events;

import com.yournetwork.hypixelconnect.models.StatType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StatChangeEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final StatType stat;
    private final double oldValue;
    private double newValue;

    public StatChangeEvent(Player player, StatType stat, double oldValue, double newValue) {
        this.player = player;
        this.stat = stat;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Player getPlayer() { return player; }
    public StatType getStat() { return stat; }
    public double getOldValue() { return oldValue; }
    public double getNewValue() { return newValue; }
    public void setNewValue(double newValue) { this.newValue = newValue; }

    @Override
    public HandlerList getHandlers() { return HANDLERS; }
    public static HandlerList getHandlerList() { return HANDLERS; }
}
