package com.nodzigames.client.nemesisclient.network.endpoints;

public class Endpoints {
    public static final String E_BASEURL = "http://localhost:8080/";


    //Client calls
    public static final String E_REGISTER = E_BASEURL + "/client/register";
    public static final String E_LOGIN = E_BASEURL + "/client/login";
    public static final String E_RETRIEVE = E_BASEURL + "/client/retrieve";
    public static final String E_ALL = E_BASEURL + "/client/all";

    //System Calls
    public static final String E_TIME = E_BASEURL + "/system/time";

    //Pool calls
    public static final String E_POOLS = E_BASEURL + "/pool/all";

    //Market Calls
    public static final String E_BUY = E_BASEURL + "/market/buy";
}
