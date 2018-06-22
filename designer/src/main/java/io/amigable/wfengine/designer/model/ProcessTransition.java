package io.amigable.wfengine.designer.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name="processTransition")
public class ProcessTransition {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="processDefinitionId")
    private ProcessDefinition processDefinition;

    @ManyToOne
    @JoinColumn(name="stepId")
    private Step step;

    private int slaTypeId;
    private Integer slaValue;
    private int responsibleId;
    private String responsibleType;
    private int sequence;
    private boolean hasDelay;
    private int delaySlaTypeId;

    private Integer delayValue;
    private boolean active;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public int getSlaTypeId() {
        return slaTypeId;
    }

    public void setSlaTypeId(int slaTypeId) {
        this.slaTypeId = slaTypeId;
    }

    public Integer getSlaValue() {
        return slaValue;
    }

    public void setSlaValue(Integer slaValue) {
        this.slaValue = slaValue;
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

    public int getDelaySlaTypeId() {
        return delaySlaTypeId;
    }

    public void setDelaySlaTypeId(int delaySlaTypeId) {
        this.delaySlaTypeId = delaySlaTypeId;
    }

    public Integer getDelayValue() {
        return delayValue;
    }

    public void setDelayValue(Integer delayValue) {
        this.delayValue = delayValue;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
