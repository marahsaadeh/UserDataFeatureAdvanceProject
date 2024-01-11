package edu.najah.cap.data;

import edu.najah.cap.iam.UserProfile;
import java.util.logging.Logger;
import java.util.logging.Level;

public class UserProfilePrinter implements Printer  {
    private static final Logger logger = LoggerSetup.getLogger();

    @Override

    public void print(MergeObject mergeObject) {
        UserProfile userProfile = mergeObject.getUserProfile();
        //The printing process started successfully
        logger.log(Level.INFO, "Printing user profile data for: " + userProfile.getUserName());
        System.out.println("Your profile data :");
        System.out.println("  FirstName: " + userProfile.getFirstName());
        System.out.println("  LastName: " + userProfile.getLastName());
        System.out.println("  PhoneNumber: " + userProfile.getPhoneNumber());
        System.out.println("  Email: " + userProfile.getEmail());
        System.out.println("  UserName: " + userProfile.getUserName());
        System.out.println("  Password: " + userProfile.getPassword());
        System.out.println("  Role: " + userProfile.getRole());
        System.out.println("  Department: " + userProfile.getDepartment());
        System.out.println("  Organization: " + userProfile.getOrganization());
        System.out.println("  Country: " + userProfile.getCountry());
        System.out.println("  City: " + userProfile.getCity());
        System.out.println("  Street: " + userProfile.getStreet());
        System.out.println("  PostalCode: " + userProfile.getPostalCode());
        System.out.println("  Building: " + userProfile.getBuilding());
        System.out.println("  UserType: " + userProfile.getUserType());
       // The printing process completed successfully
       logger.log(Level.INFO, "User profile data printed for: " + userProfile.getUserName());

        System.out.println("--------------------------");
    }
    
}
