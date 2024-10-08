package me.l3gend.gotosystem.bungee.commands.handler.exception;

import me.l3gend.gotosystem.bungee.GoToBungee;
import me.l3gend.gotosystem.bungee.utils.ColorAPI;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.CommandExceptionAdapter;
import revxrsal.commands.exception.MissingArgumentException;
import revxrsal.commands.exception.NoPermissionException;

public class BungeeCommandException extends CommandExceptionAdapter {

    @Override
    public void noPermission(@NotNull CommandActor actor, @NotNull NoPermissionException exception) {
        actor.reply(ColorAPI.translate(GoToBungee.get().getConfig().getString("no-perms")));
    }

    @Override
    public void missingArgument(@NotNull CommandActor actor, @NotNull MissingArgumentException exception) {
        actor.reply(ColorAPI.translate(exception.getCommand().getUsage()));
    }
}
