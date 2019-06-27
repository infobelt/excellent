package com.infobelt.excellent;

import org.junit.Test;

import java.io.IOException;
import java.util.*;

public class BasicTest {

    @Test
    public void genericProducts() throws IOException {

        List<IColumnMetadata> columnDefs = new ArrayList<>();
        columnDefs.add(new BasicColumnMetadata("productId", "Product ID", 1));
        columnDefs.add(new BasicColumnMetadata("productName", "Product Name", 2));
        columnDefs.add(new BasicColumnMetadata("productDesc", "Product Description", 3));

        List rows = new ArrayList<Map<String, Object> >();

        for(int i = 0; i < 3; i++){
            Map<String, Object> values = new HashMap<>();
            values.put("productId", i);
            values.put("productName", "Product Name " + i);
            values.put("productDesc", "Description for product " + i);
            rows.add(values);
        }

        WorkbookBuilder wbBuilder = new WorkbookBuilder().sheet().title("Products").from(rows).endSheet();

        WorkbookReference workbook = wbBuilder.build(columnDefs);
        workbook.toFile("/tmp/basic-wb-test.xlsx");

    }
}
