package com.nodzigames.client.nemesisclient.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nodzigames.client.nemesisclient.Main;
import com.nodzigames.client.nemesisclient.network.requests.LoginRequest;
import com.nodzigames.client.nemesisclient.network.requests.RegisterRequest;
import com.nodzigames.client.nemesisclient.network.requests.ClientRequest;
import com.nodzigames.client.nemesisclient.network.responses.ClientResponse;
import com.nodzigames.client.nemesisclient.network.responses.PoolResponse;
import com.nodzigames.client.nemesisclient.parser.Parser;
import com.nodzigames.client.nemesisclient.parser.Timer;
import com.nodzigames.client.nemesisclient.render.Draw;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.nodzigames.client.nemesisclient.logic.Commands.*;
import static com.nodzigames.client.nemesisclient.network.endpoints.Endpoints.*;

public class Processor {

    public static ObjectMapper mapper = new ObjectMapper();

    public static void process(List<String> command) throws JsonProcessingException {
        switch(command.get(0)) {
            case C_REGISTER:
                register(command);
                break ;
            case C_LOGIN:
                login(command);
                break ;
            case C_LIST:
                list(command);
                break ;
            case C_TIME:
                time(command);
                break ;
            case C_CLEAR:
                clear(command);
                break ;
            case C_LOGOUT:
                logout(command);
                break ;
            case C_POOLS:
                pools(command);
                break ;
            case C_STATUS:
                status(command);
                break ;
                default:
                    Draw.println("Unrecognized command! Type 'help' for a list of commands");
                    break ;
        }
    }

    public static void register(List<String> command) throws JsonProcessingException {
        if (Parser.verifyCommandRegister(command)) {
            try {
                RegisterRequest requestBody = new RegisterRequest(command.get(1), command.get(2));

                String body = mapper.writeValueAsString(requestBody);

                HttpResponse<String> response = Unirest.post(E_REGISTER)
                        .header("Content-Type", "application/json")
                        .body(body)
                        .asString();
                Draw.println(response.getBody());
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }
        else {
            Draw.println("Command formatted poorly. Type 'help' for instructions");
        }
    }

    public static void login(List<String> command) throws JsonProcessingException {
        if (Parser.verifyCommandLogin(command)) {
            if (Main.token == null) {
                try {
                    LoginRequest requestBody = new LoginRequest(command.get(1), command.get(2));

                    String body = mapper.writeValueAsString(requestBody);

                    HttpResponse<String> response = Unirest.post(E_LOGIN)
                            .header("Content-Type", "application/json")
                            .body(body)
                            .asString();

                    if (response.getBody().equals("This username does not exist!") || response.getBody().equals("Incorrect password!")) {
                        Draw.println(response.getBody());
                    } else {
                        Main.token = response.getBody();
                        Main.username = command.get(1);
                        Draw.println("Login successful! Welcome " + Main.username);
                    }
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
            }
            else {
                Draw.println("You are already logged in to an account. Please logout first!");
            }
        }
        else {
            Draw.println("Command formatted poorly. Type 'help' for instructions");
        }
    }

    public static void status(List<String> command) throws JsonProcessingException {
        if (Parser.verifyCommandNoArgs(command)) {
            try {
                ClientRequest requestBody = new ClientRequest(Main.username);

                String body = mapper.writeValueAsString(requestBody);

                HttpResponse<String> response = Unirest.post(E_RETRIEVE)
                        .header("Content-Type", "application/json")
                        .body(body)
                        .asString();

                if (response.getBody().equals("")) {
                    Draw.println("Cannot retrieve status, please log in first!");
                }
                else {
                    try {
                        ClientResponse clientResponse = mapper.readValue(response.getBody(), ClientResponse.class);
                        Draw.println(clientResponse.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }
    }

    public static void list(List<String> command) {
        if (Parser.verifyCommandList(command)) {
            try {
                HttpResponse<String> response = Unirest.get(E_ALL)
                        .header("Content-Type", "application/json")
                        .asString();
                try {
                    List<ClientResponse> clients = mapper.readValue(response.getBody(), new TypeReference<List<ClientResponse>>(){});

                    //Check if you passed in an amount of lines, otherwise it defaults to 10
                    int displayCount = 10;
                    if (command.size() == 2) {
                        displayCount = Integer.parseInt(command.get(1));
                    }

                    Draw.println("Listing first " + displayCount + " users...\n");

                    for (int i = 0; i < displayCount; i++) {
                        if (i >= clients.size()) {
                            break ;
                        }
                        if (clients.get(i) != null) {
                            Draw.println(clients.get(i).briefToString());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }
        else {
            Draw.println("Command formatted poorly. Type 'help' for instructions");
        }
    }

    public static void pools(List<String> command) {
        if (Parser.verifyCommandNoArgs(command)) {
            try {
                HttpResponse<String> response = Unirest.get(E_POOLS)
                        .header("Content-Type", "application/json")
                        .asString();

                try {
                    List<PoolResponse> pools = mapper.readValue(response.getBody(), new TypeReference<List<PoolResponse>>(){});

                    for (int i = 0; i < 5; i++) {
                        Draw.println(pools.get(i).getName());
                        Draw.println("Complexity = " + pools.get(i).getDifficulty());
                        Draw.println("Bounty: " + pools.get(i).getBounty() + "KB.   Max reward = " + (pools.get(i).getBounty() / 10) + "KB (10%)" + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }
    }

    public static void time(List<String> command) {
        if (Parser.verifyCommandNoArgs(command)) {
            try {
                HttpResponse<String> response = Unirest.get(E_TIME)
                        .header("Content-Type", "application/json")
                        .asString();

                Date date = new Date(Long.parseLong(response.getBody()));

                Draw.println("Server Time (GMT+2): " + Timer.toString(date));

            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }
        else {
            Draw.println("Command formatted poorly. Type 'help' for instructions");
        }
    }

    public static void clear(List<String> command) {
        if (Parser.verifyCommandNoArgs(command)) {
            Draw.cls();
        }
        else {
            Draw.println("Command formatted poorly. Type 'help' for instructions");
        }
    }

    public static void logout(List<String> command) {
        if (Parser.verifyCommandNoArgs(command)) {

            if (Main.username == null) {
                Draw.println("No account logged in currently...");
            }
            else {
                Main.username = null;
                Main.token = null;
                Draw.println("You've logged out successfully");
            }
        }
        else {
            Draw.println("Command formatted poorly. Type 'help' for instructions");
        }
    }
}
