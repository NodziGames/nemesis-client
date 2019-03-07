package com.nodzigames.client.nemesisclient.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    public List<String> parse(String input) {
        input = input.trim();
        input = input.replaceAll("\t", " ");
        input = input.replaceAll(" +", " ");

        List<String> inputs = new ArrayList<String>();

        inputs = new ArrayList<String>(Arrays.asList(input.split(" ")));

        return inputs;
    }

    public static boolean verifyCommandRegister(List<String> command) {
        if (command.size() == 3) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean verifyCommandLogin(List<String> command) {
        if (command.size() == 3) {
            return true;
        }
        else {
            return false;
        }
    }
}
