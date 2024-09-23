package com.example.players;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtility {
    private static final Logger logger = Logger.getLogger(LoggerUtility.class.getName());

    static {
        setupLogger();
    }

    private static void setupLogger() {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$s] %5$s %n");

        logger.setLevel(Level.INFO);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.WARNING);
        logger.addHandler(consoleHandler);

        try {
            FileHandler fileHandler = new FileHandler("360T.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            logger.info("Logging to console and 360T.log file has been configured.");
        } catch (IOException e) {
            logger.severe("Failed to initialize file handler for logging: " + e.getMessage());
            System.err.println("Failed to initialize file handler for logging: " + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
