package me.l3gend.gotosystem.velocity.commands;

import com.velocitypowered.api.proxy.Player;
import it.mycraft.powerlib.common.utils.ColorAPI;
import me.l3gend.gotosystem.velocity.GoToVelocity;
import net.kyori.adventure.text.Component;
import revxrsal.commands.annotation.AutoComplete;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.annotation.Usage;
import revxrsal.commands.velocity.annotation.CommandPermission;

public class GoToCommand {

    @Command({"goto", "connect"})
    @CommandPermission("goto.command")
    @AutoComplete("@serverName *")
    @Usage("&cUsage: /goto <server>")
    public void onGoToCommand(Player player, @Named("serverName") String serverName) {
        GoToVelocity.get().getProxy().getServer(serverName).ifPresentOrElse(server -> {
            player.createConnectionRequest(server).fireAndForget();
        }, () -> {
            player.sendMessage(ColorAPI.color(Component.text(GoToVelocity.get().getConfig().getString("no-found"))));
        });
    }

}
