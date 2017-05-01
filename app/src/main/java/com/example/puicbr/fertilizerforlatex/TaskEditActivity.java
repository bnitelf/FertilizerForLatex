package com.example.puicbr.fertilizerforlatex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.puicbr.fertilizerforlatex.helper.DbHelper;
import com.example.puicbr.fertilizerforlatex.model.Task;

public class TaskEditActivity extends AppCompatActivity {

    private RelativeLayout rootContainer = null;
    private EditText edtName = null;
    private EditText edtArea = null;

    private DatePicker datePicker_start = null;
    private DatePicker datePicker_harvest = null;
    private CheckBox chkHarvest =null;
    private int task_id = -1;

    private DbHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Task");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent mIntent = getIntent();
        task_id = mIntent.getIntExtra("task_id",0);

        rootContainer = (RelativeLayout) findViewById(R.id.rootContainer);

        edtName = (EditText) findViewById(R.id.edit_name);
        edtArea = (EditText) findViewById(R.id.edit_area);
        datePicker_start = (DatePicker) findViewById(R.id.datePicker_startdate);
        datePicker_harvest = (DatePicker) findViewById(R.id.datePicker_harvest);
        chkHarvest = (CheckBox) findViewById(R.id.chkHarvest);
        //edtAge = (EditText) findViewById(R.id.edit_age);
        //edtCount = (EditText) findViewById(R.id.edit_count);

        dbHelper = new DbHelper(this);
            chkHarvest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b == true){
                        datePicker_harvest.setVisibility(View.VISIBLE);
                    }
                    else
                        datePicker_harvest.setVisibility(View.INVISIBLE);

                }
            });
        loadData();
    }

    private void loadData() {
        Task task = dbHelper.selectTaskById(task_id);
        edtName.setText(task.name);
        edtArea.setText(task.rai + "");
        datePicker_start.updateDate(task.start_date.getYear(), task.start_date.getMonth(), task.start_date.getDay());

        if(task.harvest_date != null){
            chkHarvest.setChecked(true);
            datePicker_harvest.updateDate(task.harvest_date.getYear(),task.harvest_date.getMonth(),task.harvest_date.getDay());

             }
        else{

            datePicker_harvest.setVisibility(View.INVISIBLE);
        }

    }
}
