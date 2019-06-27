package com.infobelt.excellent;

import com.infobelt.excellent.annotations.ExcelColumn;
import lombok.Data;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;

@Data
public class FieldColumnMetadata implements IColumnMetadata{

    private final Field field;
    private ExcelColumn propertyAnnotation;
    private boolean ignored = false;
    private String name;
    private String header;
    private int order;

    public FieldColumnMetadata(Object obj, Field field) {
        this.field = field;
        if (field != null) {
            ExcelColumn[] annotations = field.getAnnotationsByType(ExcelColumn.class);
            this.propertyAnnotation = annotations.length > 0 ? annotations[0] : null;
            if (propertyAnnotation != null) {
                this.name = "".equals(propertyAnnotation.name()) ? field.getName() : propertyAnnotation.name();
                this.header = "".equals(propertyAnnotation.header()) ? field.getName() : propertyAnnotation.header();
                this.ignored = propertyAnnotation.ignore();
                this.order = propertyAnnotation.order();
            } else {
                this.name = field.getName();
                this.header = field.getName();
                this.order = 0;
                this.ignored = false;
            }

        } else
            this.ignored = true;
    }

    public Object getValue(Object obj) {
        try {
            return PropertyUtils.getProperty(obj, field.getName());
        } catch (Exception e) {
            throw new RuntimeException("Unable to get field " + field.getName() + " on " + obj, e);
        }
    }
}
