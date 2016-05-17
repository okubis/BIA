package com.layer;

/**
 * Created by okubis on 5/17/16.
 */
public class ConnectionParameters {
    private static String host;
    private static int port;

    // this should be called only from main and set properly by console input
    public static synchronized void setHost(String used_host){
        host = used_host;
    }

    // this should be called only from main and set properly by console input
    public static synchronized void setHost(int used_port){
        port = used_port;
    }

    protected static String getHost(){
        return host;
    }

    protected static int getPort(){
        return port;
    }


}
