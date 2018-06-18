package io.amigable.wfengine.service;

import java.net.*;
import java.io.*;
import java.sql.Connection;

/**
 * Created by capra on 16-06-2018.
 */
public class WFServerThread extends Thread {

    private Socket socket;
    private ConnectionManager manager;

    public WFServerThread(Socket socket, ConnectionManager manager){
        super("WFServerThread");
        this.socket = socket;
        this.manager = manager;
    }

    @Override
    public void run(){
        try {

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            int length = in.readInt();
            if(length > 0){
                byte[] message = new byte[length];
                in.readFully(message);
                String m = new String(message);
                System.out.println(m);
            }

            in.close();
            out.close();
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
