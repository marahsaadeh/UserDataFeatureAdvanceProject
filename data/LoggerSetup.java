package edu.najah.cap.data;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

public class LoggerSetup {
    private static final Logger logger = Logger.getLogger("MyAppLogger");

    static {
        setupLogger();
    }

    public static void setupLogger() {
        try {
            int fileSize = 1024 * 1024 * 10;
            int fileCount = 2;
            FileHandler fileHandler = new FileHandler("output.log", fileSize, fileCount, true);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.setUseParentHandlers(false);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred while configuring the file handler", e);
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
