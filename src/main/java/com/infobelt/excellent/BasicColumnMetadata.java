package com.infobelt.excellent;


import lombok.Data;
import java.util.HashMap;

@Data
public class BasicColumnMetadata implements IColumnMetadata{

    private String name;
    private String header;
    private int order;
    private boolean ignored = false;

    public BasicColumnMetadata(){};

    public BasicColumnMetadata(String name, String header, int order) {
        this.name = name;
        this.header = header;
        this.order = order;
    }

    public BasicColumnMetadata(String name, String header, int order, boolean ignored) {
        this.name = name;
        this.header = header;
        this.order = order;
        this.ignored = ignored;
    }


    public Object getValue(Object obj) {
        try {
            return ((HashMap) obj).get(getName());
        } catch (Exception e) {
            throw new RuntimeException("Unable to get field " + getName() + " on " + obj, e);
        }
    }
}
