package com.infobelt.excellent;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

public class GenericTest {

    @Test
    public void genericProducts() throws IOException {

        List<IColumnMetadata> columnDefs = new ArrayList<>();
        columnDefs.add(new GenericColumnMetadata("productId", "Product ID", 1));
        columnDefs.add(new GenericColumnMetadata("productName", "Product Name", 2));
        columnDefs.add(new GenericColumnMetadata("productDesc", "Product Description", 3));

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
        workbook.toFile("/tmp/generic-wb-test.xlsx");

    }

}
