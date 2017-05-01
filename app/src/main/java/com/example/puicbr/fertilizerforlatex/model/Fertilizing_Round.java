package com.example.puicbr.fertilizerforlatex.model;

import com.example.puicbr.fertilizerforlatex.model.Constants.FertilizingRoundState;

import java.util.Date;

/**
 * Created by Folder on 14-Nov-16.
 */
public class Fertilizing_Round {
    public int id;
    public int task_id;
    public int round;
    public int tree_age;
    public Date date;
    public FertilizingRoundState finish_state;

    public Fertilizing_Round(){
        finish_state = FertilizingRoundState.NOT_DONE;
    }
}
