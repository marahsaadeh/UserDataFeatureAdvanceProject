package edu.najah.cap.data.export;

import java.util.ArrayList;

import java.util.List;
import java.util.logging.Logger;


import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.MergeObject;

import java.util.logging.Level;

public class PremiumPrintStrategyCreator implements PrintStrategyCreator {

    private static final Logger logger = LoggerSetup.getLogger(); 
    private MergeObject userData; 

    public PremiumPrintStrategyCreator(MergeObject userData) {
        this.userData = userData;
    }

    @Override
    public List<PrintDirectExporter> createPrintStrategies() {
        logger.log(Level.INFO, "Creating premium print strategies for document export");

        List<PrintDirectExporter> strategies = new ArrayList<>();
        strategies.add(new ExportUserProfileDataToPdf());


        if (!userData.getPosts().isEmpty()) {
            strategies.add(new ExportPostsToPdf());
        }


        if (!userData.getUserActivities().isEmpty()) {
            strategies.add(new ExportActivitiesToPdf());
        }

  
        if (!userData.getTransactions().isEmpty()) {
            strategies.add(new ExportPaymentsToPdf());
        }

        logger.log(Level.INFO, "Successfully created " + strategies.size() + " premium print strategies");

        return strategies;
    }
}
