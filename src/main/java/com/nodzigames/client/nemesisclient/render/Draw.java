package com.nodzigames.client.nemesisclient.render;

public class Draw {

    public static void cls() {
        System.out.println("\033[2J");
    }

    public static void println(String text) {
        System.out.println(text);
    }

    public static void print(String text) {
        System.out.print(text);
    }
}
