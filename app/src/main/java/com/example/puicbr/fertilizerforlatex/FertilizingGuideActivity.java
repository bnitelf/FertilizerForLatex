package com.example.puicbr.fertilizerforlatex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.puicbr.fertilizerforlatex.Adapter.FertilizingGuideAdapter;
import com.example.puicbr.fertilizerforlatex.Adapter.FertilizingRoundAdapter;
import com.example.puicbr.fertilizerforlatex.helper.DateHelper;
import com.example.puicbr.fertilizerforlatex.helper.DbHelper;
import com.example.puicbr.fertilizerforlatex.model.Fertilizing_Round;
import com.example.puicbr.fertilizerforlatex.model.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FertilizingGuideActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "fertilizerforlatexDebug";

    private TextView txtHeader = null;
    private TextView txtFormula = null;
    private ListView lvData = null;
    private TextView txtNoData = null;

    private int task_id = -1;
    private boolean viewBeforeHarvast = true;

    private Task task = null;

    private FertilizingGuideAdapter adapter = null;
    private DbHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizing_guide);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtHeader = (TextView) findViewById(R.id.txt_header);
        txtFormula = (TextView) findViewById(R.id.txt_formula);
        lvData = (ListView) findViewById(R.id.lvBefore);
        txtNoData = (TextView) findViewById(R.id.txtNoDataBefore);

        Intent mIntent = getIntent();
        task_id = mIntent.getIntExtra("task_id", 0);
        viewBeforeHarvast = mIntent.getBooleanExtra("before", true);

        Log.d(DEBUG_TAG, "task_id = " + task_id);

        dbHelper = new DbHelper(this);
        task = dbHelper.selectTaskById(task_id);

        List<Fertilizing_Round> fertilizingRoundList = dbHelper.selectFertilizing_RoundByTaskId(task_id);

        // รายการรอบการให้ปุ๋ยก่อนกรีด
        List<Fertilizing_Round> fRoundBeforeList = new ArrayList<>();

        // รายการรอบการให้ปุ๋ยหลังกรีด
        List<Fertilizing_Round> fRoundAfterList = new ArrayList<>();

        Calendar harvestDate = null;
        Calendar roundDate = null;
        if (task.isHarvested()) {
            harvestDate = DateHelper.toCalendar(task.harvest_date);
        }

        // แยกรอบการให้ปุ๋ยก่อนกรีด และหลังกรีด
        if (task.isHarvested()) {
            for (Fertilizing_Round fRound : fertilizingRoundList) {

                roundDate = DateHelper.toCalendar(fRound.date);
                // ถ้ารอบการให้ปุ๋ยมากกว่าวันกรีด ไม่ต้อง add ลง fRoundBeforeList
                if (roundDate.after(harvestDate)) {
                    fRoundAfterList.add(fRound);
                } else {
                    fRoundBeforeList.add(fRound);
                }
            }
        } else {
            fRoundBeforeList.addAll(fertilizingRoundList);
        }



        // เพิ่มรอบการให้ปุ๋ยลง listview
        if (viewBeforeHarvast) {
            txtHeader.setText("รอบการให้ปุ๋ยก่อนกรีดยาง");
            txtFormula.setText("สูตรปุ๋ย: สูตร 20-10-12");

            if(fRoundBeforeList.size() > 0) {
                txtNoData.setVisibility(View.GONE);
                lvData.setVisibility(View.VISIBLE);

                adapter = new FertilizingGuideAdapter(this, task, fRoundBeforeList, viewBeforeHarvast);
                lvData.setAdapter(adapter);
            }
        } else {
            txtHeader.setText("รอบการให้ปุ๋ยก่อนหลังยาง");
            txtFormula.setText("สูตรปุ๋ย: สูตร 30-5-18 หรือ สูตร 29-5-18");

            if(fRoundAfterList.size() > 0) {
                txtNoData.setVisibility(View.GONE);
                lvData.setVisibility(View.VISIBLE);

                adapter = new FertilizingGuideAdapter(this, task, fRoundAfterList, viewBeforeHarvast);
                lvData.setAdapter(adapter);
            }
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
