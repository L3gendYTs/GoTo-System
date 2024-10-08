package me.l3gend.gotosystem.bungee.listener;

import me.l3gend.gotosystem.bungee.GoToBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class UpdateFound implements Listener {

    @EventHandler
    public void onJoin(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();

        if (player.hasPermission("goto.update")) {
            GoToBungee.get().getUpdateChecker().checkForUpdatesAsync(player);
        }
    }
}
