package com.johannes.lsctic.panels.gui.fields.callrecordevents;

/**
 * Created by johannesengler on 12.05.17.
 */
public class DataSourceNameResolvingEvent {
    private String name;

    public DataSourceNameResolvingEvent(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
