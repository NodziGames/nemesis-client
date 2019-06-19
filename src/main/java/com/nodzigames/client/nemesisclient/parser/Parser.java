package com.nodzigames.client.nemesisclient.parser;

import com.nodzigames.client.nemesisclient.render.Draw;

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

    public static boolean verifyCommandList(List<String> command) {
        if (command.size() == 1) {
            return true;
        }
        else if (command.size() == 2) {
            try {
                Integer.parseInt(command.get(1));

                if (Integer.parseInt(command.get(1)) <= 0 || Integer.parseInt(command.get(1)) > 50) {
                    return false;
                }
                return true;
            } catch (NumberFormatException e) {
                Draw.println("The number entered is not an integer");
                return false;
            }
        }
        else {
            return false;
        }
    }

    public static boolean verifyCommandNoArgs(List<String> command) {
        if (command.size() == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean verifyCommandConnect(List<String> command) {
        if (command.size() != 2) {
            return false;
        }

        try {
            Integer.parseInt(command.get(1));

            if (Integer.parseInt(command.get(1)) < 0 || Integer.parseInt(command.get(1)) > 4) {
                Draw.println("Pool Index out of range!");
                return false;
            }
        } catch (NumberFormatException e) {
            Draw.println("The number entered is not an integer");
            return false;
        }

        return true;
    }

    public static boolean verifyCommandBuy(List<String> command) {
        if (command.size() != 3) {
            return false;
        }

        try {
            Integer.parseInt(command.get(1));
            Integer.parseInt(command.get(2));
        } catch (NumberFormatException e) {
            Draw.println("Numbers provided are not integers");
            return false;
        }

        return true;
    }
}
