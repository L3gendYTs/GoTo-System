package me.l3gend.gotosystem.bungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static me.l3gend.gotosystem.bungee.GoToBungee.reloadConfiguration;
import static me.l3gend.gotosystem.bungee.managers.ConfigurationManager.config;
import static me.l3gend.gotosystem.bungee.utils.ColorUtils.color;

public class ReloadCMD extends Command {

    public ReloadCMD() {
        super("gotoreload", "", "gotorel", "reloadgoto", "relgoto");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (p.hasPermission("goto.reload")) {
                reloadConfiguration();
                p.sendMessage(new TextComponent(color(config.getString("reload"))));
            } else {
                p.sendMessage(new TextComponent(color(config.getString("no-perms"))));
            }
        } else {

            reloadConfiguration();
            sender.sendMessage(new TextComponent(color(config.getString("reload"))));

        }

    }
}
