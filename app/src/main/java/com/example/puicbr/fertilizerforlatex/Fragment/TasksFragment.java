package com.example.puicbr.fertilizerforlatex.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.puicbr.fertilizerforlatex.Adapter.TasksAdapter;
import com.example.puicbr.fertilizerforlatex.R;
import com.example.puicbr.fertilizerforlatex.helper.DbHelper;
import com.example.puicbr.fertilizerforlatex.model.Task;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TasksFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView lvTasks = null;
    private TextView tvNoTasksFound = null;
    private List<Task> taskList = null;

    private DbHelper dbHelper = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TasksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TasksFragment newInstance(String param1, String param2) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TasksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();

        if(taskList.size() > 0){
            tvNoTasksFound.setVisibility(View.INVISIBLE);
        }

        TasksAdapter tasksAdapter = new TasksAdapter(getActivity(), taskList);

        lvTasks.setAdapter(tasksAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);

        tvNoTasksFound = (TextView) rootView.findViewById(R.id.tv_no_tasks_found);
        lvTasks = (ListView) rootView.findViewById(R.id.lv_tasks);

        dbHelper = new DbHelper(getActivity());

        loadData();

        if(taskList.size() > 0){
            tvNoTasksFound.setVisibility(View.INVISIBLE);
        }

        TasksAdapter tasksAdapter = new TasksAdapter(getActivity(), taskList);

        lvTasks.setAdapter(tasksAdapter);

        return rootView;
    }

    private void loadData(){
        taskList = dbHelper.selectAllTasks();
    }

}
