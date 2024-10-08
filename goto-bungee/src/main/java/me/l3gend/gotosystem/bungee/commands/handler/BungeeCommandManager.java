package me.l3gend.gotosystem.bungee.commands.handler;

import lombok.Getter;
import me.l3gend.gotosystem.bungee.GoToBungee;
import me.l3gend.gotosystem.bungee.commands.GoToCommand;
import me.l3gend.gotosystem.bungee.commands.ReloadCommand;
import me.l3gend.gotosystem.bungee.commands.handler.exception.BungeeCommandException;
import net.md_5.bungee.api.config.ServerInfo;
import revxrsal.commands.bungee.BungeeCommandHandler;

@Getter
public class BungeeCommandManager {

    public BungeeCommandManager() {
        BungeeCommandHandler handler = BungeeCommandHandler.create(GoToBungee.get());
        handler.registerDependency(GoToBungee.class, GoToBungee.get());
        handler.setExceptionHandler(new BungeeCommandException());

        handler.getAutoCompleter().registerSuggestion("serverName", ((args, sender, command) -> GoToBungee.get().getProxy().getServers().values().stream().map(ServerInfo::getName).toList()));

        handler.register(new ReloadCommand());
        handler.register(new GoToCommand());
    }
}
