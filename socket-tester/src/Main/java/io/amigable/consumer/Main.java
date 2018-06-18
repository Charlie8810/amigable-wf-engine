/**
 * Created by capra on 17-06-2018.
 */


package io.amigable.consumer;

import java.net.*;
import java.io.*;



public class Main {

    public static void main(String[] args){

        try{
            Socket s = new Socket("localhost", 1802);
            //PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            //BufferedReader entrada = new BufferedReader(new InputStreamReader(s.getInputStream()));

            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream strm = loader.getResourceAsStream("mensajeSalida.json");
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = strm.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();

            byte[] st = buffer.toByteArray();

            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());



            out.writeInt(st.length);
            out.write(st);


            //System.out.println(in.readChar());
            in.close();
            out.close();
            //FileInputStream fis = new FileInputStream(new File("/resources/mensajeSalida.json"));
            //System.out.println(strm);
            //String inputLine = entrada.readLine();
            //pw.println(strm);
            //pw.
            //System.out.println(inputLine);
            /*while ((inputLine = entrada.readLine()) != null) {
                System.out.println(inputLine);
                //pw.println("OK");
            }*/
            //inputLine = entrada.readLine();
            //System.out.println(inputLine);

            //entrada.close();
            //pw.close();
            s.close();
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
