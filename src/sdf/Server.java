package sdf;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    
    public static void main(String[] args) throws IOException {
        
        System.out.println("Starting server on port 2308..");

        // Create a new server socket and listen to a specific port (waits for client requests)
        ServerSocket server = new ServerSocket(2308);

        // Wait for a connection
        System.out.println("Waiting for incoming connection..");
        // Socket to use for communication with the client
        Socket conn = server.accept();
        System.out.println("Got a connection!");

        boolean exit = false;

        while (!exit) {

            // Do something
            // Client provided with "num1 operand num2". Server needs to read it.
            InputStream is = conn.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is); // read data written by ObjectOutputStream
            String input = ois.readUTF();
            System.out.printf("Client's input: %s\n", input);

            if (input.equals("exit")) {
                exit = true;
            }

            // Separate the inputs to num1, operand and num2 to an array
            String[] clientInput = input.split(" ");
            // for (int i = 0; i < clientInput.length; i++) {
            //     System.out.println(clientInput[i]);
            // }
            // Parse num1 and num2 to doubles
            double num1 = Double.parseDouble(clientInput[0].trim());
            double num2 = Double.parseDouble(clientInput[2].trim());

            System.out.println(num1);
            System.out.println(num2);
            // Create IF condition to check the orerand and perform arithmetic accordingly
            double result = 0;
            String operand = clientInput[1].trim();
            if (operand.equals("+")) {
                result = num1 + num2;
            }
            else if (operand.equals("-")) {
                result = num1 - num2;
            }
            else if (operand.equals("*")) {
                result = num1 * num2;
            }
            else if (operand.equals("/")) {
                result = num1 / num2;
            }

            //Parse the RESULT to string
            String resultString = Double.toString(result);
            System.out.printf("Result is: %s\n", resultString);

            // Get outputstream to wirite the RESULT back to client
            OutputStream os = conn.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeUTF(resultString);
            oos.flush();


        }
        // Close the connection, all streams will be closed
        conn.close();
        server.close();
    }
}
