package me.l3gend.gotosystem;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import static me.l3gend.gotosystem.ConfigurationManager.createMainConfig;
import static me.l3gend.gotosystem.ConfigurationManager.registerMainConfig;

public final class GoToSystem extends Plugin {

    private static GoToSystem Instance;

    public static void setInstance(GoToSystem instance) {
        Instance = instance;
    }

    public static GoToSystem getInstance() {
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
}
