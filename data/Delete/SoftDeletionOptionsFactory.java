package edu.najah.cap.data.Delete;

import java.util.Scanner;
import java.util.logging.Logger;



import edu.najah.cap.data.LoggerSetup;

import edu.najah.cap.iam.UserType;

public class SoftDeletionOptionsFactory {
  
    private static final Logger logger = LoggerSetup.getLogger(); 
    
    public Deletion getSoftDeletionAction(UserType userType, DeletionActionFactory actionFactory) {
        logger.info("Displaying soft deletion options for user type: " + userType);
        
        int maxOption = 0;

        System.out.println("Please choose the type of data you want to delete:");
        if (userType == UserType.NEW_USER || userType == UserType.REGULAR_USER || userType == UserType.PREMIUM_USER) {
            System.out.println("1. Delete Posts Data");
            maxOption = 1;
        }
        if (userType == UserType.REGULAR_USER || userType == UserType.PREMIUM_USER) {
            System.out.println("2. Delete Activities Data");
            maxOption = 2;
        }
        if (userType == UserType.PREMIUM_USER) {
            System.out.println("3. Delete Transactions Data");
            maxOption = 3;
        }

        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int dataTypeChoice = scanner.nextInt();

  
        if (dataTypeChoice < 1 || dataTypeChoice > maxOption) {
            String errorMessage = "Invalid deletion option selected: " + dataTypeChoice;
            logger.warning(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

       Deletion deletionAction = actionFactory.getAction(dataTypeChoice);
                
        if (deletionAction == null) {
            String errorMessage = "No action found for deletion type: " + dataTypeChoice;
            logger.warning(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        } else {
            logger.info("Soft deletion action created for type: " + dataTypeChoice);
        }
        
        return deletionAction;
    }
}

