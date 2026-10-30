package me.l3gend.gotosystem.bungee.listener;

import me.l3gend.gotosystem.bungee.utils.UpdateChecker;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class UpdateFound implements Listener {

    private final UpdateChecker updateChecker;

    public UpdateFound(UpdateChecker updateChecker) {
        this.updateChecker = updateChecker;
    }

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        if (player.hasPermission("goto.update")) {
            updateChecker.checkForUpdatesAsync(player);
        }
    }
}
