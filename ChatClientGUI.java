package ui;

import client.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatClientGUI {
    private JFrame frame;
    private JTextArea messageArea;
    private JTextField inputField;
    private ChatClient client;

    public ChatClientGUI(String serverAddress, int port) {
        frame = new JFrame("Chat Client");
        messageArea = new JTextArea(20, 50);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        inputField = new JTextField(50);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputField, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                inputField.setText(""); // Clear input field after sending message
                client.sendMessage(message);
            }
        });

        client = new ChatClient(serverAddress, port, this);
        new Thread(client::receiveMessages).start();
    }

    public void displayMessage(String message) {
        messageArea.append(message + "\n");
    }

    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(frame, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        String serverAddress = "192.168.1.104"; // Use the server's IP address here if needed
        int port = 9090; // Match this with the server's port
        new ChatClientGUI(serverAddress, port);
    }
}
