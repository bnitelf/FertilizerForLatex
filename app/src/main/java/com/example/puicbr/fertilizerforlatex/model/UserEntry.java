package com.example.puicbr.fertilizerforlatex.model;

/**
 * Created by puiCBR on 7/25/2016.
 */
public class UserEntry {
    public  int id;
    public  int rai;
    public  int tree_age;
    public  int tree_amt;
    public  String date;

    public UserEntry(){

    }

    public UserEntry(int id, int rai, int tree_age, int tree_amt, String date) {
        this.id = id;
        this.rai = rai;
        this.tree_age = tree_age;
        this.tree_amt = tree_amt;
        this.date = date;
    }

    @Override
    public String toString(){
        return String.format("UserEntry {id = %d, rai = %d, tree_age = %d, tree_amt = %d, data = %s}",
                this.id, this.rai, this.tree_age, this.tree_amt, this.date);
    }
}
