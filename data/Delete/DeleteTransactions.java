package edu.najah.cap.data.Delete;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.MergeObject;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.payment.IPayment;
import edu.najah.cap.payment.Transaction;

public class DeleteTransactions implements Deletion {
    private static final Logger logger = LoggerSetup.getLogger(); 
    private IPayment paymentService;

    public DeleteTransactions(IPayment paymentService) {
        this.paymentService = paymentService;
        logger.info("DeleteTransactions initialized with IPayment service.");
    }

    private boolean tryDeleteTransaction(String userName, String transactionId) {
        final int maxRetries = 3;
        final long delayMillis = 1000;

        for (int i = 0; i < maxRetries; i++) {
            try {
                paymentService.removeTransaction(userName, transactionId);
                logger.info("Transaction " + transactionId + " deleted for user: " + userName);
                return true;
            } catch (SystemBusyException e) {
                logger.warning("SystemBusyException encountered. Retrying to delete transaction: " + transactionId + " for user: " + userName);
                System.out.println("Waiting...");
                try {
                    Thread.sleep(delayMillis);
                } catch (InterruptedException ie) {
                    logger.warning("Thread interrupted while waiting to retry deletion of transaction: " + transactionId);
                }
            } catch (Exception e) {
                logger.warning("Error occurred while trying to delete transaction: " + transactionId + " for user: " + userName);
                logger.warning(e.toString());
                return false;
            }
        }
        logger.warning("Failed to delete transaction: " + transactionId + " for user: " + userName + " after max retries.");
        return false; 
    }

    @Override
    public boolean removeData(String userName, MergeObject mergeObject) {
        boolean isRemovedSuccessfully = false;
        try {
            List<Transaction> transactions = paymentService.getTransactions(userName);
            logger.info("Retrieved transactions for user: " + userName + ". Number of transactions: " + transactions.size());

            for (Transaction transaction : new ArrayList<>(transactions)) {
                if (!tryDeleteTransaction(userName, transaction.getId())) {
                    logger.warning("Failed to delete transaction: " + transaction.getId() + " for user: " + userName);
                }
            }

            transactions = paymentService.getTransactions(userName);
            logger.info("Post deletion, remaining transactions for user: " + userName + ": " + transactions.size());

            mergeObject.setTransactions(transactions);

            if (transactions.isEmpty()) {
                logger.info("All transactions successfully deleted for user: " + userName);
                isRemovedSuccessfully = true;
            } else {
                logger.info("Some transactions were not deleted for user: " + userName);
            }
            System.out.println("Deletion process completed successfully");
        } catch (Exception e) {
            logger.warning("Error occurred while removing transactions for user: " + userName);
            logger.warning(e.getMessage());
        }
        return isRemovedSuccessfully;
    }
}
