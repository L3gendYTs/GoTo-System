package me.l3gend.gotosystem;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import javax.xml.soap.SAAJResult;
import java.util.ArrayList;
import java.util.List;

import static me.l3gend.gotosystem.GoToSystem.getInstance;

import static me.l3gend.gotosystem.ConfigurationManager.config;
public class GoToCMD extends Command implements TabExecutor {

    public GoToCMD() {
        super("goto");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent("You can't run this command from the console!"));
            return;
        }

        ProxiedPlayer p = (ProxiedPlayer) sender;

        if (args.length == 0) {
            if (p.hasPermission("goto.command")) {
                p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', config.getString("usage"))));
            } else {
                p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', config.getString("no-perms"))));
            }
        } else if (args.length == 1) {

            if (p.hasPermission("goto.command")) {
                ServerInfo server = ProxyServer.getInstance().getServerInfo(args[0]);
                p.connect(server);
            } else {
                p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', config.getString("no-perms"))));
            }

        }

    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent("You can't run this command from the console!"));
            return new ArrayList<>();
        }


        if (sender.hasPermission("goto.command.tabcomplete")) {

            List<String> servers = new ArrayList<>();

            for (String s : getInstance().getProxy().getServers().keySet()) {

                servers.add(s);

            }

            return servers;
        }

        return new ArrayList<>();
    }
}
