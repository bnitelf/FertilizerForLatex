package com.example.fertilizer.model;

/**
 * Created by puiCBR on 7/25/2016.
 */
public class User_Entry {
    public  int id;
    public  int rai;
    public  int tree_age;
    public  int tree_amt;
    public  String date;

    public User_Entry(){

    }

    public User_Entry(int id, int rai, int tree_age, int tree_amt, String date) {
        this.id = id;
        this.rai = rai;
        this.tree_age = tree_age;
        this.tree_amt = tree_amt;
        this.date = date;
    }
}
