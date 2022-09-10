package me.l3gend.gotosystem;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import static me.l3gend.gotosystem.GoToSystem.getInstance;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;

public class ConfigurationManager {

    static Configuration config;

    public static void createMainConfig(Plugin plugin) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                InputStream in = plugin.getResourceAsStream("config.yml");
                Files.copy(in, file.toPath(), new CopyOption[0]);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static void registerMainConfig(Plugin plugin) {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
