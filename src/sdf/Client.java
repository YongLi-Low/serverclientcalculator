package sdf;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    
    public static void main(String[] args) throws IOException {
        
        // Connect to the server listening on port 3000
        // 127.0.0.1 or localhost means my local computer
        Socket conn = new Socket("localhost", 2308);
        System.out.println("Connected to server on localhost: 2308");

        boolean exit = false;

        Console cons = System.console();

        while (!exit){
            
            String line = cons.readLine("Please enter 'num1' 'operand' 'num2' for arithmetic calculation: ");

            // Do something
            if (line.equals("exit")) {
                exit = true;
            }

            // Get the outputstream and write a line to the server
            OutputStream os = conn.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            oos.writeUTF(line);
            oos.flush();

            //Read the result from the server
            InputStream is = conn.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            String result = ois.readUTF();
            System.out.printf("Server returns the result: %s\n", result);


            // Close the connection
            //conn.close();
        }

        // Close the connection
        conn.close();
    }
}
