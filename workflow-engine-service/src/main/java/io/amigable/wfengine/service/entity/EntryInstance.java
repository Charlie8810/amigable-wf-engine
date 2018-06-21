package io.amigable.wfengine.service.entity;

import sun.security.jca.GetInstance;

import java.util.List;

/**
 * Created by capra on 18-06-2018.
 */
public class EntryInstance {

    private long instanceNumber;
    private int stepId;
    private int processId;
    private String description;
    private String event;
    private int userId;
    private List<InstanceAttribute> setVariables;


    public EntryInstance(){}

    public long getInstanceNumber() {
        return instanceNumber;
    }

    public void setInstanceNumber(long instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString(){
        return "{ instanceNumber: " +  this.instanceNumber +
                ", stepId: " + this.stepId +
                ", processId: " + this.processId +
                ", event: '" + this.event + "'" +
                ", userId: " + this.userId +
                " }";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<InstanceAttribute> getSetVariables() {
        return setVariables;
    }

    public void setSetVariables(List<InstanceAttribute> setValues) {
        this.setVariables = setValues;
    }
}
