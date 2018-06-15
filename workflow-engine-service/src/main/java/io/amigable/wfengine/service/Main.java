package io.amigable.wfengine.service;

import java.sql.SQLException;

/**
 * Created by charly on 09-06-2018.
 */
public class Main {

    public static void main(String[] args)
    {
        try{
            ConnectionManager manager = new ConnectionManager();
            Listener lisen = new Listener(manager);

            boolean resultado =  lisen.evaluateTransitionStepConditions(2,1, 1);


        }catch (SQLException sqlEx){
            System.out.println(sqlEx.getMessage());
        }catch (Exception gEx){
            System.out.println(gEx.getMessage());
        }




    }
}
