package io.amigable.wfengine.service.entity;
import java.sql.Date;

public class TaskEntity {
    private int id;
    private int processInstanceId;
    private int processTransitionId;
    private int taskStateId;
    private int responsibleId;
    private String responsibleType;
    private int responsibleOfficeId;
    private Date performedAt;
    private Date finalizedAt;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(int processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public int getProcessTransitionId() {
        return processTransitionId;
    }

    public void setProcessTransitionId(int processTransitionId) {
        this.processTransitionId = processTransitionId;
    }

    public int getTaskStateId() {
        return taskStateId;
    }

    public void setTaskStateId(int taskStateId) {
        this.taskStateId = taskStateId;
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

    public Date getPerformedAt() {
        return performedAt;
    }

    public void setPerformedAt(Date performedAt) {
        this.performedAt = performedAt;
    }

    public Date getFinalizedAt() {
        return finalizedAt;
    }

    public void setFinalizedAt(Date finalizedAt) {
        this.finalizedAt = finalizedAt;
    }

    public int getResponsibleOfficeId() {
        return responsibleOfficeId;
    }

    public void setResponsibleOfficeId(int responsibleOfficeId) {
        this.responsibleOfficeId = responsibleOfficeId;
    }
}
