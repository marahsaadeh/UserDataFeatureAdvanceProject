package edu.najah.cap.data.Delete;


import java.util.logging.Logger;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.iam.IUserService;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.posts.IPostService;

public class DeletionTypeFactory {
    private static final Logger logger = LoggerSetup.getLogger(); 

    public DeletionType getType(String deletionType, IPayment paymentService,
                                IUserActivityService userActivityService,
                                IPostService postService, IUserService userService) {
        try {
            if ("hard".equalsIgnoreCase(deletionType)) {
                return new HardDeletion();
            } else if ("soft".equalsIgnoreCase(deletionType)) {
                return new SoftDeletion(paymentService, userActivityService, postService);
            } else {
                throw new IllegalArgumentException("Invalid deletion type: " + deletionType);
            }
        } catch (Exception e) {
            logger.warning("Exception occurred while creating DeletionType object for deletion type: " + deletionType);
            logger.warning(e.getMessage());
            throw e; 
        }
    }
}
