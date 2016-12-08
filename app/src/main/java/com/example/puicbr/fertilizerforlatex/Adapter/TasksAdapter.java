package com.example.puicbr.fertilizerforlatex.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.puicbr.fertilizerforlatex.R;
import com.example.puicbr.fertilizerforlatex.helper.DateHelper;
import com.example.puicbr.fertilizerforlatex.helper.DbHelper;
import com.example.puicbr.fertilizerforlatex.model.Constants.TaskState;
import com.example.puicbr.fertilizerforlatex.model.Fertilizing_Round;
import com.example.puicbr.fertilizerforlatex.model.Formula;
import com.example.puicbr.fertilizerforlatex.model.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Folder on 16-Nov-16.
 */
public class TasksAdapter extends BaseAdapter {

    private Context context = null;
    private List<Task> taskList = null;
    private static LayoutInflater inflater = null;
    private DbHelper dbHelper = null;

    public TasksAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dbHelper = new DbHelper(context);
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int i) {
        return taskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return taskList.get(i).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_item_task, null);

        TextView tvTaskName = (TextView) vi.findViewById(R.id.tv_taskname);
        TextView tvTaskStatus = (TextView) vi.findViewById(R.id.tv_status);
        TextView tvTreeAge = (TextView) vi.findViewById(R.id.tv_tree_age);
        TextView tvTreeAmt = (TextView) vi.findViewById(R.id.tv_tree_amt);
        TextView tvDayLeft = (TextView) vi.findViewById(R.id.tv_day_left);
        TextView tvNextDate = (TextView) vi.findViewById(R.id.tv_next_date);

        Task task = taskList.get(position);

        // Setting all values in listview
        tvTaskName.setText(task.name);
        tvTaskStatus.setText(task.taskState.toString());

        String treeAmtStr = Integer.toString(task.tree_amt);
        tvTreeAmt.setText(treeAmtStr);

        Date dateNow = new Date();
        long monthDiff = DateHelper.getMonthDiff(task.create_date, dateNow);
        //int curTreeAge = task.tree_age + (int) monthDiff;
        int curTreeAge = (int) monthDiff;

        String curTreeAgeStr = Integer.toString(curTreeAge);
        tvTreeAge.setText(curTreeAgeStr);

        // set DayLeft และ NextDate ก็ต่อเมื่อ task state ไม่ใช่ achieve กับ cancel
        if (task.taskState != TaskState.achieve && task.taskState != TaskState.cancel) {
            List<Formula> formulaList = dbHelper.selectAllFormula();
            List<Fertilizing_Round> fertilizingRoundList = dbHelper.SelectFertilizing_RoundByTaskId(task.id);

            Fertilizing_Round nextFertilizerRound = null;
            int compareDateResult = 0;

            for (Fertilizing_Round fRound : fertilizingRoundList) {

                compareDateResult = fRound.date.compareTo(dateNow);

                // if fertilizer round date is > today
                if (compareDateResult > 0) {
                    nextFertilizerRound = fRound;
                    break;
                }
            }

            if (nextFertilizerRound == null) {
                tvDayLeft.setText("-");
                tvNextDate.setText("-");
            } else {
                // Find dayLeft
                long dayLeft = DateHelper.getDateDiff(dateNow, nextFertilizerRound.date, TimeUnit.DAYS);
                String dayLeftStr = Long.toString(dayLeft);

                // Set dayLeft & nextDate
                Date nextDate = nextFertilizerRound.date;
                String nextDateStr = DateHelper.formatDateToDateString(nextDate);

                tvDayLeft.setText(dayLeftStr);
                tvNextDate.setText(nextDateStr);
            }

        } else {
            tvDayLeft.setText("-");
            tvNextDate.setText("-");
        }

        return vi;
    }
}
