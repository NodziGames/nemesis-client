package com.nodzigames.client.nemesisclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nodzigames.client.nemesisclient.logic.Processor;
import com.nodzigames.client.nemesisclient.parser.Parser;
import com.nodzigames.client.nemesisclient.render.Draw;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static String token = null; //The token used to make calls to the server. Retrieved after login
    public static String username = null; //The username that was logged in with. Not really used for calls because it's insecure

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();

        List<String> command;

        Draw.cls();

        //Main Loop
        while (true) {
            Draw.print("/sys/nemesis> ");

            String input = scanner.nextLine();

            command = parser.parse(input);

            try {
                Processor.process(command);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
