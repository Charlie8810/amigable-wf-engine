package io.amigable.wfengine.designer.model;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name="taskState")
public class TaskState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
