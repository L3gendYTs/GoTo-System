package me.l3gend.gotosystem.bungee.commands;

import me.l3gend.gotosystem.bungee.GoToBungee;
import me.l3gend.gotosystem.bungee.utils.ColorAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ReloadCommand extends Command {

    public ReloadCommand() {
        super("gotoreload", "goto.reload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            sender.sendMessage(ColorAPI.translateToTextComponent("&cUsage: /gotoreload"));
            return;
        }

        if (!sender.hasPermission("goto.reload")) {
            sender.sendMessage(ColorAPI.translateToTextComponent(GoToBungee.get().getConfig().getString("no-perms")));
            return;
        }

        GoToBungee.get().getConfigManager().reload("config.yml");
        sender.sendMessage(ColorAPI.translateToTextComponent(GoToBungee.get().getConfig().getString("reload")));
    }
}
