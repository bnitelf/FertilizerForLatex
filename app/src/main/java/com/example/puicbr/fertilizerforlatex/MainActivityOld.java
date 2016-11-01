package com.example.puicbr.fertilizerforlatex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivityOld extends AppCompatActivity
        implements View.OnClickListener
                    {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);

        Button btnAbout = (Button) findViewById(R.id.about_button);
        btnAbout.setOnClickListener(this);
        Button btnStart = (Button) findViewById(R.id.start_button);
        btnStart.setOnClickListener(this);
        Button btnExit = (Button) findViewById(R.id.exit_button);
        btnExit.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about_button:
                Intent a = new Intent(MainActivityOld.this,AboutActivity.class);
                startActivity(a);
                break;

            case R.id.start_button:
                Intent s = new Intent(MainActivityOld.this,StartActivity.class);
                startActivity(s);
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
