Excellent
=========

Create Excel Spreadsheets from your Objects!

Installation
------------

In your Java application add a dependency on:

```xml
<dependency>
    <groupId>com.infobelt</groupId>
    <artifactId>excellent</artifactId>
    <version>${project.version}</version>
</dependency>
```

Note you will need to add the Infobelt Nexus repository

```xml
 <repositories>
    <repository>
        <releases>
            <enabled>true</enabled>
        </releases>
        <id>infobelt-nexus</id>
        <url>https://nexus.jx.infobelt.com/repository/maven-releases/</url>
    </repository>
</repositories>
```

Getting Started
---------------

It is really pretty simple, add the annotations to your Java object.

```java
public static class Person {

    @ExcelColumn(header="Person Name")
    private String name;

    @ExcelColumn(header="Person Age")
    private int age;

    @ExcelColumn(ignore=true)
    private String password;
}
```

Then you can simply use the WorkbookBuilder to create a spreadsheet, just give it a list
of Person objects.

```java
WorkbookReference workbook = new WorkbookBuilder().sheet().title("People").from(people).endSheet().build();
workbook.toFile("/tmp/test.xlsx");
```

License
=======

[See license](LICENSE.md)
