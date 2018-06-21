package io.amigable.wfengine.service.entity;

import java.sql.Date;

/**
 * Created by capra on 18-06-2018.
 */
public class ProcessInstanceEntity {
    private int id;
    private int processDefinitionId;
    private int processInstanceStateId;
    private long instanceNumber;
    private String description;
    private String createdBy;
    private Date createdAt;
    private Date closedAt;

    public ProcessInstanceEntity(int id, int processDefinitionId, int processInstanceStateId, long instanceNumber, String description, String createdBy, Date createdAt, Date closedAt){
        this.id = id;
        this.processDefinitionId = processDefinitionId;
        this.processInstanceStateId = processInstanceStateId;
        this.instanceNumber = instanceNumber;
        this.description = description;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
    }

    public ProcessInstanceEntity(int processDefinitionId, int processInstanceStateId, long instanceNumber, String description, String createdBy){
        this.processDefinitionId = processDefinitionId;
        this.processInstanceStateId = processInstanceStateId;
        this.instanceNumber = instanceNumber;
        this.description = description;
        this.createdBy = createdBy;
    }

    public ProcessInstanceEntity(){}


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

    public int getProcessInstanceStateId() {
        return processInstanceStateId;
    }

    public void setProcessInstanceStateId(int processInstanceStateId) {
        this.processInstanceStateId = processInstanceStateId;
    }

    public long getInstanceNumber() {
        return instanceNumber;
    }

    public void setInstanceNumber(long instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Date closedAt) {
        this.closedAt = closedAt;
    }

    @Override
    public String toString(){
        return "Folio de Instancia: " + this.instanceNumber;
    }
}
