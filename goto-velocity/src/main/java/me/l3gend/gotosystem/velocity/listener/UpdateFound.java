package me.l3gend.gotosystem.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import me.l3gend.gotosystem.velocity.GoToVelocity;


public class UpdateFound {

    @Subscribe
    public void onJoin(PostLoginEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("goto.update")) {
            GoToVelocity.get().getUpdateChecker().checkForUpdatesAsync(player);
        }
    }
}
