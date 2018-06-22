package io.amigable.wfengine.designer.model;

import javax.persistence.*;

@Entity
@Table(name="attribute")
public class Attribute {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name="processDefinitionId")
    private ProcessDefinition processDefinition;

    private String name;
    private String sysname;
    private String dataType;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSysname() {
        return sysname;
    }

    public void setSysname(String sysname) {
        this.sysname = sysname;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
