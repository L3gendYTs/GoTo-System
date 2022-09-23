package me.l3gend.gotosystem.bungee;

import me.l3gend.gotosystem.bungee.commands.GoToCMD;
import me.l3gend.gotosystem.bungee.commands.ReloadCMD;
import me.l3gend.gotosystem.bungee.events.UpdateFound;
import me.l3gend.gotosystem.bungee.utils.UpdateChecker;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import static me.l3gend.gotosystem.bungee.managers.ConfigurationManager.createMainConfig;
import static me.l3gend.gotosystem.bungee.managers.ConfigurationManager.registerMainConfig;

public final class GoToBungee extends Plugin {

    private static GoToBungee Instance;

    public static void setInstance(GoToBungee instance) {
        Instance = instance;
    }

    public static GoToBungee getInstance() {
        return Instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        setInstance(this);

        createMainConfig(this);
        registerMainConfig(this);

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
        PluginManager pm = ProxyServer.getInstance().getPluginManager();
        pm.registerCommand(this, new GoToCMD());
        pm.registerCommand(this, new ReloadCMD());

        pm.registerListener(this, new UpdateFound());

        new UpdateChecker(this, 105194).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("There is a new update available.");
            }
        });

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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


    public static void reloadConfiguration() {
        getInstance().getProxy().getPluginManager().getPlugin("GoToSystem").onDisable();
        getInstance().getProxy().getPluginManager().getPlugin("GoToSystem").onEnable();
    }
}
