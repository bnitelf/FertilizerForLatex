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
import com.example.puicbr.fertilizerforlatex.helper.FormulaHelper;
import com.example.puicbr.fertilizerforlatex.model.Constants.TaskState;
import com.example.puicbr.fertilizerforlatex.model.Formula;
import com.example.puicbr.fertilizerforlatex.model.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Folder on 16-Nov-16.
 */
public class TasksAdapter extends BaseAdapter{

    private Context context = null;
    private List<Task> taskList = null;
    private static LayoutInflater inflater = null;
    private DbHelper dbHelper = null;

    public TasksAdapter(Context context, List<Task> taskList){
        this.context = context;
        this.taskList = taskList;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item_task, null);

        TextView tvTaskName = (TextView)vi.findViewById(R.id.tv_taskname);
        TextView tvTaskStatus = (TextView)vi.findViewById(R.id.tv_status);
        TextView tvTreeAge = (TextView)vi.findViewById(R.id.tv_tree_age);
        TextView tvTreeAmt = (TextView)vi.findViewById(R.id.tv_tree_amt);
        TextView tvDayLeft = (TextView)vi.findViewById(R.id.tv_day_left);
        TextView tvNextDate = (TextView)vi.findViewById(R.id.tv_next_date);

        Task task = taskList.get(position);

        // Setting all values in listview
        tvTaskName.setText(task.name);
        tvTaskStatus.setText(task.taskState.toString());
        tvTreeAmt.setText(task.tree_amt);

        Date dateNow = new Date();
        long monthDiff = DateHelper.getMonthDiff(task.create_date, dateNow);
        int curTreeAge = task.tree_age + (int)monthDiff;
        tvTreeAge.setText(curTreeAge);

        // set DayLeft และ NextDate ก็ต่อเมื่อ task state ไม่ใช่ achieve กับ cancel
        if(task.taskState != TaskState.achieve && task.taskState != TaskState.cancel){
            List<Formula> formulaList = dbHelper.selectAllFormula();
            Formula nextFormula = FormulaHelper.getNextFertilizingFormulaByCurrentTreeAge(formulaList, curTreeAge);
            int monthDiff2 = nextFormula.tree_age - curTreeAge;
            int dayDiff = monthDiff2 * 30;

            // Get วันที่ในอีก dayDiff วันข้างหน้า คือวันอะไร
            // TODO: (ยังทำไม่เสร็จ ดูสูตรใน google drive ที่แชร์ไว้ ถ้าจะทำต่อ)
            Calendar c = new GregorianCalendar();
            // c.set(task.create_date.);
            c.add(Calendar.DATE, dayDiff);
            Date d = c.getTime();

        } else {
            tvDayLeft.setText("-");
            tvNextDate.setText("-");
        }


//        long dayDiff2 = DateHelper.getDateDiff()

        return vi;
    }
}
