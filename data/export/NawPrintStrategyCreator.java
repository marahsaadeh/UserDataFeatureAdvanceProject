package edu.najah.cap.data.export;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.MergeObject;

import java.util.logging.Level;
public class NawPrintStrategyCreator implements PrintStrategyCreator {

    private static final Logger logger = LoggerSetup.getLogger(); 

    private MergeObject userMergeObject;

    public NawPrintStrategyCreator(MergeObject userMergeObject) {
        this.userMergeObject = userMergeObject;
    }

    @Override
    public List<PrintDirectExporter> createPrintStrategies() {
        logger.log(Level.INFO, "Creating print strategies for document export");

        List<PrintDirectExporter> strategies = new ArrayList<>();
        strategies.add(new ExportUserProfileDataToPdf());


        if (!userMergeObject.getPosts().isEmpty()) {
            strategies.add(new ExportPostsToPdf());
        }

        logger.log(Level.INFO, "Successfully created " + strategies.size() + " print strategies");

        return strategies;
    }
}
