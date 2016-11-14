package com.example.puicbr.fertilizerforlatex.model;

import com.example.puicbr.fertilizerforlatex.model.Constants.TaskState;

import java.util.Date;

/**
 * Created by puiCBR on 7/25/2016.
 */
public class Task {
    public int id;
    public String name;
    public int rai;
    public int tree_age;
    public int tree_amt;
    public Date create_date;
    public TaskState taskState;

    public Task(){

    }

    public Task(int id, String name, int rai, int tree_age, int tree_amt, Date create_date, TaskState taskState) {
        this.id = id;
        this.name = name;
        this.rai = rai;
        this.tree_age = tree_age;
        this.tree_amt = tree_amt;
        this.create_date = create_date;
        this.taskState = taskState;
    }

    @Override
    public String toString(){
        return String.format("Task {id = %d, name = %s, rai = %d, tree_age = %d, tree_amt = %d, create_date = %s, state = %s}",
                this.id, this.name, this.rai, this.tree_age, this.tree_amt, this.create_date, this.taskState);
    }
}
