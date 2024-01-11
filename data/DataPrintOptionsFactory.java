package edu.najah.cap.data;

import java.util.Scanner;
import java.util.logging.Logger;

import edu.najah.cap.iam.UserType;

public class DataPrintOptionsFactory {
    private Scanner scanner;
    private static final Logger logger = LoggerSetup.getLogger();

    public DataPrintOptionsFactory(Scanner scanner) {
        this.scanner = scanner;
    }

    public Printer getPrinter(UserType userType, PrinterFactory printerFactory) {
        logger.info("Displaying print options for user type: " + userType);

        int maxOption = 0;
        System.out.println("Choose the data number you want to display:");
        if (userType == UserType.NEW_USER) {
            System.out.println("1. Your profile data");
            System.out.println("2. Your post data");
            maxOption = 2;
        } else if (userType == UserType.REGULAR_USER) {
            System.out.println("1. Your profile data");
            System.out.println("2. Your post data");
            System.out.println("3. Your activity data");
            maxOption = 3;
        } else if (userType == UserType.PREMIUM_USER) {
            System.out.println("1. Your profile data");
            System.out.println("2. Your post data");
            System.out.println("3. Your activity data");
            System.out.println("4. Your payment data");
            maxOption = 4;
        }

        System.out.print("Enter your choice: ");
        int dataChoice = scanner.nextInt();

        if (dataChoice < 1 || dataChoice > maxOption) {
            logger.warning("Invalid print type selected: " + dataChoice);
            throw new IllegalArgumentException("Invalid print type selected.");
        }
/*If the user enters a value that is not present in the available options Warning 
to logger with a message indicating that a printer of this type cannot be created */
        Printer printer = printerFactory.createPrinter(dataChoice);
        if (printer == null) {
            logger.warning("Printer could not be created for type: " + dataChoice);
            throw new IllegalArgumentException("Printer could not be created for type: " + dataChoice);
        }

        logger.info("Printer created for type: " + dataChoice);
        
        return printer;
    }
}
