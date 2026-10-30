package me.l3gend.gotosystem.bungee;

import it.mycraft.powerlib.bungee.config.BungeeConfigManager;
import it.mycraft.powerlib.common.configuration.ConfigManager;
import it.mycraft.powerlib.common.configuration.Configuration;
import lombok.Getter;
import me.l3gend.gotosystem.bungee.commands.GoToCommand;
import me.l3gend.gotosystem.bungee.commands.ReloadCommand;
import me.l3gend.gotosystem.bungee.listener.UpdateFound;
import me.l3gend.gotosystem.bungee.utils.UpdateChecker;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

@Getter
public final class GoToBungee extends Plugin {

    private static GoToBungee instance;

    private ConfigManager configManager;
    private Configuration config;

    private UpdateChecker updateChecker;
    private UpdateFound updateFoundListener;

    @Override
    public void onEnable() {
        instance = this;

        configManager = new BungeeConfigManager(this);
        config = configManager.create("config.yml");

        logBanner("ENABLED");

        loadCommands();

        updateChecker = new UpdateChecker(this);
        updateFoundListener = new UpdateFound(updateChecker);
        ProxyServer.getInstance().getPluginManager().registerListener(this, updateFoundListener);

        updateChecker.checkForUpdatesAsync(null);
    }

    @Override
    public void onDisable() {
        if (updateChecker != null) {
            updateChecker.shutdown();
        }

        instance = null;

        logBanner("DISABLED");
    }

    private void logBanner(String phase) {
        getProxy().getLogger().info("[GoToSystem] " + phase + "\n" +
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
        getProxy().getPluginManager().registerCommand(this, new ReloadCommand());
        getProxy().getPluginManager().registerCommand(this, new GoToCommand());
    }


    public static GoToBungee get() {
        return instance;
    }

}
