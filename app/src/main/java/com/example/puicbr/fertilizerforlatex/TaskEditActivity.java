package com.example.puicbr.fertilizerforlatex;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.puicbr.fertilizerforlatex.helper.DateHelper;
import com.example.puicbr.fertilizerforlatex.helper.DbHelper;
import com.example.puicbr.fertilizerforlatex.helper.FertilizingHelper;
import com.example.puicbr.fertilizerforlatex.helper.FertilizingRoundHelper;
import com.example.puicbr.fertilizerforlatex.model.Constants.TaskState;
import com.example.puicbr.fertilizerforlatex.model.Fertilizing_Round;
import com.example.puicbr.fertilizerforlatex.model.Formula;
import com.example.puicbr.fertilizerforlatex.model.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskEditActivity extends AppCompatActivity {

    private RelativeLayout rootContainer = null;
    private EditText edtName = null;
    private EditText edtArea = null;

    private DatePicker datePicker_start = null;
    private DatePicker datePicker_harvest = null;
    private CheckBox chkHarvest = null;
    private int task_id = -1;
    private Task task = null;

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
        task_id = mIntent.getIntExtra("task_id", 0);

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
            if (b == true) {
                datePicker_harvest.setVisibility(View.VISIBLE);
            } else
                datePicker_harvest.setVisibility(View.INVISIBLE);

            }
        });
        loadData();
    }

    private void loadData() {
        task = dbHelper.selectTaskById(task_id);
        edtName.setText(task.name);
        edtArea.setText(task.rai + "");

        Calendar dateStartDate = DateHelper.toCalendar(task.start_date);

        datePicker_start.updateDate(dateStartDate.get(Calendar.YEAR), dateStartDate.get(Calendar.MONTH), dateStartDate.get(Calendar.DAY_OF_MONTH));

        if (task.harvest_date != null) {
            chkHarvest.setChecked(true);
            datePicker_harvest.updateDate(task.harvest_date.getYear(), task.harvest_date.getMonth(), task.harvest_date.getDay());

        } else {

            datePicker_harvest.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_done) {

            if (edtName.getText().length() == 0) {
                edtName.setError("กรุณาตั้งชื่องานด้วย");
            }
            if (edtArea.getText().length() == 0) {
                edtArea.setError("กรุณาใส่พื้นที่จำนวนไร่ด้วย");
            }

            if (edtName.getText().length() > 0 ||
                    edtArea.getText().length() > 0) {

                task.name = edtName.getText().toString();

                int area = Integer.parseInt(edtArea.getText().toString());
                // ถ้ามีการ update จำนวนไร่ ต้อง cal ค่าจำนวนต้นยางใหม่
                if(area != task.rai){
                    task.rai = area;
                    task.tree_amt = FertilizingHelper.calTreeAmountFromArea(area);
                }

                // ถ้ามีการเช็คว่ากรีดยางแล้วให้เก็บวันที่กรีดด้วย
                if(chkHarvest.isChecked()){
                    Calendar calendar_harvestDate = Calendar.getInstance();
                    calendar_harvestDate.set(datePicker_harvest.getYear(), datePicker_harvest.getMonth(), datePicker_harvest.getDayOfMonth());

                    task.harvest_date = calendar_harvestDate.getTime();
                }

                Calendar calendar_newStart = Calendar.getInstance();
                calendar_newStart.set(datePicker_start.getYear(),datePicker_start.getMonth(),datePicker_start.getDayOfMonth());

                Calendar calendar_oldStart = DateHelper.toCalendar(task.start_date);

                // ถ้ามีการ update วันที่เริ่มปลูกต้อง cal อายุต้นและรอบการให้ปุ๋ยใหม่
                if(!calendar_oldStart.equals(calendar_newStart)){
                    task.start_date = calendar_newStart.getTime();
                    task.tree_age = DateHelper.GetCurrentTreeAge(task.start_date);

                    // DELETE รอบการให้ปุ๋ยเก่า
                    dbHelper.deleteFertilizing_RoundByTaskId(task.id);

                    // cal และ add รอบการให้ปุ๋ยใหม่
                    List<Formula> formulaList = dbHelper.selectAllFormula();
                    List<Fertilizing_Round> fertilizingRoundList = FertilizingRoundHelper.generateFertilizingRoundList(task, formulaList);

                    for (Fertilizing_Round fRound : fertilizingRoundList){
                        dbHelper.createFertilizing_Round(fRound);
                    }
                }

                dbHelper.updateTask(task);

                Dialog dialog = MyDialogBuilder.CreateDialog(this, "Edit Successful", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                dialog.show();

            }


        }
        return true;
    }
}
