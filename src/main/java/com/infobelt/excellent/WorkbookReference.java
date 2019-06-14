package com.infobelt.excellent;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class WorkbookReference {

    private final Workbook wb;

    public WorkbookReference(Workbook wb) {
        this.wb = wb;
    }

    public void toFile(String outputFile) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(outputFile);
        wb.write(fileOut);
        fileOut.close();
    }

    public Workbook toWorkbook() {
        return wb;
    }

}
