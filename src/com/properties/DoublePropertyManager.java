package com.properties;

import org.flightgear.fgfsclient.FGFSConnection;

import java.io.IOException;
import java.io.ObjectInput;

/**
 * Created by okubis on 5/23/16.
 */
public class DoublePropertyManager extends AbstractPropertyManager<Double> {

    public DoublePropertyManager(String name, Property caption) {
        super(name,caption);
    }

    public void updateValue(FGFSConnection fgfs, Double newValue) throws IOException {
        fgfs.setDouble(this.propertyName, newValue);
    }

    public Double retrieveValue(FGFSConnection fgfs) throws IOException {
        this.value = fgfs.getDouble(this.propertyName);
        return value;
    }

    public Double getOldValue(){
        return value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Property getLabel() {
        return label;
    }
}
