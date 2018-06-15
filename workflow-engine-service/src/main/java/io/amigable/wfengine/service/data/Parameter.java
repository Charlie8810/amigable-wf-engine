package io.amigable.wfengine.service.data;

import java.sql.Date;

public class Parameter {


    private String name;
    private eParameterType type;
    private Object value;

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public eParameterType getType() {
        return type;
    }

    private void setType(eParameterType type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    private void setValue(Object value) {
        this.value = value;
    }

    public Parameter(String name, Object value){
        this.setName(name);
        this.setValue(value);
        this.setType(eParameterType.DEFAULT);
    }

    public Parameter(String name, boolean value){
        this.setName(name);
        this.setValue(value);
        this.setType(type);
    }


    public Parameter(String name, String value){
        this.setName(name);
        this.setValue(value);
        this.setType(type);
    }

    public Parameter(String name, int value){
        this.setName(name);
        this.setValue(value);
        this.setType(type);
    }

    public Parameter(String name, Date value){
        this.setName(name);
        this.setValue(value);
        this.setType(type);
    }


}
