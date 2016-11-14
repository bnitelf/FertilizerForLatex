package com.example.puicbr.fertilizerforlatex;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.puicbr.fertilizerforlatex.Fragment.DeletedTasksFragment;
import com.example.puicbr.fertilizerforlatex.Fragment.FertilizingCalculatorFragment;
import com.example.puicbr.fertilizerforlatex.Fragment.ReportFragment;
import com.example.puicbr.fertilizerforlatex.Fragment.TasksFragment;
import com.example.puicbr.fertilizerforlatex.Fragment.UpComingTasksFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(MainActivity.this, StartActivity.class);
                startActivity(a);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set Tasks fragment as Home
        Fragment tasksFragment = TasksFragment.newInstance("param1", "param2");
        setMainContentFragment("Tasks", tasksFragment);
        navigationView.setCheckedItem(R.id.nav_tasks);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {

            Intent a = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(a);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.nav_tasks) {

            fragment = TasksFragment.newInstance("param1", "param2");
            setMainContentFragment("Tasks", fragment);
            navigationView.setCheckedItem(R.id.nav_tasks);

        } else if (id == R.id.nav_upcoming_tasks) {

            fragment = UpComingTasksFragment.newInstance("param1", "param2");
            setMainContentFragment("Upcoming Tasks", fragment);
            navigationView.setCheckedItem(R.id.nav_upcoming_tasks);

        } else if (id == R.id.nav_deleted_tasks) {

            fragment = DeletedTasksFragment.newInstance("param1", "param2");
            setMainContentFragment("Deleted Tasks", fragment);
            navigationView.setCheckedItem(R.id.nav_deleted_tasks);

        } else if (id == R.id.nav_fertilizing_calculator) {

            fragment = FertilizingCalculatorFragment.newInstance("param1", "param2");
            setMainContentFragment("Fertilizing Calculator", fragment);
            navigationView.setCheckedItem(R.id.nav_fertilizing_calculator);

        } else if (id == R.id.nav_report) {

            fragment = ReportFragment.newInstance("param1", "param2");
            setMainContentFragment("Report", fragment);
            navigationView.setCheckedItem(R.id.nav_report);

        } else if (id == R.id.nav_send) {



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setMainContentFragment(String title, Fragment fragment){
        getSupportActionBar().setTitle(title);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
    }
}
