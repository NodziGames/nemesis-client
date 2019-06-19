package com.nodzigames.client.nemesisclient.network.requests;

public class BuyRequest {

    private String token;
    private int amount;
    private int index;

    public BuyRequest(String token, int index, int amount) {
        this.token = token;
        this.index = index;
        this.amount = amount;
    }




    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
