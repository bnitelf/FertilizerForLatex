package com.example.puicbr.fertilizerforlatex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.puicbr.fertilizerforlatex.Global.ViewInfo;
import com.example.puicbr.fertilizerforlatex.model.UserEntry;
import com.google.gson.Gson;

public class StartActivity extends Activity implements View.OnClickListener {

    private EditText edtArea = null;
    private EditText edtAge = null;
    private EditText edtCount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);

        edtArea = (EditText) findViewById(R.id.edit_area);
        edtAge = (EditText) findViewById(R.id.edit_age);
        edtCount = (EditText) findViewById(R.id.edit_count);
    }

    @Override
    public void onClick(View v) {

        if (edtArea.getText().length() == 0 ||
                edtAge.getText().length() == 0 ||
                edtCount.getText().length() == 0) {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบ" ,Toast.LENGTH_LONG).show();
        }

        int area = Integer.parseInt(edtArea.getText().toString());
        int age = Integer.parseInt(edtAge.getText().toString());
        int count = Integer.parseInt(edtCount.getText().toString());

        if(ViewInfo.viUserEntry == null)
        {
            ViewInfo.viUserEntry = new UserEntry();
        }

        ViewInfo.viUserEntry.rai = area;
        ViewInfo.viUserEntry.tree_age = age;
        ViewInfo.viUserEntry.tree_amt = count;


//        Gson gson = new Gson();
//        String json = gson.toJson(user_entry);

        Intent a = new Intent(this, SelectDateActivity.class);
//        a.putExtra("user_entry", json);
        startActivity(a);
    }
}
