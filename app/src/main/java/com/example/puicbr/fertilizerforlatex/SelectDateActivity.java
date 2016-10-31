package com.example.puicbr.fertilizerforlatex;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.app.Dialog;

import com.example.puicbr.fertilizerforlatex.Global.ViewInfo;
import com.example.puicbr.fertilizerforlatex.helper.DbHelper;
import com.example.puicbr.fertilizerforlatex.model.UserEntry;

public class SelectDateActivity extends Activity {

    private DatePicker datePicker = null;
    private UserEntry user_entry = null;
    private DbHelper db = null;

    private static final String DEBUG_TAG = "SelectDateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);

        datePicker = (DatePicker) findViewById(R.id.date_start);

//        Gson gson = new Gson();
//        String json = savedInstanceState.getString("user_entry");
//        user_entry = gson.fromJson(json, UserEntry.class);

        db = new DbHelper(this);
    }

    public void onClick(View v) {
        String dateStr = String.format("%d-%d-%d", datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

        ViewInfo.viUserEntry.date = dateStr;

//        db.createUserEntry(user_entry);
        Log.d(DEBUG_TAG, ViewInfo.viUserEntry.toString());


        Dialog dialog= MyDialogBuilder.CreateDialog(this, "บันทึกข้อมูลเรียบร้อย");
        dialog.show();
    }
}
