package com.properties;

import org.flightgear.fgfsclient.FGFSConnection;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by okubis on 5/23/16.
 */
public abstract class AbstractPropertyManager<T> {
    protected String propertyName;
    protected Property label;
    protected T value;

    /**
     * WARNING:
     * Do not use unless you make sure that the null values will cause no harm during the runtime
     */
    protected AbstractPropertyManager(){
        this.propertyName = null;
        this.label = null;
    }

    /**
     * simple constructor of this class
     * @param name  Property name (e.g. "/position/altitude-ft")
     * @param caption value from the enum Property indicating the type of property in more human-readable way
     */
    public AbstractPropertyManager(String name, Property caption) {
        this.propertyName = name;
        this.label = caption;
    }


    /**
     * method for changing/setting a value of this property in the game
     * @param fgfs  connection with some instance of the game
     * @param newValue  value to be set
     * @throws IOException
     */
    public abstract void updateValue(FGFSConnection fgfs,  T newValue) throws IOException;

    /**
     * method for obtaining current in-game value of this property
     * @param fgfs connection with some instance of the game
     * @return  the value of the property
     * @throws IOException
     */
    public abstract T retrieveValue(FGFSConnection fgfs) throws IOException;

    /**
     * returns previously retrieved value of this parameter
     * @return  the value obtained by last call of retrieveValue()
     */
    public abstract T getOldValue();
}
