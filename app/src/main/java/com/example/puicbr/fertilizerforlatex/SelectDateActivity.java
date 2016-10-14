package com.example.puicbr.fertilizerforlatex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.fertilizer.helper.DbHelper;
import com.example.fertilizer.model.User_Entry;
import com.google.gson.Gson;

public class SelectDateActivity extends AppCompatActivity {

    private DatePicker datePicker = null;
    private User_Entry user_entry = null;
    private DbHelper db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);

        datePicker = (DatePicker) findViewById(R.id.date_start);

        Gson gson = new Gson();
        String json = savedInstanceState.getString("user_entry");
        user_entry = gson.fromJson(json, User_Entry.class);

        db = new DbHelper(this);
    }

    public void onClick(View v) {
        String dateStr = String.format("%d-%d-%d", datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

        user_entry.date = dateStr;

        db.createUserEntry(user_entry);

        Toast.makeText(this, "บันทึกข้อมูลเรียบร้อย", Toast.LENGTH_LONG);
    }
}
