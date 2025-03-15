package me.matistan.sharedhealth;

import me.matistan.sharedhealth.commands.SharedHealthCommand;
import me.matistan.sharedhealth.commands.SharedHealthCompleter;
import me.matistan.sharedhealth.listeners.DeathListener;
import me.matistan.sharedhealth.listeners.RespawnListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import static me.matistan.sharedhealth.commands.SharedHealthCommand.reset;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginCommand("sharedhealth").setExecutor(new SharedHealthCommand(this));
        getCommand("sharedhealth").setTabCompleter(new SharedHealthCompleter(this));
        Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new RespawnListener(), this);
        new Metrics(this, 21887);
        System.out.println("*********************************************************\n" +
                "Thank you for using this plugin! <3\n" +
                "Author: Matistan\n" +
                "If you enjoy this plugin, please rate it on spigotmc.org:\n" +
                "https://www.spigotmc.org/resources/shared-health.116438/\n" +
                "*********************************************************");
    }

    @Override
    public void onDisable() {
        reset();
    }
}
