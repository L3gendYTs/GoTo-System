package me.l3gend.gotosystem.bungee.utils;

import net.md_5.bungee.api.ChatColor;

public class ColorUtils {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
