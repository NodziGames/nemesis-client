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
import com.nodzigames.client.nemesisclient.parser.Parser;
import com.nodzigames.client.nemesisclient.render.Draw;

import java.io.IOException;
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
                Draw.println("You are already logged in to an account");
            }
        }
        else {
            Draw.println("Command formatted poorly. Type 'help' for instructions");
        }
    }

    public static void status(List<String> command) throws JsonProcessingException {
        if (Parser.verifyCommandStatus(command)) {
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
                        Draw.println("UserName: " + clientResponse.getUsername());
                        Draw.println("Firewall Level: " + clientResponse.getFirewallLevel());
                        Draw.println("Data: " + clientResponse.getData() + " KB");
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

                    Draw.println("");

                    for (int i = 0; i < displayCount; i++) {
                        if (i >= clients.size()) {
                            break ;
                        }
                        if (clients.get(i) != null) {
                            Draw.println(clients.get(i).toString() + "\n");
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
}
