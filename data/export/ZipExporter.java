package edu.najah.cap.data.export;



import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.MergeObject;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.UserType;
import java.io.File;
import java.nio.file.Files;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.logging.Logger;

import java.util.logging.Level;
//Decoreter 1
public class ZipExporter extends DataExporterDecorator{
    
    private static final Logger logger = LoggerSetup.getLogger(); 

    public ZipExporter(DataExporter exporter) {
        super(exporter);
    }
    @Override
    public String exportData(MergeObject user) throws SystemBusyException, NotFoundException, BadRequestException, IOException {
        String result = wrappedExporter.exportData(user);

            String userName = user.getUserProfile().getUserName().replaceAll("\\s+", "_");
            String zipFilePath = "out/" + userName + "_exported_data.zip"; 
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            boolean zipSuccess = false;

 
        try {
            // Check if the user exists before exporting data
            if (!userExists(userName)) {
                throw new NotFoundException("User does not exist: " + userName);
            }
            
try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            File directory = new File("out");
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".pdf") && name.contains(userName));
                 if (files != null) {
                for (File file : files) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zipOut.putNextEntry(zipEntry);
                    Files.copy(file.toPath(), zipOut);
                    zipOut.closeEntry();
                    file.delete();
                    //regstration opration in export_log.txt
                    String logMessage = "PDF file added to ZIP: " + file.getName();
                    writeToLogFile(logMessage, "export_log.txt");
                }
                zipSuccess = true; 
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException in ZipExporter", e);
        }
        
        // Only log the creation of the ZIP file if successful
        if (zipSuccess) {
            writeToLogFile("ZIP file created: " + zipFilePath, "export_log.txt");
        }
    } catch (NotFoundException e) {
        logger.log(Level.SEVERE, "User not found in exportData method", e);
    } catch (Exception e) {
        logger.log(Level.SEVERE, "Exception in exportData method", e);
    }
    
    // Log the result of the export
    writeToLogFile("Exported " + timestamp + " for user " + userName, "export_log.txt");

    return zipFilePath;
}

    public void writeToLogFile(String message, String filePath) throws IOException {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
            out.println(message);
        }
    }
    
    // Add this method to check if the user exists
    public boolean userExists(String userName) {
        // Implement logic to check if the user exists in your system
        // Return true if the user exists, false otherwise
        return true; // Replace with actual logic
    }

}


