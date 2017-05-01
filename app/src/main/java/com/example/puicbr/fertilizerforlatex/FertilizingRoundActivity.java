package com.example.puicbr.fertilizerforlatex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.puicbr.fertilizerforlatex.Adapter.FertilizingRoundAdapter;
import com.example.puicbr.fertilizerforlatex.helper.DateHelper;
import com.example.puicbr.fertilizerforlatex.helper.DbHelper;
import com.example.puicbr.fertilizerforlatex.model.Fertilizing_Round;
import com.example.puicbr.fertilizerforlatex.model.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FertilizingRoundActivity extends AppCompatActivity {

    private ListView lvBefore = null;
    private ListView lvAfter = null;
    private TextView txtNoDataBefore = null;
    private TextView txtNoDataAfter = null;

    private int task_id = -1;
    private Task task = null;

    private DbHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizing_round);

        lvBefore = (ListView) findViewById(R.id.lvBefore);
        lvAfter = (ListView) findViewById(R.id.lvAfter);
        txtNoDataBefore = (TextView) findViewById(R.id.txtNoDataBefore);
        txtNoDataAfter = (TextView) findViewById(R.id.txtNoDataAfter);

        Intent mIntent = getIntent();
        task_id = mIntent.getIntExtra("task_id", 0);

        dbHelper = new DbHelper(this);
        task = dbHelper.selectTaskById(task_id);

        List<Fertilizing_Round> fertilizingRoundList = dbHelper.selectFertilizing_RoundByTaskId(task_id);

        // รายการรอบการให้ปุ๋ยก่อนกรีด
        List<Fertilizing_Round> fRoundBeforeList = new ArrayList<>();

        // รายการรอบการให้ปุ๋ยหลังกรีด
        List<Fertilizing_Round> fRoundAfterList = null;

        Calendar harvestDate = null;
        Calendar roundDate = null;
        if(task.isHarvested()) {
            harvestDate = DateHelper.toCalendar(task.harvest_date);
        }

        // แยกรอบการให้ปุ๋ยก่อนกรีด
        for(Fertilizing_Round fRound : fertilizingRoundList){
            if(task.isHarvested()){
                roundDate = DateHelper.toCalendar(fRound.date);
                // ถ้ารอบการให้ปุ๋ยมากกว่าวันกรีด ไม่ต้อง add ลง fRoundBeforeList
                if(roundDate.after(harvestDate)){
                    break;
                }
            }

            fRoundBeforeList.add(fRound);
        }

        // เพิ่มรอบการให้ปุ๋ยลง listview
        if(fRoundBeforeList.size() > 0){
            txtNoDataBefore.setVisibility(View.GONE);
            lvBefore.setVisibility(View.VISIBLE);

            FertilizingRoundAdapter adapter = new FertilizingRoundAdapter(this, fRoundBeforeList);
            lvBefore.setAdapter(adapter);
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fertilizing_round, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
