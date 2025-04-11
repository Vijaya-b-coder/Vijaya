import java.io.*;
import java.net.*;
import java.util.*;

/**
 * A simple multi-client chat application using Java sockets and multithreading.
 * The server handles multiple clients and allows them to chat in real time.
 * Created for a hands-on learning experience!
 */
class ClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());
    
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            synchronized (clients) {
                clients.add(this);
            }
            out.println("Welcome to the chat! Start typing to send messages.");
        } catch (IOException e) {
            System.out.println("Error setting up client: " + e.getMessage());
        }
    }
    
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Client " + clientSocket.getPort() + " says: " + message);
                broadcastMessage("User " + clientSocket.getPort() + ": " + message, this);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected.");
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
            synchronized (clients) {
                clients.remove(this);
            }
        }
    }
    
    private void broadcastMessage(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender) {
                    client.out.println(message);
                }
            }
        }
    }
}

/**
 * The chat server that listens for incoming client connections and spawns a new thread for each client.
 */
public class ChatServer {
    public static void main(String[] args) {
        int port = 12345;
        System.out.println("Starting chat server on port " + port + "...");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New user connected: " + clientSocket.getPort());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }
}

/**
 * The chat client that connects to the server and enables users to send and receive messages.
 */
class ChatClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 12345;
        System.out.println("Connecting to chat server at " + serverAddress + "...");
        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {
            
            System.out.println("Connected! Type your message and press Enter to send.");
            
            Thread receiveThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });
            receiveThread.start();
            
            String userMessage;
            while ((userMessage = userInput.readLine()) != null) {
                out.println(userMessage);
            }
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
                           }
