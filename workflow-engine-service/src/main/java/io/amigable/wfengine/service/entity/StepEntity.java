package io.amigable.wfengine.service.entity;

public class StepEntity {

    private int id;
    private int stepTypeId;
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStepTypeId() {
        return stepTypeId;
    }

    public void setStepTypeId(int stepTypeId) {
        this.stepTypeId = stepTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
