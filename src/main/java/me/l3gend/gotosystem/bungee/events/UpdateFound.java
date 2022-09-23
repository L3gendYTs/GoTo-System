package me.l3gend.gotosystem.bungee.events;

import me.l3gend.gotosystem.bungee.utils.UpdateChecker;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static me.l3gend.gotosystem.bungee.GoToBungee.getInstance;
import static me.l3gend.gotosystem.bungee.utils.ColorUtils.color;

public class UpdateFound implements Listener {

    @EventHandler
    public void onJoin(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();

        if (player.hasPermission("goto.update")) {
            new UpdateChecker(getInstance(), 105194).getVersion(version -> {
                if (!getInstance().getDescription().getVersion().equals(version)) {
                    player.sendMessage(new TextComponent(color("&9&lGoTo System &7// &aNew update available. Current &aVersion: " + getInstance().getDescription().getVersion() + " &a- New version: " + version)));
                }
            });
        }
    }
}
