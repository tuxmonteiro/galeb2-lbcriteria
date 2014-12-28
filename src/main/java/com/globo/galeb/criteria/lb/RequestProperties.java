package com.globo.galeb.criteria.lb;

import java.util.HashMap;
import java.util.Iterator;

public class RequestProperties extends HashMap<String, Object> {

    private static final long serialVersionUID = 1497316059745329766L;

    public String getString(String key) {
        return get(key).toString().trim();
    }

    public String getString(String key, String aDefault) {
        return containsKey(key) ? getString(key) : aDefault;
    }

    public int getInt(String key) throws NumberFormatException {
        return Integer.valueOf(getString(key));
    }

    public int getInt(String key, int aDefault) throws NumberFormatException {
        return containsKey(key) ? getInt(key) : aDefault;
    }

    public long getLong(String key) throws NumberFormatException {
        return Long.valueOf(getString(key));
    }

    public long getLong(String key, long aDefault) throws NumberFormatException {
        return containsKey(key) ? getLong(key) : aDefault;
    }

    public boolean getBoolean(String key) throws NumberFormatException {
        return Boolean.valueOf(getString(key)) || "1".equals(getString(key));
    }

    public boolean getBoolean(String key, boolean aDefault) throws NumberFormatException {
        return containsKey(key) ? getBoolean(key) : aDefault;
    }

    public float getFloat(String key) throws NumberFormatException {
        return Float.valueOf(getString(key));
    }

    public float getFloat(String key, float aDefault) throws NumberFormatException {
        return containsKey(key) ? getFloat(key) : aDefault;
    }

    public double getDouble(String key) throws NumberFormatException {
        return Double.valueOf(getString(key));
    }

    public double getDouble(String key, double aDefault) throws NumberFormatException {
        return containsKey(key) ? getDouble(key) : aDefault;
    }

    @Override
    public String toString() {
        String requestPropertiesString = "";
        Iterator<String> keys = keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            requestPropertiesString += String.format("'%s':'%s' ", key, getString(key));
        }
        return requestPropertiesString.trim();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
