package Bai_1;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String receivedNumber;
        while ((receivedNumber = in.readLine()) != null) {
            System.out.println( receivedNumber);
        }
        socket.close();
    }
}
