package Bai_2;

import java.io.*;
import java.net.*;
import java.util.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public Server() throws Exception {
        ServerSocket serverSocket = new ServerSocket(8088);
        System.out.println("Server started");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                ClientHandler clientHandler = new ClientHandler(in, out, clients);
            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Server();
    }
}

class ClientHandler extends Thread {
    private BufferedReader in;
    private DataOutputStream out;
    private ArrayList<ClientHandler> clients;
    private String userName;

    public ClientHandler(BufferedReader in, DataOutputStream out, ArrayList<ClientHandler> clients) {
        this.in = in;
        this.out = out;
        this.clients = clients;
        clients.add(this);
        start();
    }

    public void send(String message) {
        try {
            out.writeBytes(message + "\n");
            out.flush(); // Ensure data is sent immediately
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.send(message);
        }
    }

    public void run() {
        try {
            userName = in.readLine();
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(message);
                broadcast(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
