package me.l3gend.gotosystem.bungee.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public final class ColorAPI {

    private ColorAPI() {
    }

    public static String translate(String message) {
        if (message == null || message.isEmpty()) {
            return "";
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static TextComponent translateToTextComponent(String message) {
        return new TextComponent(translate(message));
    }

}
