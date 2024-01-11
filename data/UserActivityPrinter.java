package edu.najah.cap.data;

import edu.najah.cap.activity.UserActivity;
import java.util.logging.Level;

import java.util.logging.Logger;

public class UserActivityPrinter implements Printer {
    private static final Logger logger = LoggerSetup.getLogger();

    @Override
    public void print(MergeObject mergeObject) {
        logger.log(Level.INFO, "Starting to print user activity data");

        System.out.println("Your activity data:");
        for (UserActivity userActivity : mergeObject.getUserActivities()) {
            System.out.println("___________________________");
            System.out.println("Activity Type: " + userActivity.getActivityType());
            System.out.println("Activity Date: " + userActivity.getActivityDate());

            logger.log(Level.FINE, "Printed activity: ID=" + userActivity.getId() + ", Type=" + userActivity.getActivityType());
        }

        logger.log(Level.INFO, "User activity data printing completed");
    }
}

