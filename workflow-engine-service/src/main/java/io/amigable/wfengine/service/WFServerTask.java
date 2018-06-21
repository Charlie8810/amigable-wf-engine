package io.amigable.wfengine.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.TimerTask;

/**
 * Created by capra on 19-06-2018.
 */
public class WFServerTask extends TimerTask {

    ConnectionManager manager;
    public WFServerTask() throws SQLException{
        this.manager=new ConnectionManager();
    }

    @Override
    public void run() {
        System.out.println(new Date().toString());
        System.out.println("A: " + Thread.currentThread().getId());



    }
}

