package io.amigable.wfengine.service.entity;

public class ProcessTransitionEntity {

    private int id;
    private int processDefinitionId;
    private int stepId;
    private int responsibleId;
    private String responsibleType;
    private int slaTypeId;
    private int slaValue;
    private int sequence;
    private boolean hasDelay;
    private int delaySlqTypeId;
    private int delayValue;
    private boolean active;

    public ProcessTransitionEntity(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(int processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public int getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(int responsibleId) {
        this.responsibleId = responsibleId;
    }

    public String getResponsibleType() {
        return responsibleType;
    }

    public void setResponsibleType(String responsibleType) {
        this.responsibleType = responsibleType;
    }

    public int getSlaTypeId() {
        return slaTypeId;
    }

    public void setSlaTypeId(int slaTypeId) {
        this.slaTypeId = slaTypeId;
    }

    public int getSlaValue() {
        return slaValue;
    }

    public void setSlaValue(int slaValue) {
        this.slaValue = slaValue;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public boolean isHasDelay() {
        return hasDelay;
    }

    public void setHasDelay(boolean hasDelay) {
        this.hasDelay = hasDelay;
    }

    public int getDelaySlqTypeId() {
        return delaySlqTypeId;
    }

    public void setDelaySlqTypeId(int delaySlqTypeId) {
        this.delaySlqTypeId = delaySlqTypeId;
    }

    public int getDelayValue() {
        return delayValue;
    }

    public void setDelayValue(int delayValue) {
        this.delayValue = delayValue;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
