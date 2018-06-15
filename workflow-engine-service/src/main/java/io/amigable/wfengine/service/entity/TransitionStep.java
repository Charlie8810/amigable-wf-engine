package io.amigable.wfengine.service.entity;

public class TransitionStep {

    private int transitionStepId;
    private int processDefinitionId;
    private int stepTypeId;
    private String stepTypeName;
    private String stepName;
    private String responsibleType;
    private int responsibleId;
    private int slaTypeId;
    private int slaValue;
    private int assignedRoleId;
    private String assignedRoleName;
    private int assignedUserId;
    private String assignedUserName;

    public TransitionStep(){}

    public int getTransitionStepId() {
        return transitionStepId;
    }

    public void setTransitionStepId(int transitionStepId) {
        this.transitionStepId = transitionStepId;
    }

    public int getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(int processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public int getStepTypeId() {
        return stepTypeId;
    }

    public void setStepTypeId(int stepTypeId) {
        this.stepTypeId = stepTypeId;
    }

    public String getStepTypeName() {
        return stepTypeName;
    }

    public void setStepTypeName(String stepTypeName) {
        this.stepTypeName = stepTypeName;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getResponsibleType() {
        return responsibleType;
    }

    public void setResponsibleType(String responsibleType) {
        this.responsibleType = responsibleType;
    }

    public int getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(int responsibleId) {
        this.responsibleId = responsibleId;
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

    public int getAssignedRoleId() {
        return assignedRoleId;
    }

    public void setAssignedRoleId(int assignedRoleId) {
        this.assignedRoleId = assignedRoleId;
    }

    public String getAssignedRoleName() {
        return assignedRoleName;
    }

    public void setAssignedRoleName(String assignedRoleName) {
        this.assignedRoleName = assignedRoleName;
    }

    public int getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(int assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public String getAssignedUserName() {
        return assignedUserName;
    }

    public void setAssignedUserName(String assignedUserName) {
        this.assignedUserName = assignedUserName;
    }

}
