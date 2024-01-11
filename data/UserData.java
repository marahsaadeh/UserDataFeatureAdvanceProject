package edu.najah.cap.data;
import java.util.HashSet;
import java.util.Set;

import java.util.logging.Logger;



//Singleton
public class UserData implements Database {
    private static final Logger logger = LoggerSetup.getLogger();

     //for delete
     private Set<String> deletedUserNames = new HashSet<>();
 

    // connection is open for each user
    private static UserData instance = new UserData();

    //Overriding of Database class
    public void connect() {
        System.out.println("Connecting!!");
    }
    //Overriding of Database class
    public  void disconnect() {
        System.out.println("Disconnected");
    }

    // implementation users the Singleton to ensure a single connection for each user
    public static UserData getInstance() {
        //to avoid problems if happen Multithreading
        if (instance == null) {
            synchronized (UserData.class) {

                if (instance == null) {
                    instance = new UserData();
                }

            }
        }
        return instance;
    }
    public MergeObject getMergeObjectForUser(String userName, FakeDataBase DB) {
        try {
            // Log the start of fetching the merge object for the user
            logger.info("Fetching MergeObject for user: " + userName);

            // Get the merge object from the FakeDataBase
            MergeObject mergeObject = FakeDataBase.UserInDatabase(userName);

            // Log the completion of fetching the merge object
            if (mergeObject != null) {
                logger.info("Fetch completed for user: " + userName);
            } else {
                logger.warning("User not found in the database: " + userName);
            }

            return mergeObject;
        } catch (Exception e) {
            // Handle exceptions and log them
            logger.warning("Exception occurred while fetching MergeObject for user: " + userName);
            logger.warning(e.getMessage());
            return null;
        }
    }
    //Registering the names of users who have completely deleted their accounts so 
    //that they cannot create a new account with the same username
    public void deleteUser(String userName) {
        deletedUserNames.add(userName);
    }
   //It is not possible to create a new account with the same name in the future
    public boolean canCreateUser(String userName) {
        return !deletedUserNames.contains(userName);
    }
    
}

