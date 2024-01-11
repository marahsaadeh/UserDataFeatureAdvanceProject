package edu.najah.cap.data.export;


import java.util.List;
import java.util.logging.Logger;

import java.util.logging.Level;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;

import edu.najah.cap.data.LoggerSetup;
import edu.najah.cap.data.MergeObject;
import edu.najah.cap.posts.Post;

public class ExportPostsToPdf implements PrintDirectExporter {

    private static final Logger logger = LoggerSetup.getLogger(); 


    @Override
    public void printPdf(Document document, MergeObject user) {
        try {
            List<Post> posts = user.getPosts();
            logger.log(Level.INFO, "Starting PDF export of posts for user: " + user.getUserProfile().getUserName());

            for (Post post : posts) {
                String postDetails = "Post Title: " + post.getTitle() +
                "\nID: " + post.getId() +
                "\nBody: " + post.getBody() +
                "\nAuthor: " + post.getAuthor() +
                "\nDate: " + post.getDate();

      
            document.add(new Paragraph(postDetails));

            document.add(Chunk.NEWLINE);
            document.add(new LineSeparator());
                logger.log(Level.FINE, "Added post to PDF: " + post.getTitle());
            }

            logger.log(Level.INFO, "Completed PDF export of posts");
        } catch (DocumentException e) {
            logger.log(Level.SEVERE, "Error occurred while exporting posts to PDF", e);
            // Additional exception handling logic can be added here
        }
    }


    @Override
    public String getDataType() {
        return "User Posts Data _ ";
    }
  
}

