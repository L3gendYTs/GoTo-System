package me.l3gend.gotosystem.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import it.mycraft.powerlib.common.utils.ColorAPI;
import me.l3gend.gotosystem.velocity.GoToVelocity;
import me.l3gend.gotosystem.velocity.utils.UpdateChecker;
import net.kyori.adventure.text.Component;


public class UpdateFound {

    @Subscribe
    public void onJoin(PostLoginEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("goto.update")) {
            new UpdateChecker(GoToVelocity.get(), 105194).getVersion(version -> {
                if (!GoToVelocity.get().getPluginDescription().getVersion().equals(version)) {
                    player.sendMessage(ColorAPI.color(Component.text("&9&lGoTo System &7// &aNew update available. Current &aVersion: " + GoToVelocity.get().getPluginDescription().getVersion() + " &a- New version: " + version)));
                }
            });
        }
    }
}
