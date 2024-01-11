package edu.najah.cap.data;

import edu.najah.cap.posts.Post;
import java.util.logging.Logger;
import java.util.logging.Level;

public class PostPrinter implements Printer {
    // Use LoggerSetup class to get the centralized logger instance
    private static final Logger logger = LoggerSetup.getLogger();

    @Override
    public void print(MergeObject mergeObject) {
        // Log the start of printing posts
        logger.log(Level.INFO, "Starting to print user posts data");

        System.out.println("Your posts data:");
        for (Post post : mergeObject.getPosts()) {
            System.out.println("___________________________");
            System.out.println("Title: " + post.getTitle());
            System.out.println("Body: " + post.getBody());
            System.out.println("Author: " + post.getAuthor());
            System.out.println("Date: " + post.getDate());
    
            logger.log(Level.FINE, "Printed post: ID=" + post.getId() + ", Title=" + post.getTitle());
        }

        // Log the completion of the printing process
        logger.log(Level.INFO, "User posts data printing completed");
    }
}
