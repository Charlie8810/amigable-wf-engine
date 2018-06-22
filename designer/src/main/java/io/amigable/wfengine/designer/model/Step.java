package io.amigable.wfengine.designer.model;

import javax.persistence.*;

@Entity
@Table(name="step")
public class Step {

    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name="stepTypeId")
    private StepType stepType;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public StepType getStepType() {
        return stepType;
    }

    public void setStepType(StepType stepType) {
        this.stepType = stepType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
