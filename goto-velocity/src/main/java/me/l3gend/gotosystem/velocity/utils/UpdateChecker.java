package me.l3gend.gotosystem.velocity.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.velocitypowered.api.proxy.Player;
import it.mycraft.powerlib.common.utils.ColorAPI;
import me.l3gend.gotosystem.velocity.GoToVelocity;
import net.kyori.adventure.text.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateChecker {

    private static final URI RELEASE_URI = URI.create("https://api.github.com/repos/L3gendYTs/Goto-System/releases/latest");
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(5);
    private static final long CACHE_TTL_MILLIS = Duration.ofMinutes(30).toMillis();
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(REQUEST_TIMEOUT)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    private final GoToVelocity plugin;
    private final ExecutorService executor;
    private final Object updateLock = new Object();

    private volatile CompletableFuture<UpdateCheckResult> inFlightCheck;
    private volatile UpdateCheckResult cachedResult;
    private volatile long lastSuccessfulCheck;

    public UpdateChecker(GoToVelocity plugin) {
        this.plugin = plugin;
        this.executor = Executors.newSingleThreadExecutor(runnable -> {
            Thread thread = new Thread(runnable, "GoToSystem-Velocity-UpdateChecker");
            thread.setDaemon(true);
            return thread;
        });
    }

    public void shutdown() {
        synchronized (updateLock) {
            if (inFlightCheck != null) {
                inFlightCheck.cancel(true);
            }
        }

        executor.shutdownNow();
    }

    public void checkForUpdatesAsync(Player player) {
        if (!isUpdateCheckEnabled()) {
            return;
        }

        refreshLatestRelease().whenComplete((result, throwable) -> {
            if (throwable != null) {
                plugin.getLogger().severe("Unexpected error while checking for updates: " + throwable.getMessage());
                return;
            }

            if (player != null && result.updateAvailable()) {
                notifyPlayer(player, result.latestVersion(), result.currentVersion());
            }
        });
    }

    private CompletableFuture<UpdateCheckResult> refreshLatestRelease() {
        UpdateCheckResult cached = cachedResult;
        long now = System.currentTimeMillis();

        if (cached != null && now - lastSuccessfulCheck < CACHE_TTL_MILLIS) {
            return CompletableFuture.completedFuture(cached);
        }

        synchronized (updateLock) {
            cached = cachedResult;
            now = System.currentTimeMillis();

            if (cached != null && now - lastSuccessfulCheck < CACHE_TTL_MILLIS) {
                return CompletableFuture.completedFuture(cached);
            }

            if (inFlightCheck != null && !inFlightCheck.isDone()) {
                return inFlightCheck;
            }

            inFlightCheck = CompletableFuture.supplyAsync(this::fetchLatestRelease, executor)
                    .whenComplete((result, throwable) -> {
                        synchronized (updateLock) {
                            if (throwable == null && result != null) {
                                cachedResult = result;
                                lastSuccessfulCheck = System.currentTimeMillis();
                            }

                            inFlightCheck = null;
                        }
                    });

            return inFlightCheck;
        }
    }

    private UpdateCheckResult fetchLatestRelease() {
        String currentVersion = normalizeVersion(plugin.getPluginDescription().getVersion().orElse(""));

        try {
            plugin.getLogger().info("Checking for updates...");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(RELEASE_URI)
                    .timeout(REQUEST_TIMEOUT)
                    .header("Accept", "application/json")
                    .header("User-Agent", "GoToSystem-Velocity")
                    .GET()
                    .build();

            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                plugin.getLogger().warning("Update check returned HTTP status " + response.statusCode());
                return new UpdateCheckResult(currentVersion, currentVersion, false);
            }

            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
            String latestVersion = normalizeVersion(jsonObject.get("tag_name").getAsString());
            boolean updateAvailable = !Objects.equals(latestVersion, currentVersion);

            if (updateAvailable) {
                plugin.getLogger().info("New update available: " + latestVersion + " (current: " + currentVersion + ")");
            } else {
                plugin.getLogger().info("You are already on the latest version: " + currentVersion);
            }

            return new UpdateCheckResult(currentVersion, latestVersion, updateAvailable);
        } catch (Exception exception) {
            plugin.getLogger().severe("Unexpected error while checking for updates: " + exception.getMessage());
            return new UpdateCheckResult(currentVersion, currentVersion, false);
        }
    }

    private void notifyPlayer(Player player, String latestVersion, String oldVersion) {
        if (player == null) {
            return;
        }

        String message = plugin.getConfig().getString("update-check.message");
        if (message == null || message.isBlank()) {
            return;
        }

        player.sendMessage(ColorAPI.color(Component.text(
                message.replace("{OLD_VERSION}", oldVersion)
                        .replace("{NEW_VERSION}", latestVersion))));
    }

    private boolean isUpdateCheckEnabled() {
        return plugin.getConfig() != null && plugin.getConfig().getBoolean("update-check.enabled");
    }

    private String normalizeVersion(String version) {
        if (version == null) {
            return "";
        }

        String normalized = version.trim();
        if (normalized.startsWith("v") || normalized.startsWith("V")) {
            return normalized.substring(1);
        }

        return normalized;
    }

    private record UpdateCheckResult(String currentVersion, String latestVersion, boolean updateAvailable) {
    }
}
