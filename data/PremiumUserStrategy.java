package edu.najah.cap.data;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import edu.najah.cap.activity.IUserActivityService;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.payment.IPayment;

public class PremiumUserStrategy implements UserTypeStrategy {
    private IPayment paymentService;
    private IUserActivityService userActivityService;
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


    public PremiumUserStrategy(IPayment paymentService, IUserActivityService userActivityService) {
        this.paymentService = paymentService;
        this.userActivityService = userActivityService;
    }

    @Override
    public void collectUserData(MergeObject mergeObject, String userName) {
        try {
            // Log the start of data collection
            logger.info("Collecting data for Premium User: " + userName);

            // Collect and set Transactions
            mergeObject.setTransactions(paymentService.getTransactions(userName));

            // Collect and set User Activities
            mergeObject.setUserActivities(userActivityService.getUserActivity(userName));

            // Log the completion of data collection
            logger.info("Data collection completed for Premium User: " + userName);
        } catch (NotFoundException | SystemBusyException | BadRequestException e) {
            // Handle exceptions and log them
            logger.warning("Exception occurred while collecting data for Premium User: " + userName);
            logger.warning(e.getMessage());
        }
    }
}