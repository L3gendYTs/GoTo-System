package me.l3gend.gotosystem.velocity.commands;

import it.mycraft.powerlib.common.utils.ColorAPI;
import me.l3gend.gotosystem.velocity.GoToVelocity;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Usage;
import revxrsal.commands.velocity.VelocityCommandActor;
import revxrsal.commands.velocity.annotation.CommandPermission;

public class ReloadCommand {

    @Command("gotoreload")
    @CommandPermission("goto.reload")
    @Usage("&cUsage: /gotoreload")
    public void onReload(VelocityCommandActor actor) {
        GoToVelocity.get().getConfigManager().reload("config.yml");
        actor.reply(ColorAPI.color(GoToVelocity.get().getConfig().getString("reload")));
    }
}
