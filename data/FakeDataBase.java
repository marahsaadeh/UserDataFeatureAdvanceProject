package edu.najah.cap.data;
import edu.najah.cap.iam.UserType;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;
import java.util.*;
public class FakeDataBase implements Database{
    private static final Logger logger = LoggerSetup.getLogger();

static {
    try {
        FileHandler fileHandler = new FileHandler("output.log");
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        logger.setUseParentHandlers(false);
        logger.addHandler(fileHandler);
    } catch (IOException e) {
        logger.log(Level.SEVERE, "An error occurred while configuring the file handler", e);
    }
}


    private DataFacade dataFacade;
    //This to follow the put values in map & avoid Duplicate User name
    private Set<String> addedUserNames;
      // |  Type   |  Obj,Obj,Obj,...  |
    public static Map<UserType, List<MergeObject>> DB;

    public FakeDataBase(DataFacade dataFacade) {
        this.dataFacade = dataFacade;
        this.addedUserNames = new HashSet<>();
         DB = new HashMap<>();

    }


    public void initializeFakeData() {
        if (dataFacade == null) {
            logger.warning("dataFacade is null!");
            return;
        }
        
        for (int i = 0; i < 100; i++) {
            try {
                String userName = "user" + i;
                dataFacade.getMergedData(userName);
                 //|  Type   |  Obj  |
                Map<UserType, MergeObject> mergeObjectUser = dataFacade.getMergeObjectMap();
                // mergeMap opration
                mergeValues(DB, mergeObjectUser, addedUserNames);
            } catch (Exception e) {
                logger.warning("Exception occurred while initializing fake data.");
                logger.warning(e.getMessage());
            }
        }

        if (DB == null || DB.isEmpty()) {
            logger.warning("DB is null or empty.");
            return;
        }

      //  printAllUserData();
    }

    public boolean isUserInDatabase(String userName) {
        return isUserInDatabase(userName, DB);
    }

    private boolean isUserInDatabase(String userName, Map<UserType, List<MergeObject>> DB) {
        for (List<MergeObject> mergeObjects : DB.values()) {
            for (MergeObject mergeObject : mergeObjects) {
                if (mergeObject.getUserProfile().getUserName().equals(userName)) {
                    return true;
                }
            }
        }
        return false;
    }

public static MergeObject UserInDatabase(String userName) {
        for (List<MergeObject> mergeObjects : DB.values()) {
            for (MergeObject mergeObject : mergeObjects) {
                if (mergeObject.getUserProfile().getUserName().equals(userName)) {
                    return mergeObject;
                }
            }
        }
        return null;
    }


    public void mergeValues(Map<UserType, List<MergeObject>> targetMap,
     Map<UserType, MergeObject> sourceMap, Set<String> addedUserNames) {
        for (Map.Entry<UserType, MergeObject> entry : sourceMap.entrySet()) {
            UserType key = entry.getKey();
            MergeObject value = entry.getValue();

            if (!addedUserNames.contains(value.getUserProfile().getUserName())) {

                addedUserNames.add(value.getUserProfile().getUserName());


                if (targetMap.containsKey(key)) {
                    targetMap.get(key).add(value);
                } else {
                    List<MergeObject> values = new ArrayList<>();
                    values.add(value);
                    targetMap.put(key, values);
                }
            }
        }
    }
//To Print DB
    public void printAllUserData() {
        if (DB == null || DB.isEmpty()) {
            System.out.println("DB is null or empty.");
            return;
        }
     System.out.println("----------------------------------------------------------------");
        System.out.printf("|%-20s|%-20s|%-20s|%n", "New User", "Regular User ", "Premium User");
    System.out.println("|--------------------|--------------------|--------------------|");

    // Find the maximum size among the lists
    int maxSize = 0;
    for (List<MergeObject> mergeObjects : DB.values()) {
        maxSize = Math.max(maxSize, mergeObjects.size());
    }

    // Print rows
    for (int i = 0; i < maxSize; i++) {
        System.out.print("|");

        for (List<MergeObject> mergeObjects : DB.values()) {
            if (i < mergeObjects.size()) {
                String userName = mergeObjects.get(i).getUserProfile().getUserName();
                System.out.printf("%-20s|", userName);
            } else {
                System.out.printf("%-20s|", "");
            }
        }
        System.out.println();
    }
    
    System.out.println(" ____________________________");
    System.out.println("| User Type     | User Count |");
    System.out.println("|---------------|------------|");

    for (Map.Entry<UserType, List<MergeObject>> entry : DB.entrySet()) {
        UserType userType = entry.getKey();
        List<MergeObject> users = entry.getValue();
        
        if (users != null && !users.isEmpty()) {
            String row = String.format("| %-13s | %-10d |", userType, users.size());
            System.out.println(row);
        }
    }
     System.out.println("-----------------------------");
    }
    //delete
public static void deleteUser(String userName) {
    for (List<MergeObject> mergeObjects : DB.values()) {
        mergeObjects.removeIf(mergeObject -> mergeObject.getUserProfile().getUserName().equals(userName));
    }
}

   //Overriding of Database class
     public void connect(){
         logger.info("Connecting to FakeDatabase!!");
    }
    //Overriding of Database class
    public void disconnect() {
        logger.info("Disconnected from the FakeDatabase.");
    }
}


