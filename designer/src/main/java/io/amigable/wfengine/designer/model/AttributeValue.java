package io.amigable.wfengine.designer.model;

import javax.persistence.*;

@Entity
@Table(name="attributeValues")
public class AttributeValue {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="attributeId")
    private Attribute attribute;

    @ManyToOne
    @JoinColumn(name="processInstanceId")
    private ProcessInstance processInstance;

    private String rawValue;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    public String getRawValue() {
        return rawValue;
    }

    public void setRawValue(String rawValue) {
        this.rawValue = rawValue;
    }
}
