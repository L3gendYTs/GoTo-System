package me.l3gend.gotosystem.velocity.commands.handler.exception;

import it.mycraft.powerlib.common.utils.ColorAPI;
import me.l3gend.gotosystem.velocity.GoToVelocity;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.CommandExceptionAdapter;
import revxrsal.commands.exception.MissingArgumentException;
import revxrsal.commands.exception.NoPermissionException;

public class VelocityCommandException extends CommandExceptionAdapter {

    @Override
    public void noPermission(@NotNull CommandActor actor, @NotNull NoPermissionException exception) {
        actor.reply(ColorAPI.color(GoToVelocity.get().getConfig().getString("no-perms")));
    }

    @Override
    public void missingArgument(@NotNull CommandActor actor, @NotNull MissingArgumentException exception) {
        actor.reply(ColorAPI.color(exception.getCommand().getUsage()));
    }

}
