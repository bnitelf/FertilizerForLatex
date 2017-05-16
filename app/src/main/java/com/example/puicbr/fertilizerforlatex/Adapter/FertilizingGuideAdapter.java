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
import com.example.puicbr.fertilizerforlatex.model.Constants.FertilizingRoundState;
import com.example.puicbr.fertilizerforlatex.model.Fertilizing_Round;
import com.example.puicbr.fertilizerforlatex.model.Formula;
import com.example.puicbr.fertilizerforlatex.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Folder on 16-May-17.
 */
public class FertilizingGuideAdapter extends BaseAdapter {

    private Context context = null;
    private Task task = null;
    private List<Fertilizing_Round> fertilizingRoundList = null;
    private boolean viewBeforeHarvest = true;
    private static LayoutInflater inflater = null;
    private DbHelper dbHelper = null;

    // ปริมาณที่ใช้ต่อต้น หลังกรีดยางแล้ว (หน่วยเป็นกรัม)
    private int usePerTreeAfterHarvest = 500;
    private int sumInGram = 0;

    // ปริมาณที่ใช้ต่อต้น (หน่วยเป็นกรัม)
    private List<Integer> usePerTreeList = null;

    public FertilizingGuideAdapter(Context context, Task task, List<Fertilizing_Round> fertilizingRoundList, boolean viewBeforeHarvest) {
        this.context = context;
        this.task = task;
        this.fertilizingRoundList = fertilizingRoundList;
        this.viewBeforeHarvest = viewBeforeHarvest;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dbHelper = new DbHelper(context);

        Formula formula = null;

        if(viewBeforeHarvest){
            usePerTreeList = new ArrayList<>();
            for (Fertilizing_Round fRound : fertilizingRoundList) {
                formula = dbHelper.selectFormulaByTreeAge(fRound.tree_age);
                usePerTreeList.add(formula.use_per_tree);
                sumInGram += formula.use_per_tree * task.tree_amt;
            }
        } else {
            for (Fertilizing_Round fRound : fertilizingRoundList) {
                sumInGram += usePerTreeAfterHarvest * task.tree_amt;
            }
        }

        Fertilizing_Round fRoundSum = new Fertilizing_Round();
        fRoundSum.id = 999;
        fRoundSum.task_id = task.id;

        this.fertilizingRoundList.add(fRoundSum);
    }

    @Override
    public int getCount() {
        return fertilizingRoundList.size();
    }

    @Override
    public Object getItem(int i) {
        return fertilizingRoundList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return fertilizingRoundList.get(i).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_item_fertilizing_guide, null);

        TextView tvTreeAge = (TextView) vi.findViewById(R.id.txt_tree_age);
        TextView tvDate = (TextView) vi.findViewById(R.id.txt_date);
        TextView tvUsePerTree  = (TextView) vi.findViewById(R.id.txt_use_per_tree);
        TextView tvTotal = (TextView) vi.findViewById(R.id.txt_total);

        Fertilizing_Round fRound = fertilizingRoundList.get(position);
        float total = 0;

        // Setting all values in listview
        if(fRound.id != 999){
            // ข้อมุลรอบการให้ปุ๋ยทั่วไป
            tvTreeAge.setText(String.valueOf(fRound.tree_age) + " เดือน");
            tvDate.setText(DateHelper.formatDateToDateStringForDisplay(fRound.date));

            if(viewBeforeHarvest){
                tvUsePerTree.setText(usePerTreeList.get(position) + " กรัม");
                total = ((float)usePerTreeList.get(position) * (float)task.tree_amt) / 1000;

            } else {

                tvUsePerTree.setText(usePerTreeAfterHarvest + " กรัม");
                total = ((float)usePerTreeAfterHarvest * (float)task.tree_amt) / 1000;
            }

        } else {
            // ข้อมูล sum
            tvTreeAge.setText("");
            tvDate.setText("");
            tvUsePerTree.setText("รวม");
            total = (float)sumInGram / 1000;
        }

        tvTotal.setText(total + " กก.");

        if(fRound.finish_state == FertilizingRoundState.NOT_DONE){

        } else {
            // Maybe style bg to green
        }

        return vi;
    }
}
