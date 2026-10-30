package me.l3gend.gotosystem.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
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
import me.l3gend.gotosystem.velocity.commands.GoToCommand;
import me.l3gend.gotosystem.velocity.commands.ReloadCommand;
import me.l3gend.gotosystem.velocity.listener.UpdateFound;
import me.l3gend.gotosystem.velocity.utils.UpdateChecker;

import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(
        id = "goto-velocity",
        name = "goto-velocity",
        version = "2.0",
        authors = {"L3gend"}
)
@Getter
public class GoToVelocity {

    /*
    * Goto System Created by L3gend
     */

    private static GoToVelocity instance;

    private final ProxyServer proxy;
    private final PluginDescription pluginDescription;
    private final Path dataDirectory;

    private final Logger logger;

    private ConfigManager configManager;
    private Configuration config;

    private UpdateChecker updateChecker;
    private UpdateFound updateFoundListener;

    @Inject
    public GoToVelocity(ProxyServer proxy, PluginDescription description, @DataDirectory Path folder, Logger logger) {
        this.proxy = proxy;
        this.pluginDescription = description;
        this.dataDirectory = folder;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        instance = this;

        configManager = new VelocityConfigManager(pluginDescription);
        config = configManager.create("config.yml");

        logBanner("ENABLED");

        loadCommands();

        updateChecker = new UpdateChecker(this);
        updateFoundListener = new UpdateFound(updateChecker);
        proxy.getEventManager().register(this, updateFoundListener);

        updateChecker.checkForUpdatesAsync(null);
    }

    @Subscribe
    public void onProxyShutdownEvent(ProxyShutdownEvent event) {
        if (updateChecker != null) {
            updateChecker.shutdown();
        }

        instance = null;

        logBanner("DISABLED");
    }

    private void logBanner(String phase) {
        logger.info("[GoToSystem] " + phase + "\n" +
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

    private void loadCommands() {
        CommandManager commandManager = getProxy().getCommandManager();

        GoToCommand goToCommand = new GoToCommand(this);
        CommandMeta goToMeta = commandManager.metaBuilder("goto")
                .aliases("connect")
                .plugin(this)
                .build();
        commandManager.register(goToMeta, goToCommand);

        CommandMeta reloadMeta = commandManager.metaBuilder("gotoreload")
                .plugin(this)
                .build();
        commandManager.register(reloadMeta, new ReloadCommand(this));
    }

    public static GoToVelocity get() {
        return instance;
    }
}
