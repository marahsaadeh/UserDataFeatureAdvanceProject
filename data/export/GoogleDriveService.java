package edu.najah.cap.data.export;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.client.util.Lists;

import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.MergeObject;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GoogleDriveService extends DataExporterDecorator {
    private static final Logger logger = LoggerSetup.getLogger();
    private static final String APPLICATION_NAME = "AdvanceUserDataFeature";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "analog-receiver-396316-eabb5b86e8fd.json"; 
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    
    public GoogleDriveService(DataExporter exporter) {
        super(exporter);
    }
    
    
    public static GoogleCredentials getCredentials() throws IOException {
        InputStream in = GoogleDriveService.class.getResourceAsStream("/analog-receiver-396316-eabb5b86e8fd.json");
        if (in == null) {
            throw new FileNotFoundException("Resource not found: /analog-receiver-396316-eabb5b86e8fd.json");
        }
        return GoogleCredentials.fromStream(in).createScoped(SCOPES);
    } 

    public Drive getDriveService() throws IOException, GeneralSecurityException {
        GoogleCredentials credential = getCredentials();
        return new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, new HttpCredentialsAdapter(credential))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    
    @Override
    public String exportData(MergeObject user) throws SystemBusyException, NotFoundException, BadRequestException, IOException {
  
        String exportedFilePath = wrappedExporter.exportData(user);

        try {
            /*java.io.File fileToUpload = new java.io.File(localFilePath);
            String fileName = fileToUpload.getName();
            return uploadFileToDrive(fileName, fileToUpload);*/
            return uploadFile(exportedFilePath);
        } catch (IOException | GeneralSecurityException e) {
            logger.log(Level.SEVERE, "An error occurred while uploading to Google Drive", e);
                throw new IOException("Failed to upload file to Google Drive", e);
        }
    }
   //uploadFile (): Drive obj  to create a new file on Google Drive.
    public String uploadFile(String filePath) throws IOException, GeneralSecurityException {
  //public String uploadFileToDrive(String fileName, java.io.File filePath) throws IOException, GeneralSecurityException 
        System.out.println("Enter the function");
        logger.log(Level.INFO, "Starting upload to Google Drive for file: " + filePath);
        File file = null;
         try {
        Drive service = getDriveService();
        java.io.File fileToUpload = new java.io.File(filePath);
         if (!fileToUpload.exists()) {
                logger.log(Level.SEVERE, "File not found for uploading: " + filePath);
                throw new FileNotFoundException("File not found: " + filePath);
            }
        File fileMetadata = new File();
        fileMetadata.setName(fileToUpload.getName());
        FileContent mediaContent = new FileContent("application/zip", fileToUpload);
        file = service.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        logger.log(Level.INFO, "File uploaded successfully to Google Drive with ID: " + file.getId());

        } catch (IOException | GeneralSecurityException e){
            logger.log(Level.SEVERE, "An error occurred during file upload to Google Drive", e);
            System.out.println("An error occurred: " + e);
            throw e; 
        }
         return file != null ? "Google Drive File ID: " + file.getId() : null;
    }
 
    
}