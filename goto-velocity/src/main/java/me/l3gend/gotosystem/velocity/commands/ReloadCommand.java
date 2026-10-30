package me.l3gend.gotosystem.velocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import it.mycraft.powerlib.common.utils.ColorAPI;
import me.l3gend.gotosystem.velocity.GoToVelocity;
import net.kyori.adventure.text.Component;

public class ReloadCommand implements SimpleCommand {

    private final GoToVelocity plugin;

    public ReloadCommand(GoToVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        if (!source.hasPermission("goto.reload")) {
            source.sendMessage(colored("no-perms"));
            return;
        }

        if (invocation.arguments().length > 0) {
            source.sendMessage(ColorAPI.color(Component.text("&cUsage: /gotoreload")));
            return;
        }

        plugin.getConfigManager().reload("config.yml");
        source.sendMessage(colored("reload"));
    }

    private Component colored(String key) {
        return ColorAPI.color(Component.text(plugin.getConfig().getString(key)));
    }
}
