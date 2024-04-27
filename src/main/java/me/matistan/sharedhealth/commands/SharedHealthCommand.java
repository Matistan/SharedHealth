package me.matistan.sharedhealth.commands;

import me.matistan.sharedhealth.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.LinkedList;
import java.util.List;

public class SharedHealthCommand implements CommandExecutor {
    public static BukkitTask game;
    public static boolean inGame = false;
    public static List<String> players = new LinkedList<>();
    public static double health;
    double absorption;
    public static List<Boolean> ops = new LinkedList<>();
    private static Main main;

    public SharedHealthCommand(Main main) {
        SharedHealthCommand.main = main;
    }

    @Override
    public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "You must type an argument. For help, type: /sharedhealth help");
            return true;
        }
        if (args[0].equals("help")) {
            if (!p.hasPermission("sharedhealth.help") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedhealth help");
                return true;
            }
            p.sendMessage(ChatColor.GREEN + "------- " + ChatColor.WHITE + " Minecraft Shared Health " + ChatColor.GREEN + "----------");
            p.sendMessage(ChatColor.BLUE + "Here is a list of shared health commands:");
            p.sendMessage(ChatColor.YELLOW + "/sharedhealth add <player> <player> ... " + ChatColor.AQUA + "- adds players to the game");
            p.sendMessage(ChatColor.YELLOW + "/sharedhealth remove <player> <player> ... " + ChatColor.AQUA + "- removes  players from the game");
            p.sendMessage(ChatColor.YELLOW + "/sharedhealth start " + ChatColor.AQUA + "- starts the shared health game");
            p.sendMessage(ChatColor.YELLOW + "/sharedhealth stop " + ChatColor.AQUA + "- stops the game");
            p.sendMessage(ChatColor.YELLOW + "/sharedhealth list " + ChatColor.AQUA + "- shows a list of players in shared health game");
            p.sendMessage(ChatColor.YELLOW + "/sharedhealth help " + ChatColor.AQUA + "- shows a list of shared health commands");
            p.sendMessage(ChatColor.GREEN + "----------------------------------");
            return true;
        }
        if (args[0].equals("list")) {
            if (!p.hasPermission("sharedhealth.list") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedhealth help");
                return true;
            }
            if (main.getConfig().getBoolean("playWithEveryone")) {
                p.sendMessage(ChatColor.AQUA + "Everyone is in the game!");
                return true;
            }
            if (players.isEmpty()) {
                p.sendMessage(ChatColor.RED + "There is no player in your game!");
                return true;
            }
            p.sendMessage(ChatColor.GREEN + "------- " + ChatColor.WHITE + " Minecraft shared health " + ChatColor.GREEN + "----------");
            for (String player : players) {
                p.sendMessage(ChatColor.AQUA + player);
            }
            p.sendMessage(ChatColor.GREEN + "----------------------------------");
            return true;
        }
        if (args[0].equals("add")) {
            if (!p.hasPermission("sharedhealth.add") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length < 2) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedhealth help");
                return true;
            }
            if (main.getConfig().getBoolean("playWithEveryone")) {
                p.sendMessage(ChatColor.RED + "You can't add players to the game because you are playing with everyone!");
                return true;
            }
            if (inGame) {
                p.sendMessage(ChatColor.RED + "The game has already started!");
                return true;
            }
            int count = 0;
            if (args[1].equals("@a")) {
                if (args.length != 2) {
                    p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedhealth help");
                    return true;
                }
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (players.contains(target.getName())) continue;
                    players.add(target.getName());
                    count++;
                }
                if (count > 0) {
                    p.sendMessage(ChatColor.AQUA + "Successfully added " + count + " player" + (count == 1 ? "" : "s") + " to the game!");
                } else {
                    p.sendMessage(ChatColor.RED + "No player was added!");
                }
                return true;
            }
            for (int i = 1; i < args.length; i++) {
                Player target = Bukkit.getPlayerExact(args[i]);
                if (target == null || players.contains(target.getName())) continue;
                players.add(target.getName());
                count++;
            }
            if (count > 0) {
                p.sendMessage(ChatColor.AQUA + "Successfully added " + count + " player" + (count == 1 ? "" : "s") + " to the game!");
            } else {
                p.sendMessage(ChatColor.RED + "Could not add " + (args.length == 2 ? "this player!" : "these players!"));
            }
            return true;
        }
        if (args[0].equals("remove")) {
            if (!p.hasPermission("sharedhealth.remove") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length < 2) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedhealth help");
                return true;
            }
            if (main.getConfig().getBoolean("playWithEveryone")) {
                p.sendMessage(ChatColor.RED + "You can't remove players from the game because you are playing with everyone!");
                return true;
            }
            int count = 0;
            if (args[1].equals("@a")) {
                if (args.length != 2) {
                    p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedhealth help");
                    return true;
                }
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (!players.contains(target.getName()) || (inGame && players.size() == 2)) continue;
                    removePlayer(target.getName());
                    count++;
                }
                if (count > 0) {
                    p.sendMessage(ChatColor.AQUA + "Successfully removed " + count + " player" + (count == 1 ? "" : "s") + " from the game!");
                } else {
                    p.sendMessage(ChatColor.RED + "No player was removed!");
                }
                return true;
            }
            for (int i = 1; i < args.length; i++) {
                Player target = Bukkit.getPlayerExact(args[i]);
                if (target == null || !players.contains(target.getName()) || (inGame && players.size() == 2)) continue;
                players.remove(target.getName());
                count++;
            }
            if (count > 0) {
                p.sendMessage(ChatColor.AQUA + "Successfully removed " + count + " player" + (count == 1 ? "" : "s") + " from the game!");
            } else {
                p.sendMessage(ChatColor.RED + "Could not remove " + (args.length == 2 ? "this player!" : "these players!"));
            }
            if (players.isEmpty()) {
                reset();
            }
            return true;
        }
        if (args[0].equals("stop")) {
            if (!p.hasPermission("sharedhealth.stop") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedhealth help");
                return true;
            }
            if (!inGame) {
                p.sendMessage(ChatColor.RED + "The game has not started yet!");
                return true;
            }
            p.sendMessage(ChatColor.AQUA + "shared health game has been stopped!");
            reset();
            return true;
        }
        if (args[0].equals("start")) {
            if (!p.hasPermission("sharedhealth.start") && main.getConfig().getBoolean("usePermissions")) {
                p.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                return true;
            }
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Wrong usage of this command. For help, type: /sharedhealth help");
                return true;
            }
            if (inGame) {
                p.sendMessage(ChatColor.YELLOW + "The game has already started!");
                return true;
            }
            if (main.getConfig().getBoolean("playWithEveryone")) {
                players.clear();
                for (Player target : Bukkit.getOnlinePlayers()) {
                    players.add(target.getName());
                }
            }
            if (players.isEmpty()) {
                p.sendMessage(ChatColor.RED + "There are no players in your game!");
                return true;
            }
            if (players.size() == 1) {
                p.sendMessage(ChatColor.RED + "There must be at least 2 players!");
                return true;
            }
            if (main.getConfig().getBoolean("timeSetDayOnStart")) {
                p.getServer().getWorlds().get(0).setTime(0);
            }
            if (main.getConfig().getBoolean("weatherClearOnStart")) {
                p.getServer().getWorlds().get(0).setStorm(false);
            }
            for (int i = 0; i < players.size(); i++) {
                String t = players.get(i);
                Player player = Bukkit.getPlayerExact(t);
                if (player == null) continue;
                if (main.getConfig().getBoolean("takeAwayOps")) {
                    ops.add(player.isOp());
                    player.setOp(false);
                }
                player.setGameMode(GameMode.SURVIVAL);
                player.setHealth(20);
                player.setAbsorptionAmount(0);
                player.setFoodLevel(20);
                player.setSaturation(5);
            }
            inGame = true;
            playersMessage(ChatColor.AQUA + "START!");
            health = 20;
            absorption = 0;
            game = new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 0; i < players.size(); i++) {
                        Player player = Bukkit.getPlayerExact(players.get(i));
                        if (player == null || player.isDead()) continue;
                        if (player.getHealth() != health || player.getAbsorptionAmount() != absorption) {
                            for (int j = 0; j < players.size(); j++) {
                                Player target = Bukkit.getPlayerExact(players.get(j));
                                if (target == null || i == j || target.isDead()) continue;
                                if (player.getHealth() > health) {
                                    target.setHealth(player.getHealth());
                                } else {
                                    target.damage(health - player.getHealth());
                                }
                                target.setAbsorptionAmount(player.getAbsorptionAmount());
                            }
                            health = player.getHealth();
                            absorption = player.getAbsorptionAmount();
                        }
                    }
                }
            }.runTaskTimer(main, 0, 1);
            return true;
        }
        p.sendMessage(ChatColor.RED + "Wrong argument. For help, type: /sharedhealth help");
        return true;
    }

    private void removePlayer(String name) {
        int index = players.indexOf(name);
        players.remove(index);
        if (main.getConfig().getBoolean("takeAwayOps")) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(name);
            target.setOp(ops.get(index));
            ops.remove(index);
        }
    }

    public static void reset() {
        if (inGame) {
            inGame = false;
            game.cancel();
            if (main.getConfig().getBoolean("takeAwayOps")) {
                for (int i = 0; i < players.size(); i++) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(players.get(i));
                    target.setOp(ops.get(i));
                }
            }
        }
        ops.clear();
        players.clear();
    }

    public static void playersMessage(String s) {
        for (String value : players) {
            Player player = Bukkit.getPlayerExact(value);
            if (player == null) continue;
            player.sendMessage(s);
        }
    }
}