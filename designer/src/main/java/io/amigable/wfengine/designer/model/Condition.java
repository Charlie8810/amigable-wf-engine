package io.amigable.wfengine.designer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.w3c.dom.Attr;

import javax.persistence.*;

@Entity
@Table(name="`condition`")
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="conditionSetId")
    private ConditionSet conditionSet;

    @ManyToOne
    @JoinColumn(name="attributeId")
    private Attribute attribute;

    private String operator;

    private String evalInput;

    private boolean active;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ConditionSet getConditionSet() {
        return conditionSet;
    }

    public void setConditionSet(ConditionSet conditionSet) {
        this.conditionSet = conditionSet;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getEvalInput() {
        return evalInput;
    }

    public void setEvalInput(String evalInput) {
        this.evalInput = evalInput;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
