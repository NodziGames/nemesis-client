package com.nodzigames.client.nemesisclient.network.responses;

public class ClientResponse {

    //The response for retrieving a client from the server. Same structure but without the jsonignore fields
    private String username;
    private int firewallLevel;
    private long data;
    private long dataFarms;



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

    @Override
    public String toString() {
        return "Username -> " + this.username + "\n" +
                "Data -> " + this.data + "kb" + "\n" +
                "Firewall Level -> " + this.firewallLevel + "\n" +
                "Data Farms -> " + this.dataFarms + "x (" + this.dataFarms + "kb/m)";
    }

    //Used to list accounts
    public String briefToString() {
        return this.username + ": Lvl -> " + this.firewallLevel + ": Data -> " + this.data + "kb";
    }

    public long getDataFarms() {
        return dataFarms;
    }

    public void setDataFarms(long dataFarms) {
        this.dataFarms = dataFarms;
    }
}
