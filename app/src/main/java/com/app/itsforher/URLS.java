package com.app.itsforher;

public class URLS {

    private static final String ROOT_URL = "http://itsforher.icelabs-eeyan.com/Api.php?apicall=";
    private static final String ROOT_URL_ = "http://192.168.137.240/testServer/Api.php?apicall=";


    public static final String URL_send;

    static {
        URL_send = ROOT_URL + "send";
    }

    public static final String URL_get= ROOT_URL + "get";

}

