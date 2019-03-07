package com.nodzigames.client.nemesisclient.network.requests;

public class ClientRequest {

    private String username;

    public ClientRequest(String username) {
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
