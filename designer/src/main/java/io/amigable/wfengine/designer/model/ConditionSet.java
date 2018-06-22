package io.amigable.wfengine.designer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "conditionSet")
public class ConditionSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="parentSetId")
    private ConditionSet parentSet;

    private String operator;

    private int order;

    @JsonManagedReference
    @OneToMany(mappedBy = "parentSet")
    private Collection<ConditionSet> childSets;

    @JsonManagedReference
    @OneToMany(mappedBy = "conditionSet")
    private Collection<Condition> conditions;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ConditionSet getParentSet() {
        return parentSet;
    }

    public void setParentSet(ConditionSet parentSet) {
        this.parentSet = parentSet;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Collection<ConditionSet> getChildSets() {
        return childSets;
    }

    public void setChildSets(Collection<ConditionSet> childSets) {
        this.childSets = childSets;
    }

    public Collection<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(Collection<Condition> conditions) {
        this.conditions = conditions;
    }
}
