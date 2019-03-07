package com.nodzigames.client.nemesisclient.network.responses;

public class ClientResponse {

    //The response for retrieving a client from the server. Same structure but without the jsonignore fields
    private String username;
    private int firewallLevel;
    private long data;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFirewallLevel() {
        return firewallLevel;
    }

    public void setFirewallLevel(int firewallLevel) {
        this.firewallLevel = firewallLevel;
    }

    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }
}
