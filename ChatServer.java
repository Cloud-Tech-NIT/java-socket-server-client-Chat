// ChatServer.java

package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private int port;
    private List<ClientHandler> clients = new ArrayList<>();

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                String clientAddress = clientSocket.getInetAddress().getHostAddress();
                System.out.println("New client connected from " + clientAddress);
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    public synchronized void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public synchronized void addClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastMessage(clientHandler.getUserName() + " connected.", null);
        broadcastConnectedUsers();
    }

    public synchronized void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        System.out.println(clientHandler.getUserName() + " disconnected");
        broadcastMessage(clientHandler.getUserName() + " disconnected.", null);
        broadcastConnectedUsers();
    }

    public synchronized boolean isUserNameUnique(String userName) {
        for (ClientHandler client : clients) {
            if (client.getUserName().equals(userName)) {
                return false;
            }
        }
        return true;
    }

    private synchronized void broadcastConnectedUsers() {
        StringBuilder userList = new StringBuilder("Connected Users:\n");
        for (ClientHandler client : clients) {
            userList.append(client.getUserName()).append("\n");
        }
        for (ClientHandler client : clients) {
            client.sendMessage(userList.toString());
        }
    }

    public synchronized void disconnectClient(String userName) {
        for (ClientHandler client : clients) {
            if (client.getUserName().equals(userName)) {
                client.sendMessage("You have been disconnected by the server.");
                client.disconnect();
                break;
            }
        }
    }

    public synchronized void handleCommand(String command) {
        if ("/shutdown".equals(command)) {
            // Disconnect all clients and shut down the server
            for (ClientHandler client : clients) {
                client.sendMessage("Server is shutting down. Goodbye!");
                client.disconnect();
            }
            System.out.println("Server is shutting down.");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        int port = 9090; // Change this to your desired port
        ChatServer server = new ChatServer(port);
        server.start();
    }
}


