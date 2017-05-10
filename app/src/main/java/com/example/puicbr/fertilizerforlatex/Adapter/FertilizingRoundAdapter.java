package com.example.puicbr.fertilizerforlatex.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.puicbr.fertilizerforlatex.R;
import com.example.puicbr.fertilizerforlatex.helper.DateHelper;
import com.example.puicbr.fertilizerforlatex.helper.DbHelper;
import com.example.puicbr.fertilizerforlatex.model.Constants.FertilizingRoundState;
import com.example.puicbr.fertilizerforlatex.model.Constants.TaskState;
import com.example.puicbr.fertilizerforlatex.model.Fertilizing_Round;
import com.example.puicbr.fertilizerforlatex.model.Formula;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Folder on 01-May-17.
 */
public class FertilizingRoundAdapter extends BaseAdapter {

    private Context context = null;
    private List<Fertilizing_Round> fertilizingRoundList = null;
    private static LayoutInflater inflater = null;
    private DbHelper dbHelper = null;
    private boolean editMode = false;

    private static final int KEY_TASK_ID = 1;
    private static final int KEY_FERTILIZING_ROUND_ID = 2;
    private static final int KEY_POSITION = 3;

    public FertilizingRoundAdapter(Context context, List<Fertilizing_Round> fertilizingRoundList) {
        this.context = context;
        this.fertilizingRoundList = fertilizingRoundList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dbHelper = new DbHelper(context);
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
            vi = inflater.inflate(R.layout.list_item_fertilizing_round, null);

        TextView tvRound = (TextView) vi.findViewById(R.id.txt_round);
        TextView tvTreeAge = (TextView) vi.findViewById(R.id.txt_tree_age);
        TextView tvDate = (TextView) vi.findViewById(R.id.txt_date);
        TextView tvStatus = (TextView) vi.findViewById(R.id.txt_status);
        CheckBox chkFinish = (CheckBox) vi.findViewById(R.id.chk_finish);

        Fertilizing_Round fRound = fertilizingRoundList.get(position);

        // Setting all values in listview
        tvRound.setText(String.valueOf(fRound.round));
        tvTreeAge.setText(String.valueOf(fRound.tree_age) + " เดือน");
        tvDate.setText(DateHelper.formatDateToDateStringFertilizingRound(fRound.date));
        tvStatus.setText(fRound.finish_state.getFieldDescription());
        chkFinish.setTag(position);

        if(fRound.finish_state == FertilizingRoundState.NOT_DONE){
            chkFinish.setChecked(false);
        } else {
            chkFinish.setChecked(true);
        }

        chkFinish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                int pos = (int) compoundButton.getTag();
                Fertilizing_Round fRoundSelected = fertilizingRoundList.get(pos);

                if(checked){
                    fRoundSelected.finish_state = FertilizingRoundState.DONE;
                } else {
                    fRoundSelected.finish_state = FertilizingRoundState.NOT_DONE;
                }

                dbHelper.updateFertilizing_Round(fRoundSelected);
            }
        });


        if(editMode){
            tvStatus.setVisibility(View.GONE);
            chkFinish.setVisibility(View.VISIBLE);
        } else {
            tvStatus.setVisibility(View.VISIBLE);
            chkFinish.setVisibility(View.GONE);
        }

        return vi;
    }

    public void enableEditMode(boolean edit) {

        if(this.editMode != edit) {
            this.editMode = edit;

            // สั่งให้ listview refresh ใหม่
            notifyDataSetChanged();
        }
    }
}
