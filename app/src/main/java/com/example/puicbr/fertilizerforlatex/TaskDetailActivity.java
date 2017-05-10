package com.example.puicbr.fertilizerforlatex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.puicbr.fertilizerforlatex.helper.DateHelper;
import com.example.puicbr.fertilizerforlatex.helper.DbHelper;
import com.example.puicbr.fertilizerforlatex.model.Task;

public class TaskDetailActivity extends AppCompatActivity {

    private TextView txtArea02 = null;
    private TextView txtRai = null;
    private TextView txtTon = null;
    private TextView txtAgeTreeMonth =null;
    private TextView txtDate = null ;
    private TextView txtFomula = null;
    private RadioButton radBefore = null;
    private RadioButton radAfter = null ;
    private Button btnEdit =null;
    private Button btnViewFreRound =null;
    private TextView txtName =null;
    private DbHelper dbHelper = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtArea02 = (TextView)findViewById(R.id.edit_area02);
        txtTon = (TextView) findViewById(R.id.txt_tree_amt);
        txtAgeTreeMonth = (TextView) findViewById(R.id.txt_tree_age);
        txtDate = (TextView) findViewById(R.id.txt_start_date);
        txtFomula = (TextView) findViewById(R.id.txt_formula);
        txtRai = (TextView) findViewById(R.id.txt_rai);
        txtName = (TextView) findViewById(R.id.txt_name);
        radAfter = (RadioButton) findViewById(R.id.rad_after);
        radBefore = (RadioButton) findViewById(R.id.rad_before);
        btnEdit = (Button) findViewById(R.id.btn_edit);
        btnViewFreRound = (Button) findViewById(R.id.btn_view_fertilizing_round);
        dbHelper = new DbHelper(this);

        Intent mIntent = getIntent();
        final int task_id = mIntent.getIntExtra("task_id",0);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(TaskDetailActivity.this, TaskEditActivity.class);
                myIntent.putExtra("task_id", task_id);
                startActivity(myIntent);
            }
        });
        btnViewFreRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(TaskDetailActivity.this, FertilizingRoundActivity.class);
                myIntent.putExtra("task_id", task_id);
                startActivity(myIntent);
            }

        });


        Task task = dbHelper.selectTaskById(task_id);

        txtName.setText(task.name);
        txtArea02.setText(task.location);
        txtTon.setText(task.tree_amt + " ต้น");
        txtRai.setText(task.rai + " ไร่");
        txtDate.setText(DateHelper.formatDateToDateString(task.start_date));
        txtAgeTreeMonth.setText(DateHelper.GetCurrentTreeAge(task.start_date) + " เดือน");

        if(task.harvest_date == null){
            // ยังไม่ได้กรีด
            txtFomula.setText("สูตรการให้ปุ๋ยก่อนกรีดยาง");
        } else {
            // กรีดแล้ว
            txtFomula.setText("สูตรการให้ปุ๋ยหลังกรีดยาง");
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
