package me.matistan.sharedhealth.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static me.matistan.sharedhealth.commands.SharedHealthCommand.inGame;
import static me.matistan.sharedhealth.commands.SharedHealthCommand.players;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeathEvent(PlayerDeathEvent e) {
        if (!inGame || !players.contains(e.getEntity().getName())) return;
        for (String s : players) {
            Player player = Bukkit.getPlayerExact(s);
            if (player == null || player == e.getEntity() || player.isDead()) continue;
            player.getInventory().clear();
            player.setHealth(0);
        }
    }
}
