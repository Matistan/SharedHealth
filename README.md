# Minecraft Shared Health

---

View on [Spigot](https://www.spigotmc.org/resources/shared-inventory.109491/) •
Download [here](https://github.com/Matistan/SharedHealth/releases)

---

> **Having issues?** Feel free to report them on the [Issues tab](https://github.com/Matistan/SharedHealth/issues). I'll be glad to hear your opinion about the plugin as well as extra features you would like me to add!

## Welcome to readme!

Hi! I just want to thank you for your interest in this plugin. I put a lot of effort into this project and I would really love someone to use it!

### Minecraft version

This plugin runs on a Minecraft version 1.16+.

## What is a Shared Health?

With this plugin, everyone shares the health bar. If you die, everyone dies!

## How to use it

- drag the .jar file from the [Release tab](https://github.com/Matistan/SharedHealth/releases) to your plugins folder on your server.
- type `/sharedhealth start` to start the game of shared health, and stop it using `/sharedhealth stop`
- if you don't want to play with every player on the server, change the rule `playWithEveryone` to false, and choose the players using `/sharedhealth add` command

## Commands

- `/sharedhealth add <player> <player> ... ` - adds players
- `/sharedhealth add @a` - adds all players
- `/sharedhealth remove <player> <player> ... ` - removes players
- `/sharedhealth remove @a` - removes all players
- `/sharedhealth start` - starts the game
- `/sharedhealth reset` - resets the game
- `/sharedhealth list` - shows a list of players in the game of shared health
- `/sharedhealth help` - shows a list of shared health commands

## Configuration Options

Edit the `plugins/SharedHealth/config.yml` file to change the following options:

| Key                 | Description                                                                                                 | Type    | recommended                                                   |
|---------------------|-------------------------------------------------------------------------------------------------------------|---------|---------------------------------------------------------------|
| timeSetDayOnStart   | Set to true to set the time to day automatically when the game starts.                                      | boolean | true                                                          |
| weatherClearOnStart | Set to true to set the weather to clear automatically when the game starts.                                 | boolean | true                                                          |
| playWithEveryone    | Set to true to not have to use '/sharedhealth add' every time, and instead play with every player on server | boolean | true                                                          |
| takeAwayOps         | Set to true to take away OPs for the duration of the game.                                                  | boolean | true                                                          |
| usePermissions      | Set to true to require users to have permission to use certain commands.                                    | boolean | false; true if you don't trust the people you're playing with |

## Permissions

If `usePermissions` is set to `true` in the `config.yml` file, players without ops will need the following permissions to use the commands:

| Permission                | Description                                                  |
|---------------------------|--------------------------------------------------------------|
| sharedhealth.sharedhealth | Allows the player to use all `/sharedhealth` commands.       |
| sharedhealth.add          | Allows the player to use the `/sharedhealth add` command.    |
| sharedhealth.remove       | Allows the player to use the `/sharedhealth remove` command. |
| sharedhealth.start        | Allows the player to use the `/sharedhealth start` command.  |
| sharedhealth.reset        | Allows the player to use the `/sharedhealth reset` command.  |
| sharedhealth.list         | Allows the player to use the `/sharedhealth list` command.   |
| sharedhealth.help         | Allows the player to use the `/sharedhealth help` command.   |

### Bugs & Issues

> **Having issues?** Feel free to report them on the [Issues tab](https://github.com/Matistan/SharedHealth/issues). I'll be glad to hear your opinion about the plugin as well as extra features you would like me to add!

Made by [Matistan](https://github.com/Matistan)