package com.infobelt.excellent;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A builder for a work sheet
 */
public class WorksheetBuilder {

    private final WorkbookBuilder workbookBuilder;
    private Collection objects;
    private String title = "export";
    private boolean includeHeader = true;
    private int startRow = 0;
    private int startCell = 0;
    private boolean showNullAsEmptyString = true;

    public WorksheetBuilder(WorkbookBuilder workbookBuilder) {
        this.workbookBuilder = workbookBuilder;
    }

    public WorksheetBuilder startCell(int startCell) {
        this.startCell = startCell;
        return this;
    }

    public WorksheetBuilder startRow(int startRow) {
        this.startRow = startRow;
        return this;
    }

    public WorksheetBuilder includeHeader(boolean includeHeader) {
        this.includeHeader = includeHeader;
        return this;
    }

    public WorksheetBuilder showNullAsEmptyString(boolean showNullAsEmptyString) {
        this.showNullAsEmptyString = showNullAsEmptyString;
        return this;
    }

    public WorksheetBuilder from(Collection objects) {
        this.objects = objects;
        return this;
    }

    public WorksheetBuilder from(Object object) {
        objects = Collections.singletonList(object);
        return this;
    }

    public WorksheetBuilder title(String title) {
        this.title = title;
        return this;
    }

    public WorkbookBuilder endSheet() {
        return workbookBuilder;
    }

    void build(Workbook wb) {
        ColumnsMetadata columnsMetadata = new ColumnsMetadata(objects);
        columnsMetadata.setColumns(true);
        writeWooksheet(wb, columnsMetadata);
    }

    void build(Workbook wb, Collection<IColumnMetadata> columnDefs) {
        ColumnsMetadata columnsMetadata = new ColumnsMetadata(objects);
        columnsMetadata.setColumns(columnDefs);
        writeWooksheet(wb, columnsMetadata);
    }

    void writeWooksheet (Workbook wb, ColumnsMetadata columnsMetadata ) {
        Sheet sheet = wb.createSheet(WorkbookUtil.createSafeSheetName(title));
        CreationHelper createHelper = wb.getCreationHelper();

        int currentRow = startRow;
        if (includeHeader) {
            writeHeader(sheet, createHelper, columnsMetadata);
            currentRow++;
        }

        for (Object obj : objects) {
            writeRow(sheet, createHelper, columnsMetadata, currentRow, obj);
            currentRow++;
        }
    }

    private void writeRow(Sheet sheet, CreationHelper createHelper, ColumnsMetadata columnsMetadata, int currentRow, Object obj) {
        Row row = sheet.createRow(currentRow);
        AtomicInteger startCell = new AtomicInteger(this.startCell);
        columnsMetadata.getColumns().forEach(c -> {
            if (!c.isIgnored()) {
                Cell cell = row.createCell(startCell.getAndIncrement());
                setCellValue(cell, c, obj);
            }
        });
    }

    private void setCellValue(Cell cell, IColumnMetadata c, Object obj) {
        Object rawCellVal = c.getValue(obj);
        String columnType = "";
        if (c instanceof FieldColumnMetadata) {
            columnType = ((FieldColumnMetadata) c).getField().getType().getSimpleName();
        }
        if (rawCellVal != null && !String.valueOf(rawCellVal).equals("")) {
            rawCellVal = rawCellVal.toString().replaceAll("\\s{2,}", " ").trim();
            rawCellVal = rawCellVal.toString().replaceAll("&ensp;", " ");
            if (rawCellVal.toString().length() > 32767) {
                cell.setCellValue(rawCellVal.toString().substring(0, 32763) + "...");
            }
            else {
                if (columnType.equals("ZonedDateTime")) {
                    ZonedDateTime parsedVal;
                    parsedVal = ZonedDateTime.parse(String.valueOf(rawCellVal));
                    cell.setCellValue(parsedVal.format(DateTimeFormatter.ofPattern("MM/dd/YYYY, HH:mm:ss")));
                } else {
                    cell.setCellValue(String.valueOf(rawCellVal));
                }
            }
        } else {
            cell.setCellValue("");
        }
    }

    private void writeHeader(Sheet sheet, CreationHelper createHelper, ColumnsMetadata columnsMetadata) {
        Row row = sheet.createRow(startRow);
        AtomicInteger startCell = new AtomicInteger(this.startCell);
        columnsMetadata.getColumns().forEach(c -> {
            if (!c.isIgnored()) {
                Cell cell = row.createCell(startCell.getAndIncrement());
                cell.setCellValue(c.getHeader());
            }
        });
    }
}
