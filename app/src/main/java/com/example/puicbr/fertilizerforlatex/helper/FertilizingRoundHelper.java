package com.example.puicbr.fertilizerforlatex.helper;

import com.example.puicbr.fertilizerforlatex.model.Fertilizing_Round;
import com.example.puicbr.fertilizerforlatex.model.Formula;
import com.example.puicbr.fertilizerforlatex.model.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nplayground on 12/8/16.
 */

public class FertilizingRoundHelper {

    public static List<Fertilizing_Round> generateFertilizingRoundList(Task task, List<Formula> formulaList){
        List<Fertilizing_Round> roundList = new ArrayList<>();
        Fertilizing_Round fertilizingRound = null;

        Calendar calendar = null;
        int round = 0;

        for (Formula f : formulaList){

            calendar = DateHelper.toCalendar(task.start_date);
            calendar.add(Calendar.MONTH, f.tree_age);

            round += 1;

            fertilizingRound = new Fertilizing_Round();
            fertilizingRound.task_id = task.id;
            fertilizingRound.round = round;
            fertilizingRound.tree_age = f.tree_age;
            fertilizingRound.date = calendar.getTime();

            roundList.add(fertilizingRound);
        }

        return roundList;
    }
}
