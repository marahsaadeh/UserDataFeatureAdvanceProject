package edu.najah.cap.data.export;

import java.util.logging.Logger;

import java.util.logging.Level;

import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.MergeObject;
import edu.najah.cap.iam.UserType;

public class PrintStrategyFactoryImpl implements PrintStrategyFactory {

     private static final Logger logger = LoggerSetup.getLogger(); 

    @Override
    public PrintStrategyCreator createPrintStrategy(MergeObject userData) {
        UserType userType=userData.getUserProfile().getUserType();
        try {
            logger.log(Level.INFO, "Creating print strategy for user type: " + userType);

            if (userType.equals(UserType.NEW_USER)) {
                return new NawPrintStrategyCreator(userData);
            } else if (userType.equals(UserType.REGULAR_USER)) {
                return new RegularPrintStrategyCreator(userData);
            } else if (userType.equals(UserType.PREMIUM_USER)) {
                return new PremiumPrintStrategyCreator(userData);
            } else {
                throw new IllegalArgumentException("Invalid user type: " + userType);
            }
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Error in creating print strategy: " + e.getMessage(), e);
            throw e;  
        }
    }
}
