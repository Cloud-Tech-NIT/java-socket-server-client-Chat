// ClientHandler.java

package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private ChatServer server;
    private PrintWriter out;
    private BufferedReader in;
    private String userName;

    public ClientHandler(Socket socket, ChatServer server) {
        this.clientSocket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Prompt the user to provide a unique name
            userName = promptForUniqueName();

            // Add the user to the list of connected clients
            server.addClient(this);

            String message;
            while ((message = in.readLine()) != null) {
                if ("/exit".equals(message)) {
                    break;
                }
                // Broadcast the message to all clients
                server.broadcastMessage(userName + ": " + message, this);
            }
        } catch (IOException e) {
            System.err.println("Client disconnected: " + e.getMessage());
        } finally {
            // Remove the client from the list of connected clients
            server.removeClient(this);
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String getUserName() {
        return userName;
    }

    public void disconnect() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing client socket: " + e.getMessage());
        }
    }

    private String promptForUniqueName() {
        try {
            String name;
            do {
                out.println("Please enter your username:");
                name = in.readLine();
                if (!server.isUserNameUnique(name)) {
                    out.println("Username '" + name + "' is already taken. Please choose another one.");
                }
            } while (!server.isUserNameUnique(name));
            return name;
        } catch (IOException e) {
            System.err.println("Error reading username: " + e.getMessage());
            return "User" + System.currentTimeMillis(); // Fallback to a unique name
        }
    }
}

