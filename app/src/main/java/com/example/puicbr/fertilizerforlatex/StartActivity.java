package com.example.puicbr.fertilizerforlatex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.puicbr.fertilizerforlatex.Global.ViewInfo;
import com.example.puicbr.fertilizerforlatex.model.Task;

public class StartActivity extends AppCompatActivity {

    private RelativeLayout rootContainer = null;
    private EditText edtName = null;
    private EditText edtArea = null;
    private EditText edtAge = null;
    private EditText edtCount = null;

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
        edtAge = (EditText) findViewById(R.id.edit_age);
        edtCount = (EditText) findViewById(R.id.edit_count);
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
            if (edtAge.getText().length() == 0) {
                edtAge.setError("กรุณาใส่อายุต้นยางด้วย");
            }
            if (edtCount.getText().length() == 0) {
                edtCount.setError("กรุณาใส่จำนวนต้นยางด้วย");
            }

            if (edtName.getText().length() > 0 ||
                    edtArea.getText().length() > 0 ||
                    edtAge.getText().length() > 0 ||
                    edtCount.getText().length() > 0) {

                int area = Integer.parseInt(edtArea.getText().toString());
                int age = Integer.parseInt(edtAge.getText().toString());
                int count = Integer.parseInt(edtCount.getText().toString());

                Snackbar.make(rootContainer, "Add Successful", Snackbar.LENGTH_LONG).show();
                finish();
            }


        }
        return true;
    }
}
