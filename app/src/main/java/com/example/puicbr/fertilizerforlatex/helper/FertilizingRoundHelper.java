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
            calendar = DateHelper.addDay(calendar, f.tree_age * 30);

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

    public static List<Fertilizing_Round> generateFertilizingRoundListAfterHarvest(Task task){
        List<Fertilizing_Round> roundList = new ArrayList<>();
        Fertilizing_Round fertilizingRound = null;

        // 20 year * 12 = 240 month
        int maxTreeAge = 240;

        // รอบการใส่ปุ๋ย = ทุก 6 เดือน
        int monthInterval = 6;

        if(task.isHarvested()) {

            Calendar calendar = null;
            int round = 0;

            int currentTreeAge = DateHelper.GetCurrentTreeAge(task);
            while(currentTreeAge < maxTreeAge) {

                if(round == 0) {
                    calendar = DateHelper.toCalendar(task.harvest_date);
                } else {
                    calendar = DateHelper.toCalendar(roundList.get(roundList.size() - 1).date);
                }
                calendar = DateHelper.addDay(calendar, monthInterval * 30);

                round += 1;
                currentTreeAge += monthInterval;

                fertilizingRound = new Fertilizing_Round();
                fertilizingRound.task_id = task.id;
                fertilizingRound.round = round;
                fertilizingRound.tree_age = currentTreeAge;
                fertilizingRound.date = calendar.getTime();

                roundList.add(fertilizingRound);
            }
        }

        return roundList;
    }
}
