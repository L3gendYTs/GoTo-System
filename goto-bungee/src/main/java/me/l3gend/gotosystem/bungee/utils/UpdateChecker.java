package me.l3gend.gotosystem.bungee.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.l3gend.gotosystem.bungee.GoToBungee;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class UpdateChecker {

    private final GoToBungee plugin;

    public UpdateChecker(GoToBungee plugin) {
        this.plugin = plugin;
    }

    public void checkForUpdatesAsync(ProxiedPlayer player) {
        CompletableFuture.runAsync(() -> {
            try {
                if (!plugin.getConfig().getBoolean("update-check.enabled")) {
                    return;
                }

                plugin.getLogger().info("Checking for updates...");

                HttpClient client = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://api.github.com/repos/L3gendYTs/Goto-System/releases/latest"))
                        .header("Accept", "application/json")
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    return;
                }

                JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
                String latest = jsonObject.get("tag_name").getAsString();

                String current = plugin.getDescription().getVersion();

                plugin.getLogger().info("Current plugin version: " + current);
                if (!latest.equals(current)) {
                    plugin.getLogger().info("New update available: " + latest);
                    notifyPlayer(player, latest, current);
                } else {
                    plugin.getLogger().info("You are already on the latest version: " + current);
                }

                client.close();
            } catch (Exception e) {
                plugin.getLogger().severe("Unexpected error while checking for updates: " + e.getMessage());
                e.printStackTrace(System.err);
            }
        });
    }

    private void notifyPlayer(ProxiedPlayer player, String latestVersion, String oldVersion) {
        if (player == null) return;
        player.sendMessage(new TextComponent(ColorAPI.translate(
                plugin.getConfig().getString("update-check.message")
                .replace("{OLD_VERSION}", oldVersion)
                .replace("{NEW_VERSION}", latestVersion))));
    }
}
