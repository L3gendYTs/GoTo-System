package me.l3gend.gotosystem.velocity.commands.handler;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import it.mycraft.powerlib.common.configuration.Configuration;
import lombok.Getter;
import me.l3gend.gotosystem.velocity.GoToVelocity;
import me.l3gend.gotosystem.velocity.commands.GoToCommand;
import me.l3gend.gotosystem.velocity.commands.ReloadCommand;
import me.l3gend.gotosystem.velocity.commands.handler.exception.VelocityCommandException;
import revxrsal.commands.velocity.VelocityCommandHandler;

@Getter
public class VelocityCommandManager {

    public VelocityCommandManager() {
        VelocityCommandHandler handler = VelocityCommandHandler.create(GoToVelocity.get(), GoToVelocity.get().getServer());
        handler.registerDependency(GoToVelocity.class, GoToVelocity.get());
        handler.setExceptionHandler(new VelocityCommandException());

        handler.getAutoCompleter().registerSuggestion("serverName", ((args, sender, command) -> GoToVelocity.get().getProxy().getAllServers().stream().map(RegisteredServer::getServerInfo).map(ServerInfo::getName).toList()));

        handler.register(new ReloadCommand());
        handler.register(new GoToCommand());
    }
}
