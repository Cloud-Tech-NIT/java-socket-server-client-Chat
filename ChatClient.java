package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import ui.ChatClientGUI; // Import correct class

public class ChatClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ChatClientGUI gui;

    public ChatClient(String serverAddress, int port, ChatClientGUI gui) {
        try {
            this.gui = gui;
            socket = new Socket(serverAddress, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            gui.showError("Error connecting to server: " + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void receiveMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                gui.displayMessage(message);
            }
        } catch (IOException e) {
            gui.showError("Error receiving message: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            gui.showError("Error disconnecting from server: " + e.getMessage());
        }
    }
}
