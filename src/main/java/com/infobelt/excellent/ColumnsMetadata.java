package com.infobelt.excellent;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ColumnsMetadata {

    private final Collection objects;

    private Map<String, ColumnMetadata> columns = new HashMap<>();

    public ColumnsMetadata(Collection objects) {
        this.objects = objects;
    }

    public void inspect(boolean firstRow) {
        for (Object obj : objects) {

            for (Field field : obj.getClass().getDeclaredFields()) {
                ColumnMetadata columnMetadata = new ColumnMetadata(obj, field);
                columns.put(columnMetadata.getName(), columnMetadata);
            }

            if (firstRow)
                break;
        }
    }

    public List<ColumnMetadata> getColumns() {
        return columns.values().stream()
                .sorted(Comparator.comparing(ColumnMetadata::getOrder)).collect(Collectors.toList());
    }

}
