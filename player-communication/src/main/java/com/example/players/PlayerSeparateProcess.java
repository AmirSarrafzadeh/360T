package com.example.players;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Logger;

public class PlayerSeparateProcess {
    private String p1Name;
    private String p2Name;
    private int port;
    private int otherPort;
    private int messageCounter = 0;
    private boolean isInitiator;
    // initialize the logger object for logging all the process
    private static final Logger logger = LoggerUtility.getLogger();

    public PlayerSeparateProcess(String p1Name, String p2Name, int port, int otherPort, boolean isInitiator) {
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        this.port = port;
        this.otherPort = otherPort;
        this.isInitiator = isInitiator;
    }

    public void start() {
        // Starting server socket to listen
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started and listening on port: " + port);
            logger.info("Server started and listening on port: " + port);
            int maxMessages = Integer.parseInt(ConfigManager.getProperty("maxMessages"));

            // If this player is the initiator, start by sending a message
            if (isInitiator) {
                System.out.println("Enter a message for " + p1Name + " to send to " + p2Name + ":");
                Scanner scanner = new Scanner(System.in);
                String messageFromInitiator = scanner.nextLine();
                sendMessage(messageFromInitiator);
                logger.info(p1Name + " sends: " + messageFromInitiator);
            }

            while (messageCounter < 2 * maxMessages) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String message = in.readLine();
                    System.out.println(p1Name + " received: " + message);
                    messageCounter++;
                    if (messageCounter < 2 * maxMessages) {
                        // Simulate some delay before sending back a response
                        Thread.sleep(1000);
                        System.out.println("Enter a message for " + p1Name + " to send to " + p2Name + ":");
                        Scanner scanner = new Scanner(System.in);
                        String messageFromInitiator = scanner.nextLine();
                        sendMessage(messageFromInitiator + " " + messageCounter);
                        logger.info(p1Name + " sends: " + messageFromInitiator);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.err.println("Server failed on port: " + port + " with error: " + e.getMessage());
            logger.severe("Server failed on port: " + port + " with error: " + e.getMessage());
        }
    }



    private void sendMessage(String message) {
        int attempts = 0;
        while (attempts < 3) {
            try (Socket socket = new Socket("localhost", otherPort);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                System.out.println(p1Name + " sends: " + message);
                out.println(message);
                messageCounter++;
                return; // Success, exit the loop
            } catch (IOException e) {
                System.err.println("Attempt " + (attempts + 1) + " failed with error: " + e.getMessage());
                logger.severe("Attempt " + (attempts + 1) + " failed with error: " + e.getMessage());
                e.printStackTrace();  // This will give you a detailed stack trace
                attempts++;
                try {
                    Thread.sleep(1000); // Wait for 1 second before retrying
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
        System.err.println("Failed to connect after several attempts.");
    }


    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage: java Player <p1Name> <p2Name> <port> <otherPort> <isInitiator>");
            return;
        }

        String p1Name = args[0];
        String p2Name = args[1];
        int port = Integer.parseInt(args[2]);
        int otherPort = Integer.parseInt(args[3]);
        boolean isInitiator = Boolean.parseBoolean(args[4]);

        PlayerSeparateProcess player = new PlayerSeparateProcess(p1Name, p2Name, port, otherPort, isInitiator);
        player.start();
        logger.info("Conversation finished because both players have sent the maximum number of messages. Exiting.");
    }
}

