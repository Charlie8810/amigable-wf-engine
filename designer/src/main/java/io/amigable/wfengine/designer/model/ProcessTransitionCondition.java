package io.amigable.wfengine.designer.model;

import javax.persistence.*;

@Entity
@Table(name="processTransitionCondition")
public class ProcessTransitionCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="processTransitionId")
    private ProcessTransition processTransition;

    @ManyToOne
    @JoinColumn(name = "conditionSetId")
    private ConditionSet conditionSet;

    private String transitionEvent;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProcessTransition getProcessTransition() {
        return processTransition;
    }

    public void setProcessTransition(ProcessTransition processTransition) {
        this.processTransition = processTransition;
    }

    public ConditionSet getConditionSet() {
        return conditionSet;
    }

    public void setConditionSet(ConditionSet conditionSet) {
        this.conditionSet = conditionSet;
    }

    public String getTransitionEvent() {
        return transitionEvent;
    }

    public void setTransitionEvent(String transitionEvent) {
        this.transitionEvent = transitionEvent;
    }
}
