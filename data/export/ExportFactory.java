package edu.najah.cap.data.export;

import java.util.logging.Logger;


import edu.najah.cap.data.LoggerSetup;

import java.util.logging.Level;

public class ExportFactory {

    private static final Logger logger = LoggerSetup.getLogger(); 

    public DataExporter createExport(String type) {
        logger.log(Level.INFO, "Creating data exporter of type: " + type);

        if ("PDF".equals(type)) {
            logger.log(Level.INFO, "PDF exporter selected");
            return new DirectExporter();
        } else if ("ZIP".equals(type)) {
            logger.log(Level.INFO, "ZIP exporter selected");
            DataExporter wrappedExporter = new DirectExporter();
            
          return new ZipExporter(wrappedExporter);
        } else if ("GoogleDrive".equals(type)) {
            logger.log(Level.INFO, "Google Drive exporter selected");
            DataExporter zipExporter = new ZipExporter(new DirectExporter());
            return new GoogleDriveService(zipExporter); 
        } else {
            logger.log(Level.SEVERE, "Unsupported export type: " + type);
            throw new IllegalArgumentException("Unsupported export type: " + type);
        }
    }
}
