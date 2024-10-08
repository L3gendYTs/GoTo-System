package me.l3gend.gotosystem.bungee;

import it.mycraft.powerlib.bungee.config.BungeeConfigManager;
import it.mycraft.powerlib.common.configuration.ConfigManager;
import it.mycraft.powerlib.common.configuration.Configuration;
import lombok.Getter;
import lombok.Setter;
import me.l3gend.gotosystem.bungee.commands.handler.BungeeCommandManager;
import me.l3gend.gotosystem.bungee.listener.UpdateFound;
import me.l3gend.gotosystem.bungee.utils.UpdateChecker;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

@Getter
@Setter
public final class GoToBungee extends Plugin {

    private static GoToBungee instance;

    private ConfigManager configManager;
    private Configuration config;

    private UpdateChecker updateChecker;

    @Override
    public void onEnable() {
        instance = this;

        configManager = new BungeeConfigManager(this);
        config = configManager.create("config.yml");

        getProxy().getLogger().info("\n" +
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

        new BungeeCommandManager();

        ProxyServer.getInstance().getPluginManager().registerListener(this, new UpdateFound());

        updateChecker = new UpdateChecker(this);
        updateChecker.checkForUpdatesAsync(null);
    }

    @Override
    public void onDisable() {
        getProxy().getLogger().info("\n" +
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


    public static GoToBungee get() {
        return instance;
    }

}
