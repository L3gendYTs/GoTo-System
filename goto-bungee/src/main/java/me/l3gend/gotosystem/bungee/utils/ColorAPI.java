package me.l3gend.gotosystem.bungee.utils;

import net.md_5.bungee.api.ChatColor;

public class ColorAPI {

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
