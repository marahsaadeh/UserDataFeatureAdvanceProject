package edu.najah.cap.data;

public class ConcretePrinterFactory implements PrinterFactory {
    
    @Override
    public Printer createPrinter(int choice) {
        switch (choice) {
            case 1:
                return new UserProfilePrinter();
            case 2:
                return new PostPrinter();
            case 3:
                return new UserActivityPrinter();
            case 4:
                return new TransactionPrinter();
            default:
                throw new IllegalArgumentException("Invalid Print Type");
        }
    }
}
