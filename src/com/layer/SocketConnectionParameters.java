package com.layer;

/**
 * Created by okubis on 5/17/16.
 */
public class SocketConnectionParameters {
    private String host;
    private int port;

    public SocketConnectionParameters(String host, int port) {
        this.host = host;
        this.port = port;
    }

    // this should be called only from main and set properly by user
    public void setHost(String used_host){
        host = used_host;
    }

    // this should be called only from main and set properly by user
    public void setHost(int used_port){
        port = used_port;
    }

    protected String getHost(){
        return host;
    }

    protected int getPort(){
        return port;
    }


}
