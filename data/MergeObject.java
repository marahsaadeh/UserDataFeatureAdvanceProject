package edu.najah.cap.data;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;
import java.util.List;
import edu.najah.cap.activity.UserActivity;
import edu.najah.cap.iam.UserProfile;
import edu.najah.cap.payment.Transaction;
import edu.najah.cap.posts.Post;

public class MergeObject {
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


    private UserProfile userProfile;
    private List<Post> posts;
    private List<Transaction> transactions;
    private List<UserActivity> userActivities;
 
    public List<UserActivity> getUserActivities() {
        return userActivities;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
    public List<Post> getPosts() {
       return posts;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setUserActivities(List<UserActivity> userActivities) {
        this.userActivities = userActivities;
    }
    public UserProfile getUserProfile() {
        return userProfile;
    }

   
    public void printData() {
        try {

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
            System.out.println("--------------------------");
            System.out.println("\nPosts:");

            // Print Posts
            logger.info("Posts:");
            if (posts != null) {
                for (Post post : posts) {
                    logger.info("  Post ID: " + post.getId());
                    logger.info("  Title: " + post.getTitle());
                }
            }
        
            // Print Transactions
            logger.info("Transactions:");
            if (transactions != null) {
                for (Transaction transaction : transactions) {
                    logger.info("  Transaction ID: " + transaction.getId());
                    logger.info("  Amount: " + transaction.getAmount());
                }
            }

            // Print User Activities
            logger.info("User Activities:");
            if (userActivities != null) {
                for (UserActivity userActivity : userActivities) {
                    logger.info("  Activity ID: " + userActivity.getId());
                    logger.info("  Activity Type: " + userActivity.getActivityType());
                }
            }

            // Log the completion of data printing
            logger.info("Data printing completed for UserProfile: " + userProfile.getUserName());
        } catch (Exception e) {
            // Handle exceptions and log them
            logger.warning("Exception occurred while printing data for UserProfile: " + userProfile.getUserName());
            logger.warning(e.getMessage());
            e.printStackTrace();
        }
    }
}
