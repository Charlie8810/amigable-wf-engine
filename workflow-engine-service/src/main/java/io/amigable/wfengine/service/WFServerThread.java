package io.amigable.wfengine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.InvalidSchemaException;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import io.amigable.wfengine.service.entity.*;

import java.net.*;
import java.io.*;
import java.sql.SQLException;

/**
 * Created by capra on 16-06-2018.
 */
public class WFServerThread extends Thread {

    private Socket socket;
    private ConnectionManager manager;

    public WFServerThread(Socket socket) throws SQLException{
        super("WFServerThread");
        this.socket = socket;
        this.manager = new ConnectionManager();
        //System.out.println("B: " + this.getId());
    }

    @Override
    public void run(){
        try {

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            int length = in.readInt();
            if (length > 0) {
                byte[] message = new byte[length];
                in.readFully(message);
                String m = new String(message);
                boolean isValid = JsonSchemaValidator.isValidInput(m);
                if (isValid) {
                    //System.out.println("Esquema Valido");
                    ObjectMapper om = new ObjectMapper();
                    EntryInstance entryInstance = om.readValue(m, EntryInstance.class);
                    //System.out.println("Entrada: " + entryInstance.toString());

                    Listener listener = new Listener(this.manager);

                    InputEventResult<EntryInstance> result = listener.processInputEvent(entryInstance);
                    System.out.println(result.toString() + " | Thread: " + this.getId());
                }
            }

            in.close();
            out.close();
            this.manager.doClose();
            this.socket.close();
        } catch(InvalidSchemaException e) {
            System.out.println("Esquema Invalido");
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
