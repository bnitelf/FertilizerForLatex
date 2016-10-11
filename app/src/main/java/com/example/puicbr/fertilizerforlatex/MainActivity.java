package com.example.puicbr.fertilizerforlatex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener
                    {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAbout = (Button) findViewById(R.id.about_button);
        btnAbout.setOnClickListener(this);
        Button btnStart = (Button) findViewById(R.id.start_button);
        btnStart.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about_button:
                Intent a = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(a);
                break;

            case R.id.start_button:
                Intent s = new Intent(MainActivity.this,StartActivity.class);
                startActivity(s);
                break;
        }
    }
}
