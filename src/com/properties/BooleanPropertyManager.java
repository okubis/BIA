package com.properties;

import org.flightgear.fgfsclient.FGFSConnection;

import java.io.IOException;

/**
 * Created by okubis on 5/23/16.
 */
public class BooleanPropertyManager extends AbstractPropertyManager<Boolean> {
    public BooleanPropertyManager(String name, Property caption) {
        super(name,caption);
    }

    public void updateValue(FGFSConnection fgfs, Boolean newValue) throws IOException {
        fgfs.setBoolean(this.propertyName, newValue);
    }

    public Boolean retrieveValue(FGFSConnection fgfs) throws IOException {
        this.value = fgfs.getBoolean(this.propertyName);
        return value;
    }

    public Boolean getOldValue() {
        return null;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Property getLabel() {
        return label;
    }
}
