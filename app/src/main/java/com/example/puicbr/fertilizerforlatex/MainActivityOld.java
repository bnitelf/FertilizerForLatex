package com.example.puicbr.fertilizerforlatex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.puicbr.fertilizerforlatex.helper.AlarmUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivityOld extends AppCompatActivity
        implements View.OnClickListener
                    {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);

        Button btnAbout = (Button) findViewById(R.id.about_button);
        btnAbout.setOnClickListener(this);
        Button btnBegin = (Button) findViewById(R.id.begin_button);
        btnBegin.setOnClickListener(this);
        Button btnExit = (Button) findViewById(R.id.exit_button);
        btnExit.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about_button:
                Intent a = new Intent(MainActivityOld.this,AboutActivity.class);
                startActivity(a);

//                // Test alarm
//                Calendar calendar = new GregorianCalendar();
//                calendar.add(Calendar.DATE, 2);
//                AlarmUtil.setReminder(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                break;

            case R.id.begin_button:
                Intent b = new Intent(MainActivityOld.this,Sub_main.class);
                startActivity(b);
                break;

            case R.id.exit_button:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}
