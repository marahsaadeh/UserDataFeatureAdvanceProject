package edu.najah.cap.data.export;

import edu.najah.cap.data.MergeObject;


public interface PrintStrategyFactory {
    PrintStrategyCreator createPrintStrategy(MergeObject userData);
}
