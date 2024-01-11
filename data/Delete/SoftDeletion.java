package edu.najah.cap.data.Delete;


import java.util.logging.Logger;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.data.FakeDataBase;
import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.MergeObject;
import edu.najah.cap.iam.UserType;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.posts.IPostService;

public class SoftDeletion implements DeletionType {
 private static final Logger logger = LoggerSetup.getLogger(); 
    private IPayment paymentService;
    private IUserActivityService userActivityService;
    private IPostService postService;

   
    public SoftDeletion(IPayment paymentService, IUserActivityService userActivityService,
                        IPostService postService) {
        this.paymentService = paymentService;
        this.userActivityService = userActivityService;
        this.postService = postService;

    }

    @Override
    public void executeDeletion(String userName) {
        try {
         
           MergeObject user=FakeDataBase.UserInDatabase(userName);
           UserType userType =user.getUserProfile().getUserType();
           logger.info("Executing soft deletion for user: " + userName);
           
           
            SoftDeletionOptionsFactory optionsFactory = new SoftDeletionOptionsFactory();
            DeletionActionFactory actionFactory = new DeletionActionFactory( userActivityService,paymentService, postService);
            Deletion deletionAction = optionsFactory.getSoftDeletionAction(userType, actionFactory);

            if (deletionAction != null) {
               deletionAction.removeData( userName,  user);
            }
        } catch (Exception e) {
            logger.warning("Exception occurred during soft deletion for user: " + userName);
            logger.warning(e.getMessage());
        }
    }
}

