package me.l3gend.gotosystem.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginDescription;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import it.mycraft.powerlib.common.configuration.ConfigManager;
import it.mycraft.powerlib.common.configuration.Configuration;
import it.mycraft.powerlib.velocity.config.VelocityConfigManager;
import lombok.Getter;
import lombok.Setter;
import me.l3gend.gotosystem.velocity.commands.handler.VelocityCommandManager;
import me.l3gend.gotosystem.velocity.listener.UpdateFound;
import me.l3gend.gotosystem.velocity.utils.UpdateChecker;

import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(
        id = "goto-velocity",
        name = "goto-velocity",
        version = "1.2"
)
@Getter
@Setter
public class GoToVelocity {

    private static GoToVelocity instance;

    private ProxyServer proxy, server;
    private PluginDescription pluginDescription;
    private Path path;

    private Logger logger;

    private ConfigManager configManager;
    private Configuration config;


    @Inject
    public GoToVelocity(ProxyServer proxy, ProxyServer server, PluginDescription description, @DataDirectory Path folder, Logger logger) {
        this.proxy = proxy;
        this.server = server;
        this.pluginDescription = description;
        this.path = folder;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        instance = this;

        configManager = new VelocityConfigManager(pluginDescription);
        config = configManager.create("config.yml");

        logger.info("\n" +
                "           ______      __            \n" +
                "          / ____/___  / /_____       \n" +
                "         / / __/ __ \\/ __/ __ \\      \n" +
                "        / /_/ / /_/ / /_/ /_/ /      \n" +
                "   _____\\____/\\____/\\__/\\____/       \n" +
                "  / ___/__  _______/ /____  ____ ___ \n" +
                "  \\__ \\/ / / / ___/ __/ _ \\/ __ `__ \\\n" +
                " ___/ / /_/ (__  ) /_/  __/ / / / / /\n" +
                "/____/\\__, /____/\\__/\\___/_/ /_/ /_/ \n" +
                "     /____/                          \n");

        new VelocityCommandManager();

        proxy.getEventManager().register(this, new UpdateFound());

        new UpdateChecker(this, 105194).getVersion(version -> {
            if (pluginDescription.getVersion().equals(version)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("There is a new update available.");
            }
        });
    }

    @Subscribe
    public void onProxyShutdownEvent(ProxyShutdownEvent event) {
        logger.info("\n" +
                "           ______      __            \n" +
                "          / ____/___  / /_____       \n" +
                "         / / __/ __ \\/ __/ __ \\      \n" +
                "        / /_/ / /_/ / /_/ /_/ /      \n" +
                "   _____\\____/\\____/\\__/\\____/       \n" +
                "  / ___/__  _______/ /____  ____ ___ \n" +
                "  \\__ \\/ / / / ___/ __/ _ \\/ __ `__ \\\n" +
                " ___/ / /_/ (__  ) /_/  __/ / / / / /\n" +
                "/____/\\__, /____/\\__/\\___/_/ /_/ /_/ \n" +
                "     /____/                          \n");
    }

    public static GoToVelocity get() {
        return instance;
    }
}
