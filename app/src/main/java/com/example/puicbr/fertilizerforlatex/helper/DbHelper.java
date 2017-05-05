package com.example.puicbr.fertilizerforlatex.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.puicbr.fertilizerforlatex.model.Constants.FertilizingRoundState;
import com.example.puicbr.fertilizerforlatex.model.Constants.TaskState;
import com.example.puicbr.fertilizerforlatex.model.Fertilizing_Round;
import com.example.puicbr.fertilizerforlatex.model.Formula;
import com.example.puicbr.fertilizerforlatex.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by puiCBR on 7/25/2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Fertilizer";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME_TASK = "task";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_RAI = "rai";
    public static final String COL_TREE_AGE = "tree_age";
    public static final String COL_TREE_AMT = "tree_amt";
    public static final String COL_CREATE_DATE = "create_date";
    public static final String COL_STATE = "state";
    public static final String COL_START_DATE = "start_date";
    public static final String COL_HARVEST_DATE = "harvest_date";
    public static final String COL_LOCATION = "location";

    public static final String TABLE_NAME_FORMULA = "formula";
    public static final String COL_USE_PER_TREE = "use_per_tree";
    public static final String COL_AREA_USED = "area_used";

    public static final String TABLE_NAME_FERTILIZING_ROUND = "fertilizing_round";
    public static final String COL_TASK_ID = "task_id";
    public static final String COL_ROUND = "round";
    public static final String COL_DATE = "create_date";
    public static final String COL_FINISH_STATE = "finish_state";

    // Create Table syntax
    private static final String CREATE_TABLE_TASK =
            "CREATE TABLE " + TABLE_NAME_TASK + " ("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_NAME + " TEXT, "
                    + COL_RAI + " INTEGER, "
                    + COL_TREE_AGE + " INTEGER, "
                    + COL_TREE_AMT + " INTEGER, "
                    + COL_LOCATION + " TEXT, "
                    + COL_CREATE_DATE + " DATETIME, "
                    + COL_START_DATE + " DATETIME, "
                    + COL_HARVEST_DATE + " DATETIME, "
                    + COL_STATE + " TEXT );";

    private static final String CREATE_TABLE_FERTILIZING_ROUND =
            "CREATE TABLE " + TABLE_NAME_FERTILIZING_ROUND + " ("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_TASK_ID + " INTEGER , "
                    + COL_ROUND + " INTEGER, "
                    + COL_TREE_AGE + " INTEGER, "
                    + COL_DATE + " DATETIME, "
                    + COL_FINISH_STATE + " TEXT );";


    private static final String CREATE_TABLE_FORMULA =
            "CREATE TABLE " + TABLE_NAME_FORMULA + " ("
                    + COL_TREE_AGE + " INTEGER PRIMARY KEY , "
                    + COL_USE_PER_TREE + " INTEGER, "
                    + COL_AREA_USED + " TEXT );";


    private static final String EMPTY_DATE_STRING = "0000-00-00";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASK);
        db.execSQL(CREATE_TABLE_FERTILIZING_ROUND);
        db.execSQL(CREATE_TABLE_FORMULA);
        db.execSQL(String.format("INSERT INTO %s VALUES (%d,%d,'%s');", TABLE_NAME_FORMULA, 1, 50, "ใส่รอบต้นรัศมี 30 cm"));
        db.execSQL(String.format("INSERT INTO %s VALUES (%d,%d,'%s');", TABLE_NAME_FORMULA, 6, 70, "ใส่รอบต้นรัศมี 40 cm"));
        db.execSQL(String.format("INSERT INTO %s VALUES (%d,%d,'%s');", TABLE_NAME_FORMULA, 12, 80, "ใส่รอบต้นรัศมี 40 cm"));
        db.execSQL(String.format("INSERT INTO %s VALUES (%d,%d,'%s');", TABLE_NAME_FORMULA, 18, 90, "ใส่รอบต้นรัศมี 50 cm"));
        db.execSQL(String.format("INSERT INTO %s VALUES (%d,%d,'%s');", TABLE_NAME_FORMULA, 24, 100, "ใส่รอบต้นรัศมี 30 cm"));
        db.execSQL(String.format("INSERT INTO %s VALUES (%d,%d,'%s');", TABLE_NAME_FORMULA, 30, 140, "ใส่รอบต้นรัศมี 40 cm"));
        db.execSQL(String.format("INSERT INTO %s VALUES (%d,%d,'%s');", TABLE_NAME_FORMULA, 36, 140, "ใส่รอบต้นรัศมี 40 cm"));
        db.execSQL(String.format("INSERT INTO %s VALUES (%d,%d,'%s');", TABLE_NAME_FORMULA, 42, 140, "ใส่รอบต้นรัศมี 50 cm"));
        db.execSQL(String.format("INSERT INTO %s VALUES (%d,%d,'%s');", TABLE_NAME_FORMULA, 48, 140, "ใส่รอบต้นรัศมี 30 cm"));
        db.execSQL(String.format("INSERT INTO %s VALUES (%d,%d,'%s');", TABLE_NAME_FORMULA, 54, 160, "ใส่รอบต้นรัศมี 40 cm"));
        db.execSQL(String.format("INSERT INTO %s VALUES (%d,%d,'%s');", TABLE_NAME_FORMULA, 60, 160, "ใส่รอบต้นรัศมี 40 cm"));
        db.execSQL(String.format("INSERT INTO %s VALUES (%d,%d,'%s');", TABLE_NAME_FORMULA, 66, 160, "ใส่รอบต้นรัศมี 50 cm"));
        db.execSQL(String.format("INSERT INTO %s VALUES (%d,%d,'%s');", TABLE_NAME_FORMULA, 72, 160, "ใส่รอบต้นรัศมี 30 cm"));
        db.execSQL(String.format("INSERT INTO %s VALUES (%d,%d,'%s');", TABLE_NAME_FORMULA, 78, 160, "ใส่รอบต้นรัศมี 40 cm"));

        // ตรงนี้ไปเพิ่มต่อเอง
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TASK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FERTILIZING_ROUND);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FORMULA);

        onCreate(db);
    }

    public void createTask(Task user_entry){
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");

        ContentValues values = new ContentValues();
        values.put(COL_NAME, user_entry.name);
        values.put(COL_RAI, user_entry.rai);
        values.put(COL_TREE_AGE, user_entry.tree_age);
        values.put(COL_TREE_AMT, user_entry.tree_amt);
        values.put(COL_LOCATION, user_entry.location);
        values.put(COL_CREATE_DATE, dateFormat.format(user_entry.create_date));
        values.put(COL_START_DATE, dateFormat.format(user_entry.start_date));

        if(user_entry.harvest_date != null)
            values.put(COL_HARVEST_DATE, dateFormat.format(user_entry.harvest_date));
        else
            values.put(COL_HARVEST_DATE, EMPTY_DATE_STRING);

        values.put(COL_STATE, user_entry.taskState.toString());

        // Inserting Row
        db.insert(TABLE_NAME_TASK, null, values);
        db.close(); // Closing database connection
    }

    public void updateTask(Task user_entry){
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");

        ContentValues values = new ContentValues();
        values.put(COL_NAME, user_entry.name);
        values.put(COL_RAI, user_entry.rai);
        values.put(COL_TREE_AGE, user_entry.tree_age);
        values.put(COL_TREE_AMT, user_entry.tree_amt);
        values.put(COL_LOCATION, user_entry.location);
        values.put(COL_CREATE_DATE, dateFormat.format(user_entry.create_date));
        values.put(COL_START_DATE, dateFormat.format(user_entry.start_date));

        if(user_entry.harvest_date != null)
            values.put(COL_HARVEST_DATE, dateFormat.format(user_entry.harvest_date));
        else
            values.put(COL_HARVEST_DATE, EMPTY_DATE_STRING);

        values.put(COL_STATE, user_entry.taskState.toString());

        // Inserting Row
        db.update(TABLE_NAME_TASK, values, COL_ID + " = ?",
                new String[]{String.valueOf(user_entry.id)});
        db.close(); // Closing database connection
    }

    public void deleteTask(Task user_entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_TASK, COL_ID + " = ?",
                new String[]{String.valueOf(user_entry.id)});
        db.close();
    }

    public void markTaskAsDelete(Task task){
        task.taskState = TaskState.DELETE;
        updateTask(task);
    }

    public List<Task> selectAllTasks(){
        List<Task> taskList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME_TASK;

        String create_date;
        String start_date;
        String harvest_date;
        String taskState;

        Cursor c =  db.rawQuery(sql, null );
        if(c.moveToFirst()){
            do{
                Task task = new Task();
                task.id = c.getInt(c.getColumnIndex(COL_ID));
                task.name = c.getString(c.getColumnIndex(COL_NAME));
                task.rai = c.getInt(c.getColumnIndex(COL_RAI));
                task.tree_age = c.getInt(c.getColumnIndex(COL_TREE_AGE));
                task.tree_amt = c.getInt(c.getColumnIndex(COL_TREE_AMT));

                create_date = c.getString(c.getColumnIndex(COL_CREATE_DATE));
                task.create_date = dateStringToDate(create_date);

                start_date = c.getString(c.getColumnIndex(COL_START_DATE));
                task.start_date = dateStringToDate(start_date);

                harvest_date = c.getString(c.getColumnIndex(COL_HARVEST_DATE));
                task.harvest_date = dateStringToDate(harvest_date);

                taskState = c.getString(c.getColumnIndex(COL_STATE));
                task.taskState = TaskState.valueOf(taskState.toUpperCase());

                taskList.add(task);
            }while(c.moveToNext());
        }

        return taskList;
    }

    private Date dateStringToDate(String date){
        Date dateOut = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");

        try {
            if(EMPTY_DATE_STRING.equals(date))
                dateOut = null;
            else
                dateOut = dateFormat.parse(date);

            return dateOut;
        }catch (ParseException e) {
            System.out.println("Unparseable using " + dateFormat);
            return null;
        }
    }

    public Task selectTaskById(int id){
        Task user_entry = null;
        List<Task> user_entryList = selectAllTasks();
        for(Task item : user_entryList){
            if(item.id == id){
                user_entry = item;
            }
        }
        return user_entry;
    }

    public Task selectLastTask(){
        List<Task> taskList = selectAllTasks();
        Task lastTask = null;
        for(Task item : taskList){
            lastTask = item;
        }

        return lastTask;
    }

    public List<Formula> selectAllFormula(){
        List<Formula> formulaList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME_FORMULA;

        Cursor c =  db.rawQuery(sql, null );
        if(c.moveToFirst()){
            do{
                Formula formula = new Formula();
                formula.tree_age = c.getInt(c.getColumnIndex(COL_TREE_AGE));
                formula.use_per_tree = c.getInt(c.getColumnIndex(COL_USE_PER_TREE));
                formula.area_used = c.getString(c.getColumnIndex(COL_AREA_USED));

                formulaList.add(formula);
            }while(c.moveToNext());
        }

        return formulaList;
    }

    public Formula selectFormulaByTreeAge(int tree_age){
        List<Formula> formulaList = selectAllFormula();

        // เรียงข้อมูลตาม tree_age จากน้อยไปมาก
        Collections.sort(formulaList, new Comparator<Formula>() {
            public int compare(Formula f1, Formula f2) {
                return f1.tree_age - f2.tree_age;
            }
        });

        Formula lastFormula = null;

        //  loop จาก น้อยไปมาก
        for(Formula formula : formulaList){
            if(formula.tree_age == tree_age){
                return formula;
            } else if(formula.tree_age > tree_age){
                if(lastFormula != null)
                    return lastFormula;
                else
                    return formula;
            }

            lastFormula = formula;
        }

        return null;
    }

    public void createFertilizing_Round(Fertilizing_Round fertilizingRound){
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");

        ContentValues values = new ContentValues();
        values.put(COL_TASK_ID, fertilizingRound.task_id);
        values.put(COL_ROUND, fertilizingRound.round);
        values.put(COL_TREE_AGE, fertilizingRound.tree_age);
        values.put(COL_DATE, dateFormat.format(fertilizingRound.date));
        values.put(COL_FINISH_STATE, fertilizingRound.finish_state.toString());

        // Inserting Row
        db.insert(TABLE_NAME_FERTILIZING_ROUND, null, values);
        db.close(); // Closing database connection
    }

    public void updateFertilizing_Round(Fertilizing_Round fertilizingRound){
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");

        ContentValues values = new ContentValues();
        values.put(COL_TASK_ID, fertilizingRound.task_id);
        values.put(COL_ROUND, fertilizingRound.round);
        values.put(COL_TREE_AGE, fertilizingRound.tree_age);
        values.put(COL_DATE, dateFormat.format(fertilizingRound.date));
        values.put(COL_FINISH_STATE, fertilizingRound.finish_state.toString());

        // Inserting Row
        db.update(TABLE_NAME_FERTILIZING_ROUND, values, COL_ID + " = ?",
                new String[]{String.valueOf(fertilizingRound.id)});
        db.close(); // Closing database connection
    }

    public void deleteFertilizing_Round(Fertilizing_Round fertilizingRound) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_FERTILIZING_ROUND, COL_ID + " = ?",
                new String[]{String.valueOf(fertilizingRound.id)});
        db.close();
    }

    public void deleteFertilizing_RoundByTaskId(int task_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_FERTILIZING_ROUND, COL_TASK_ID + " = ?",
                new String[]{String.valueOf(task_id)});
        db.close();
    }

    public List<Fertilizing_Round> selectFertilizing_RoundByTaskId(int id){
        List<Fertilizing_Round> fertilizingRoundList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = String.format("select * from %s where task_id = %d order by %s", TABLE_NAME_FERTILIZING_ROUND, id, COL_ROUND);

        String dateStr;
        String finishStateStr;

        Cursor c =  db.rawQuery(sql, null );
        if(c.moveToFirst()){
            do{
                Fertilizing_Round fRound = new Fertilizing_Round();
                fRound.id = c.getInt(c.getColumnIndex(COL_ID));
                fRound.task_id = c.getInt(c.getColumnIndex(COL_TASK_ID));
                fRound.round = c.getInt(c.getColumnIndex(COL_ROUND));
                fRound.tree_age = c.getInt(c.getColumnIndex(COL_TREE_AGE));

                dateStr = c.getString(c.getColumnIndex(COL_DATE));
                fRound.date = dateStringToDate(dateStr);

                finishStateStr = c.getString(c.getColumnIndex(COL_FINISH_STATE));
                fRound.finish_state = FertilizingRoundState.valueOf(finishStateStr.toUpperCase());

                fertilizingRoundList.add(fRound);
            }while(c.moveToNext());
        }

        return fertilizingRoundList;
    }
}
