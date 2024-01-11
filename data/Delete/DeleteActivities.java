package edu.najah.cap.data.Delete;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.MergeObject;
import edu.najah.cap.exceptions.SystemBusyException;

public class DeleteActivities implements Deletion {
    private static final Logger logger = LoggerSetup.getLogger(); 
    private IUserActivityService userActivityService;

    public DeleteActivities(IUserActivityService userActivityService) {
        this.userActivityService = userActivityService;
        logger.info("DeleteActivities initialized with IUserActivityService.");
    }

    private boolean tryDeleteActivity(String userName, String activityId) {
        final int maxRetries = 3; 
        final long delayMillis = 1000; 

        for (int i = 0; i < maxRetries; i++) {
            try {
                userActivityService.removeUserActivity(userName, activityId);
                logger.info("Activity " + activityId + " deleted for user: " + userName);
                return true; 
            } catch (SystemBusyException e) {
                logger.warning("SystemBusyException encountered. Retrying to delete activity: " + activityId + " for user: " + userName);
                System.out.println("Waiting...");
                try {
                    Thread.sleep(delayMillis);
                } catch (InterruptedException ie) {
                    logger.warning("Thread interrupted while waiting to retry deletion of activity: " + activityId);
                }
            } catch (Exception e) {
                logger.warning("Error occurred while trying to delete activity: " + activityId + " for user: " + userName);
                logger.warning(e.toString());
                return false;
            }
        }
        logger.warning("Failed to delete activity: " + activityId + " for user: " + userName + " after max retries.");
        return false; 
    }

    @Override
    public boolean removeData(String userName, MergeObject mergeObject) {
        boolean isRemovedSuccessfully = false;
        try {
            List<UserActivity> activities = userActivityService.getUserActivity(userName);
            logger.info("Retrieved activities for user: " + userName + ". Number of activities: " + activities.size());

            for (UserActivity activity : new ArrayList<>(activities)) {
                if (!tryDeleteActivity(userName, activity.getId())) {
                    logger.warning("Failed to delete activity: " + activity.getId() + " for user: " + userName);
                }
            }

            activities = userActivityService.getUserActivity(userName);
            logger.info("Post deletion, remaining activities for user: " + userName + ": " + activities.size());

            mergeObject.setUserActivities(activities);

            if (activities.isEmpty()) {
                logger.info("All activities successfully deleted for user: " + userName);
                isRemovedSuccessfully = true;
            } else {
                logger.info("Some activities were not deleted for user: " + userName);
            }
            System.out.println("Deletion process completed successfully");
        } catch (Exception e) {
            logger.warning("Error occurred while removing activities for user: " + userName);
            logger.warning(e.getMessage());
            logger.warning("Error: " + e.getMessage());
        }
        return isRemovedSuccessfully;
    }
}
