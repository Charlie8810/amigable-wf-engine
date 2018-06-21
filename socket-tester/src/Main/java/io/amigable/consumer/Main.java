/**
 * Created by capra on 17-06-2018.
 */


package io.amigable.consumer;

import java.net.*;
import java.io.*;



public class Main {

    public static void main(String[] args){

        try{


            //System.out.println(String.format("%03d", 11));




            Socket s = new Socket("localhost", 1802);

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

            /*get response*/
            //boolean response = in.readBoolean();
            //System.out.println(response);


            /*if (args.length != 1) {
                System.err.println("Usage: java Planet <earth_weight>");
                System.exit(-1);
            }
            double earthWeight = Double.parseDouble(args[0]);
            double mass = earthWeight/Planet.EARTH.surfaceGravity();
            for (Planet p : Planet.values())
                System.out.printf("Your weight on %s is %f%n",
                        p, p.surfaceWeight(mass));
            */

            in.close();
            out.close();
            s.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
