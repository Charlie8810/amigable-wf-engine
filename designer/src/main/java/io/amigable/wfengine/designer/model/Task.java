package io.amigable.wfengine.designer.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="task")
public class Task {

    @Id
    private int id;
    private int processInstanceId;
    private int processTransitionId;
    @ManyToOne
    @JoinColumn(name="taskStateId")
    private TaskState taskState;
    private int responsibleId;
    private String responsibleType;
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

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
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
}
