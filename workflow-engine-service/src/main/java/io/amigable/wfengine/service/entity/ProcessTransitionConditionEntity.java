package io.amigable.wfengine.service.entity;

/**
 * Created by capra on 16-06-2018.
 */
public class ProcessTransitionConditionEntity {

    private int id;
    private int processTransitionId;
    private int conditionSetId;
    private String transitionEvent;
    private int nextProcessTransitionId;

    public ProcessTransitionConditionEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProcessTransitionId() {
        return processTransitionId;
    }

    public void setProcessTransitionId(int processTransitionId) {
        this.processTransitionId = processTransitionId;
    }

    public int getConditionSetId() {
        return conditionSetId;
    }

    public void setConditionSetId(int conditionSetId) {
        this.conditionSetId = conditionSetId;
    }

    public String getTransitionEvent() {
        return transitionEvent;
    }

    public void setTransitionEvent(String transitionEvent) {
        this.transitionEvent = transitionEvent;
    }

    public int getNextProcessTransitionId() {
        return nextProcessTransitionId;
    }

    public void setNextProcessTransitionId(int nextProcessTransitionId) {
        this.nextProcessTransitionId = nextProcessTransitionId;
    }
}
