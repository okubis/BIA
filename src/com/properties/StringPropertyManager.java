package com.properties;

import org.flightgear.fgfsclient.FGFSConnection;

import java.io.IOException;

/**
 * Created by okubis on 5/23/16.
 */
public class StringPropertyManager extends AbstractPropertyManager<String> {

    public StringPropertyManager(String name, Property caption) {
        super(name,caption);
    }

    public void updateValue(FGFSConnection fgfs, String newValue) throws IOException {
        fgfs.set(this.propertyName, newValue);
    }

    public String retrieveValue(FGFSConnection fgfs) throws IOException {
        this.value = fgfs.get(this.propertyName);
        return value;
    }

    public String getOldValue(){
        return value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Property getLabel() {
        return label;
    }
}
