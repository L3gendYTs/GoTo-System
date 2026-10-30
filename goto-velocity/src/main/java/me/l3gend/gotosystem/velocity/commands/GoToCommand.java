package me.l3gend.gotosystem.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import it.mycraft.powerlib.common.utils.ColorAPI;
import me.l3gend.gotosystem.velocity.GoToVelocity;
import net.kyori.adventure.text.Component;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class GoToCommand implements SimpleCommand {

    private final GoToVelocity plugin;

    public GoToCommand(GoToVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        if (!(source instanceof Player player)) {
            source.sendMessage(colored("no-console"));
            return;
        }

        if (!player.hasPermission("goto.command")) {
            player.sendMessage(colored("no-perms"));
            return;
        }

        String[] args = invocation.arguments();
        if (args.length == 0) {
            player.sendMessage(colored("usage"));
            return;
        }

        String serverName = args[0];
        RegisteredServer server = plugin.getProxy().getServer(serverName).orElse(null);

        if (server == null) {
            player.sendMessage(colored("no-found"));
            return;
        }

        if (!canAccessServer(player, server.getServerInfo().getName())) {
            player.sendMessage(colored("no-perms"));
            return;
        }

        player.createConnectionRequest(server).fireAndForget();
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        CommandSource source = invocation.source();
        if (!(source instanceof Player player) || !player.hasPermission("goto.command")) {
            return Collections.emptyList();
        }

        String[] args = invocation.arguments();
        if (args.length > 1) {
            return Collections.emptyList();
        }

        String prefix = args.length == 0 ? "" : args[0].toLowerCase(Locale.ROOT);

        return plugin.getProxy().getAllServers().stream()
                .map(RegisteredServer::getServerInfo)
                .map(ServerInfo::getName)
                .filter(serverName -> serverName.toLowerCase(Locale.ROOT).startsWith(prefix))
                .filter(serverName -> canAccessServer(player, serverName))
                .toList();
    }

    private boolean canAccessServer(Player player, String serverName) {
        if (player.hasPermission("goto.all")) {
            return true;
        }

        if (!plugin.getConfig().getBoolean("per-server-permission")) {
            return true;
        }

        return player.hasPermission("goto.server." + serverName);
    }

    private Component colored(String key) {
        return ColorAPI.color(Component.text(plugin.getConfig().getString(key)));
    }
}
