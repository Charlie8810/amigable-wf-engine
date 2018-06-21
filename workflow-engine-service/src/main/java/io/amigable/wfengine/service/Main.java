package io.amigable.wfengine.service;

import java.sql.SQLException;
import java.net.*;
import java.io.*;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by charly on 09-06-2018.
 */
public class Main {

    private static int port = 1802;


    public static void main(String[] args) throws IOException, SQLException
    {
        /*Read App Properties*/
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try (InputStream resourceStream = loader.getResourceAsStream("application.properties")) {
            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int port=Integer.parseInt(properties.getProperty("app.listener.port", "1802"));
        int schedulePeriod = Integer.parseInt(properties.getProperty("app.scheduler.period", "1000"));

        /*Schedule Listener*/
        //Timer o = new Timer();
        //o.scheduleAtFixedRate(new WFServerTask(),0,schedulePeriod);

        /*Socket Listener*/
        ServerSocket serverSocket = null;
        boolean listening = true;
        try {

            serverSocket = new ServerSocket(port);
            System.out.println("Listenting in port: " + port);

        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(-1);
        }

        while (listening) {
            new WFServerThread(serverSocket.accept()).start();
        }
        serverSocket.close();


    }
}
