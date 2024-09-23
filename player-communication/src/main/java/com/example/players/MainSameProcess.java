package com.example.players;

/*
  This script is a simple communication app in which two players can chat each-other.
  Author: Amir Sarrafzadeh Arasi
  Date: 22/09/2024
*/

import java.util.Scanner;
import java.util.logging.*;

public class MainSameProcess {

    // initialize the logger object for logging all the process
    private static final Logger logger = LoggerUtility.getLogger();

    // First read the config.properties file and then run the runInSameProcess method
    // for the Task 5
    public static void main(String[] args) {

        try  {
            // Get the values from config.properties file
            String player1Name = ConfigManager.getProperty("player1.name");
            String player2Name = ConfigManager.getProperty("player2.name");
            int maxMessages = Integer.parseInt(ConfigManager.getProperty("maxMessages"));
            runInSameProcess(player1Name, player2Name, maxMessages);
        } catch (NumberFormatException ex) {
            System.out.println("Error parsing a number from the config file: " + ex.getMessage());
            ex.printStackTrace();
        }
        logger.info("Conversation finished because both players have sent the maximum number of messages. Exiting.");
    }

    // This method is designed as a communication simulator among two players, and first it gets the first player
    // message and send to the second one and sends back the response in the format which is asked in the Task 3.
    private static void runInSameProcess(String p1, String p2, int maxMessages) {
        try (Scanner scanner = new Scanner(System.in)) {
            PlayerSameProcess player1 = new PlayerSameProcess(p1, maxMessages);
            PlayerSameProcess player2 = new PlayerSameProcess(p2, maxMessages);
            logger.info("Players created successfully: " + player1.getName() + ", " + player2.getName());
            logger.info("Conversation in the same process is started between " + player1.getName() + ", " + player2.getName());

            // Interaction loop
            while (player1.getMessageCount() <= maxMessages) {
                System.out.println("Enter a message for " + player1.getName() + " to send to " + player2.getName() + ":");
                String messageFromInitiator = scanner.nextLine();
                player1.sendMessage(player2, messageFromInitiator);

                System.out.println("Enter a message for " + player2.getName() + " to send to " + player1.getName() + ":");
                String messageFromSecondPlayer = scanner.nextLine();
                player2.sendMessage(player1, messageFromSecondPlayer);
            }
        } catch (Exception e) {
            logger.severe("Error during input handling: " + e.getMessage());
            System.out.println("Error during input handling: " + e.getMessage());
        }
    }
}
