package com.example.puicbr.fertilizerforlatex;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.puicbr.fertilizerforlatex.helper.DbHelper;
import com.example.puicbr.fertilizerforlatex.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private DbHelper dbHelper = null;
    private ListView lvTaskList = null;
    private List<Task> taskList = null;

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_tasklist);
        lvTaskList = (ListView) findViewById(R.id.task_list);
        dbHelper = new DbHelper(this);
        taskList = dbHelper.selectAllTasks();
        List<String> taskNameList = new ArrayList<String>();

        for (Task t : taskList) {
            taskNameList.add(t.name);
        }

        String[] values = taskNameList.toArray(new String[taskNameList.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);

        lvTaskList.setAdapter(adapter);
        lvTaskList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Task selectedTask = taskList.get(position);
        Toast.makeText(this, selectedTask.name + " selected", Toast.LENGTH_LONG).show();

        Intent myIntent = new Intent(TaskListActivity.this, TaskDetailActivity.class);
        myIntent.putExtra("task_id",selectedTask.id );
        startActivity(myIntent);
    }

}

