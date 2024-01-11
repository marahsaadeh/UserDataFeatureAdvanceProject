package edu.najah.cap.data.Delete;

import java.util.Scanner;
import java.util.logging.Logger;
import edu.najah.cap.data.FakeDataBase;
import edu.najah.cap.data.LoggerSetup;

public class HardDeletion implements DeletionType {
    private static final Logger logger = LoggerSetup.getLogger(); 

    @Override
    public void executeDeletion(String userName) {
        try {
            logger.info("User has chosen to completely delete their account: " + userName);
System.out.println("This action cannot be undone and you will not be able to create a new account with the same username in the future. Are you sure? (yes/no)");
 Scanner scanner = new Scanner(System.in);     
 String confirmation = scanner.next();       
if ("yes".equalsIgnoreCase(confirmation)) {
            try {
                logger.info("Initiating hard deletion for user: " + userName);

                FakeDataBase.deleteUser(userName);
            

                logger.info("Hard deletion completed for user: " + userName);
            } catch (Exception e) {
      
                logger.severe("Error during hard deletion for user: " + userName);
                logger.severe(e.getMessage());
            }
        } else {
            logger.info("User cancelled the hard deletion process.");
        }
           
        } catch (Exception e) {
            logger.severe("Error during hard deletion for user: " + userName);
            logger.severe(e.getMessage());
        }
    }
}


