package edu.najah.cap.data.export;

import java.io.IOException;

import edu.najah.cap.data.MergeObject;
import edu.najah.cap.exceptions.BadRequestException;
import edu.najah.cap.exceptions.NotFoundException;
import edu.najah.cap.exceptions.SystemBusyException;
//The interface from which the base class and decorations are implemented
public interface DataExporter {
    String exportData(MergeObject user) throws SystemBusyException, NotFoundException, BadRequestException  , IOException ;

}


