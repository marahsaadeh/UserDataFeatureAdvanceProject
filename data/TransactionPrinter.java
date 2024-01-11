package edu.najah.cap.data;

import java.util.logging.Logger;

import edu.najah.cap.payment.Transaction;

import java.util.logging.Level;

public class TransactionPrinter implements Printer {
    private static final Logger logger = LoggerSetup.getLogger(); 

    @Override
    public void print(MergeObject mergeObject) {
        // Log the start of printing transactions
        logger.log(Level.INFO, "Starting to print user transactions data");

        System.out.println("Your payments data:");
        for (Transaction transaction : mergeObject.getTransactions()) {
            System.out.println("___________________________");
            System.out.println("Amount: " + transaction.getAmount());
            System.out.println("Description: " + transaction.getDescription());

            logger.log(Level.FINE, "Printed transaction: ID=" + transaction.getId() + ", Amount=" + transaction.getAmount());
        }

        // Log the completion of the printing process
        logger.log(Level.INFO, "User transactions data printing completed");
    }
}
