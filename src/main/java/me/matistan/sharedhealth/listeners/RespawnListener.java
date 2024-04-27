package me.matistan.sharedhealth.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import static me.matistan.sharedhealth.commands.SharedHealthCommand.health;
import static me.matistan.sharedhealth.commands.SharedHealthCommand.players;

public class RespawnListener implements Listener {

    @EventHandler
    public void onRespawnEvent(PlayerRespawnEvent e) {
        if (!players.contains(e.getPlayer().getName())) return;
        e.getPlayer().setHealth(health);
    }
}
