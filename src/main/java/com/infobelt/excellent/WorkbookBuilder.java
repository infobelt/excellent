package com.infobelt.excellent;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A builder for a workbook
 */
public class WorkbookBuilder {

    private List<WorksheetBuilder> sheets = new ArrayList<>();

    public WorksheetBuilder sheet() {
        WorksheetBuilder newSheet = new WorksheetBuilder(this);
        sheets.add(newSheet);
        return newSheet;
    }

    public WorkbookReference build() {
        Workbook wb = new XSSFWorkbook();
        sheets.forEach(s -> s.build(wb));
        return new WorkbookReference(wb);
    }

    public WorkbookReference build(Collection<IColumnMetadata> columnDefs) {
        Workbook wb = new XSSFWorkbook();
        sheets.forEach(s -> s.build(wb, columnDefs));
        return new WorkbookReference(wb);
    }

}
