package edu.najah.cap.data.export;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.MergeObject;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
import edu.najah.cap.iam.UserType;

import java.util.logging.Level;
import java.util.logging.Logger;



import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.io.File;
//basic class
//PDF Exporter
public class DirectExporter implements DataExporter {
    private static final Logger logger = LoggerSetup.getLogger(); 
    Document document;
 
    @Override
   public String exportData(MergeObject user) throws SystemBusyException, NotFoundException, BadRequestException, IOException {
            
    String userName = user.getUserProfile().getUserName();

      //PDF
    byte[] pdfContent = null;
    try{
    // Export User Profile Data
    // Check if the user exists before exporting data
     if (!userExists(userName)) {
        throw new NotFoundException("User does not exist: " + userName);
    }
    PrintStrategyFactory factory = new PrintStrategyFactoryImpl();
                                       //new RegularPrintStrategyCreator()
    PrintStrategyCreator strategyCreator = factory.createPrintStrategy(user);
                                        //array[export data to pdf "add.doc"]
    List<PrintDirectExporter> exporters = strategyCreator.createPrintStrategies();


    for (PrintDirectExporter dataAddedDocument : exporters) {

   // dataAddedDocument : do add to document

   //pdfContent =pdf file
    try {
            pdfContent = createPdf(document,user, dataAddedDocument);
            String pdfFileName = userName.replaceAll("\\s+", "_") + "_" + dataAddedDocument.getDataType() + ".pdf";
            savePdf(pdfFileName, pdfContent);
            //regstration opration in export_log.txt
             String logMessage = "PDF file created: " + pdfFileName;
            writeToLogFile(logMessage, "export_log.txt");
        } catch (DocumentException e) {
            logger.log(Level.SEVERE, "Error creating PDF for data type: " + dataAddedDocument.getDataType(), e);
        } 
               
             }    
    logger.log(Level.INFO, "Data export completed for user: " + userName);
        } catch ( IOException e) {
            logger.log(Level.SEVERE, "Error during data export", e);
        }finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
return "PDF files created for " + userName; 

   }
    public byte[] createPdf( Document document,MergeObject user, PrintDirectExporter dataAddedDocument) throws SystemBusyException, NotFoundException, BadRequestException, DocumentException, IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document = new Document();
        //PdfWriter.getInstance(document, new FileOutputStream("UserName_" + userName.replaceAll("\\s+", "_") + ".pdf"));
        PdfWriter.getInstance(document, byteArrayOutputStream);
        try {
            document.open();
            dataAddedDocument.printPdf(document, user);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    

    public boolean userExists(String userName) {
        return true;
    }

    public void savePdf(String pdfFileName, byte[] pdfContent) throws IOException {
    String directoryPath = "out"; 
    File directory = new File(directoryPath);
    if (!directory.exists()) {
        directory.mkdir(); 
    }
    
    File pdfFile = new File(directoryPath, pdfFileName);
    try (FileOutputStream outputStream = new FileOutputStream(pdfFile)) {
        outputStream.write(pdfContent);
    }
}
public void writeToLogFile(String message, String filePath) throws IOException {
    try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
        out.println(message);
    }
}

}
