package com.example.players;

import java.util.Objects;
import java.util.logging.Logger;


public class PlayerSameProcess {
    // Fields of the Player Class
    private final String name;
    private int messageCount;
    private String lastReceivedMessage;
    private final int maxMessages;
    // initialize the logger object for logging all the process
    private static final Logger logger = LoggerUtility.getLogger();

    // Constructor of the Player Class
    public PlayerSameProcess(String name, int maxMessages) {
        this.name = name;
        this.messageCount = 1;
        this.lastReceivedMessage = "";
        this.maxMessages = maxMessages;
    }

    // This method is for sending the message to another player
    public void sendMessage(PlayerSameProcess receiver, String message) {
        if (this.messageCount == 1 && Objects.equals(this.lastReceivedMessage, "")){
            System.out.println(this.name + " sends message to " + receiver.getName() + ": " + message + ". Msg number: " + this.messageCount);
            logger.info(this.name + " sends message to " + receiver.getName() + ": " + message + ". Msg number: " + this.messageCount);
            receiver.receiveMessage(this, message);
            this.messageCount++;
        }
        else if (this.messageCount <= this.maxMessages) {
            System.out.println(this.name + " sends message to " + receiver.getName() + ": " + message
                    + " (In reply to: \"" + this.lastReceivedMessage + "\")" + ". Msg number: " + this.messageCount
            );
            logger.info(this.name + " sends message to " + receiver.getName() + ": " + message
                    + " (In reply to: \"" + this.lastReceivedMessage + "\")" + ". Msg number: " + this.messageCount
            );
            receiver.receiveMessage(this, message);
            this.messageCount++;
        } else {
            System.out.println(this.name + " has reached the message limit and cannot send more messages.");
            logger.warning(this.name + " has reached the message limit and cannot send more messages.");
        }
    }

    // Method to receive a message from another player
    public void receiveMessage(PlayerSameProcess sender, String message) {
        this.lastReceivedMessage = message;
        System.out.println(this.name + " received a message from " + sender.getName() + ": " + message);
        logger.info(this.name + " received a message from " + sender.getName() + ": " + message);
    }

    // Getter of the Player Class for getting the name of the player
    public String getName() {
        return name;
    }

    // Getter of the Player Class for getting the maximum number of messages can players exchange
    public int getMessageCount() {
        return messageCount;
    }
}
