package com.infobelt.excellent;

import com.infobelt.excellent.annotations.ExcelColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BasicTest {

    @Data
    @AllArgsConstructor
    public static class Person {

        @ExcelColumn(header="Person Name")
        private String name;

        @ExcelColumn(header="Person Age")
        private int age;

        @ExcelColumn(ignore=true)
        private String password;
    }

    @Test
    public void people() throws IOException {
        List<Person> people = Arrays.asList(new Person("Philip", 45, "blhablah"), new Person("Bob", 32, "blahla"));
        WorkbookReference workbook = new WorkbookBuilder().sheet().title("People").from(people).endSheet().build();
        workbook.toFile("/tmp/test.xlsx");

    }

}
