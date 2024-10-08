package me.l3gend.gotosystem.bungee.commands;

import me.l3gend.gotosystem.bungee.GoToBungee;
import me.l3gend.gotosystem.bungee.utils.ColorAPI;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Usage;
import revxrsal.commands.bungee.BungeeCommandActor;
import revxrsal.commands.bungee.annotation.CommandPermission;

public class ReloadCommand {

    @Command("gotoreload")
    @CommandPermission("goto.reload")
    @Usage("&cUsage: /gotoreload")
    public void onReload(BungeeCommandActor actor) {
        GoToBungee.get().getConfigManager().reload("config.yml");
        actor.reply(ColorAPI.translate(GoToBungee.get().getConfig().getString("reload")));
    }
}
