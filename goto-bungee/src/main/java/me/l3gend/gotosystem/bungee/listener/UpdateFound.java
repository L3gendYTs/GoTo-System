package me.l3gend.gotosystem.bungee.listener;

import me.l3gend.gotosystem.bungee.GoToBungee;
import me.l3gend.gotosystem.bungee.utils.ColorUtils;
import me.l3gend.gotosystem.bungee.utils.UpdateChecker;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class UpdateFound implements Listener {

    @EventHandler
    public void onJoin(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();

        if (player.hasPermission("goto.update")) {
            new UpdateChecker(GoToBungee.get(), 105194).getVersion(version -> {
                if (!GoToBungee.get().getDescription().getVersion().equals(version)) {
                    player.sendMessage(new TextComponent(ColorUtils.color("&9&lGoTo System &7// &aNew update available. Current &aVersion: " + GoToBungee.get().getDescription().getVersion() + " &a- New version: " + version)));
                }
            });
        }
    }
}
