package me.l3gend.gotosystem.bungee.commands;

import me.l3gend.gotosystem.bungee.GoToBungee;
import me.l3gend.gotosystem.bungee.utils.ColorAPI;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import revxrsal.commands.annotation.AutoComplete;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.annotation.Usage;
import revxrsal.commands.bungee.annotation.CommandPermission;

public class GoToCommand {

    @Command({"goto", "connect"})
    @CommandPermission("goto.command")
    @AutoComplete("@serverName *")
    @Usage("&cUsage: /goto <server>")
    public void onGoToCommand(ProxiedPlayer player, @Named("serverName") String server) {
        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(server);

        if (serverInfo == null) {
            player.sendMessage(new TextComponent(ColorAPI.translate(GoToBungee.get().getConfig().getString("no-found"))));
            return;
        }

        player.connect(serverInfo);
    }

}
