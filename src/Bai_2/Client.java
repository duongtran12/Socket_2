package Bai_2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        new Client();
    }

    public Client() throws Exception {
        Socket socket = new Socket("localhost", 8088);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String userName = scanner.nextLine();
        out.writeBytes(userName + "\n");
        out.flush();

        new Thread(() -> {
            while (true) {
                try {
                    String message = in.readLine();
                    if (message != null) {
                        System.out.println(message);
                    } else {
                        System.out.println("Server disconnected");
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        while (true) {
            String message = scanner.nextLine();
            out.writeBytes(userName + ": " + message + "\n");
            out.flush();
        }
    }
}
