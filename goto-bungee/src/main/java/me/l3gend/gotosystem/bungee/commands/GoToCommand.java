package me.l3gend.gotosystem.bungee.commands;

import me.l3gend.gotosystem.bungee.GoToBungee;
import me.l3gend.gotosystem.bungee.utils.ColorAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Collections;
import java.util.Locale;


public class GoToCommand extends Command implements TabExecutor {

    public GoToCommand() {
        super("goto", "", "connect");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer player)) {
            sender.sendMessage(ColorAPI.translateToTextComponent(GoToBungee.get().getConfig().getString("no-console")));
            return;
        }

        if (!player.hasPermission("goto.command")) {
            player.sendMessage(ColorAPI.translateToTextComponent(GoToBungee.get().getConfig().getString("no-perms")));
            return;
        }

        if (args.length == 0) {
            player.sendMessage(ColorAPI.translateToTextComponent(GoToBungee.get().getConfig().getString("usage")));
            return;
        }

        ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(args[0]);

        if (serverInfo == null) {
            player.sendMessage(ColorAPI.translateToTextComponent(GoToBungee.get().getConfig().getString("no-found")));
            return;
        }

        if (!canAccessServer(player, serverInfo.getName())) {
            player.sendMessage(ColorAPI.translateToTextComponent(GoToBungee.get().getConfig().getString("no-perms")));
            return;
        }

        player.connect(serverInfo);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer player)) {
            return Collections.emptyList();
        }

        if (!player.hasPermission("goto.command")) {
            return Collections.emptyList();
        }

        if (args.length > 1) {
            return Collections.emptyList();
        }

        String prefix = args.length == 0 ? "" : args[0].toLowerCase(Locale.ROOT);

        return ProxyServer.getInstance().getServers().values().stream()
                .map(ServerInfo::getName)
                .filter(serverName -> serverName.toLowerCase(Locale.ROOT).startsWith(prefix))
                .filter(serverName -> canAccessServer(player, serverName))
                .toList();
    }

    private boolean canAccessServer(ProxiedPlayer player, String serverName) {
        if (player.hasPermission("goto.all")) {
            return true;
        }

        if (!GoToBungee.get().getConfig().getBoolean("per-server-permission")) {
            return true;
        }

        return player.hasPermission("goto.server." + serverName);
    }
}
