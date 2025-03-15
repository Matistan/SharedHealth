package me.matistan.sharedhealth.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import static me.matistan.sharedhealth.commands.SharedHealthCommand.*;

public class RespawnListener implements Listener {

    @EventHandler
    public void onRespawnEvent(PlayerRespawnEvent e) {
        if (!inGame || !players.contains(e.getPlayer().getName())) return;
        e.getPlayer().setHealth(health);
    }
}
