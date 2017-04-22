package com.example.puicbr.fertilizerforlatex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Sub_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_sub_main);

        //calpage

        Button btnStart = (Button) findViewById(R.id.start_button);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sub_main.this, StartActivity.class);
                Sub_main.this.startActivity(intent);
            }
        });


    }

    public void test(){

    }

    public void test2(){
        // Hi I'm Nut
    }
}
