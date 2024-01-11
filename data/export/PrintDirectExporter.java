package edu.najah.cap.data.export;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import edu.najah.cap.data.MergeObject;

public interface PrintDirectExporter {
    void printPdf(Document document, MergeObject user) throws DocumentException;
    String getDataType();
}
