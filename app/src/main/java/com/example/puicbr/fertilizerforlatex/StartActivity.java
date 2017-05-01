package com.example.puicbr.fertilizerforlatex;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class StartActivity extends AppCompatActivity {

    private RelativeLayout rootContainer = null;
    private EditText edtName = null;
    private EditText edtArea = null;
    private EditText edtAge = null;
    private EditText edtCount = null;
    private DatePicker datePicker_start = null;

    private DbHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create New Task");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        rootContainer = (RelativeLayout) findViewById(R.id.rootContainer);

        edtName = (EditText) findViewById(R.id.edit_name);
        edtArea = (EditText) findViewById(R.id.edit_area);
        datePicker_start = (DatePicker) findViewById(R.id.datePicker);
        //edtAge = (EditText) findViewById(R.id.edit_age);
        //edtCount = (EditText) findViewById(R.id.edit_count);

        dbHelper = new DbHelper(this);
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
            //if (edtAge.getText().length() == 0) {
              //  edtAge.setError("กรุณาใส่อายุต้นยางด้วย");
            //}
            //if (edtCount.getText().length() == 0) {
              //  edtCount.setError("กรุณาใส่จำนวนต้นยางด้วย");
            //}

            if (edtName.getText().length() > 0 ||
                    edtArea.getText().length() > 0) {

                String taskname = edtName.getText().toString();
                int area = Integer.parseInt(edtArea.getText().toString());
                //int age = Integer.parseInt(edtAge.getText().toString());
                //int count = Integer.parseInt(edtCount.getText().toString());

                Calendar calendar_start = Calendar.getInstance();
                calendar_start.set(datePicker_start.getYear(),datePicker_start.getMonth(),datePicker_start.getDayOfMonth());
                Date start_date = calendar_start.getTime();

                Date empty_date=DateHelper.getEmptyDate();

                Task task = new Task();
                task.name = taskname;
                task.rai = area;
                task.tree_age = DateHelper.GetCurrentTreeAge(start_date);
                task.tree_amt = FertilizingHelper.calTreeAmountFromArea(area);
                task.taskState = TaskState.ACTIVE;
                task.create_date = new Date();
                task.start_date = start_date;
                task.harvest_date = empty_date;

                dbHelper.createTask(task);

                Task newTask = dbHelper.selectLastTask();

                // add ข้อมูลลงตาราง Fertilizing_Round (ตารางรอบการให้ปุ๋ย)
                List<Formula> formulaList = dbHelper.selectAllFormula();
                List<Fertilizing_Round> fertilizingRoundList = FertilizingRoundHelper.generateFertilizingRoundList(newTask, formulaList);

                for (Fertilizing_Round fRound : fertilizingRoundList){
                    dbHelper.createFertilizing_Round(fRound);
                }


                Dialog dialog = MyDialogBuilder.CreateDialog(this, "Add Successful", new DialogInterface.OnClickListener() {
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
