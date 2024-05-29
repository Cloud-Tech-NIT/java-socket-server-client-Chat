# java-socket-server-client-Chat : Online Chat Application


This is a simple online chat application developed in Java. It allows multiple users to connect to a central server and exchange messages in real-time.

## Features

- Users can connect to the server using a client application.
- Messages sent by one user are broadcasted to all connected users.
- Basic text-based user interface for sending and receiving messages.

## Project Structure

The project is structured into separate packages for server, client, and user interface:

```
ChatApplication/
│
├── src/
│   ├── server/
│   ├── client/
│   └── ui/
│
└── README.md
```

## Usage

1. **Server Setup**:
   - Compile and run `ChatServer.java` to start the chat server.
   - By default, the server listens on port 8080.

2. **Client Setup**:
   - Compile and run `ChatClient.java` to start the client application.
   - Provide the server address (localhost by default) and port number.

3. **User Interface**:
   - Use the text-based interface to send and receive messages.

## How to Run

1. Clone this repository to your local machine.
2. Compile the Java source files using a Java compiler.
3. Start the server by running `ChatServer.java`.
4. Start the client by running `ChatClient.java`.
5. Follow the on-screen instructions to connect and start chatting.

## Implementation Details

- The server uses socket programming to manage connections from multiple clients.
- Each client is assigned a unique user ID upon connection.
- The server maintains a list of connected users and broadcasts messages to all clients.
- The client application connects to the server and provides a text-based interface for sending and receiving messages.
- The client can exit from chat by /exit command.

## Dependencies

This project does not have any external dependencies beyond the standard Java libraries.

## License

This project is licensed under the [MIT License](LICENSE).

## Contributors

- [Abdul Ruknuddin](https://github.com/Cloud-Tech-NIT)
```

