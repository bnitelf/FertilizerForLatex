package com.example.fertilizer.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fertilizer.model.Formula;
import com.example.fertilizer.model.User_Entry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by puiCBR on 7/25/2016.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Fertilizer";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME_USER_ENTRY = "user_entry";
    public static final String COL_ID = "id";
    public static final String COL_RAI = "rai";
    public static final String COL_TREE_AGE = "tree_age";
    public static final String COL_TREE_AMT = "tree_amt";
    public static final String COL_DATE = "date";

    public static final String TABLE_NAME_FORMULA = "formula";
    public static final String COL_USE_PER_TREE = "use_per_tree";
    public static final String COL_AREA_USED = "area_used";

    // Create Table syntax
    private static final String CREATE_TABLE_USER_ENTRY =
            "CREATE TABLE " + TABLE_NAME_USER_ENTRY + " ("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_RAI + " INTEGER, "
                    + COL_TREE_AGE + " INTEGER, "
                    + COL_TREE_AMT + " INTEGER, "
                    + COL_DATE + " DATATIME  );";

    private static final String CREATE_TABLE_FORMULA =
            "CREATE TABLE " + TABLE_NAME_FORMULA + " ("
                    + COL_TREE_AGE + " INTEGER PRIMARY KEY , "
                    + COL_USE_PER_TREE + " INTEGER, "
                    + COL_AREA_USED + " TEXT);";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER_ENTRY);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER_ENTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FORMULA);

        onCreate(db);
    }

    public void createUserEntry(User_Entry user_entry){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_RAI, user_entry.rai);
        values.put(COL_TREE_AGE, user_entry.tree_age);
        values.put(COL_TREE_AMT, user_entry.tree_amt);
        values.put(COL_DATE, user_entry.date);

        // Inserting Row
        db.insert(TABLE_NAME_USER_ENTRY, null, values);
        db.close(); // Closing database connection
    }

    public void updateUserEntry(User_Entry user_entry){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_RAI, user_entry.rai);
        values.put(COL_TREE_AGE, user_entry.tree_age);
        values.put(COL_TREE_AMT, user_entry.tree_amt);
        values.put(COL_DATE, user_entry.date);

        // Inserting Row
        db.update(TABLE_NAME_USER_ENTRY, values, COL_ID + " = ?",
                new String[] { String.valueOf(user_entry.id) });
        db.close(); // Closing database connection
    }

    public void deleteContact(User_Entry user_entry) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_USER_ENTRY, COL_ID + " = ?",
                new String[] { String.valueOf(user_entry.id) });
        db.close();
    }

    public List<User_Entry> selectAllUserEntry(){
        List<User_Entry> user_entryList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME_USER_ENTRY;

        Cursor c =  db.rawQuery(sql, null );
        if(c.moveToFirst()){
            do{
                User_Entry ue = new User_Entry();
                ue.id = c.getInt(c.getColumnIndex(COL_ID));
                ue.rai = c.getInt(c.getColumnIndex(COL_RAI));
                ue.tree_age = c.getInt(c.getColumnIndex(COL_TREE_AGE));
                ue.tree_amt = c.getInt(c.getColumnIndex(COL_TREE_AMT));
                ue.date = c.getString(c.getColumnIndex(COL_DATE));

                user_entryList.add(ue);
            }while(c.moveToNext());
        }

        return user_entryList;
    }

    public User_Entry selectUserEntryById(int id){
        User_Entry user_entry = null;
        List<User_Entry> user_entryList = selectAllUserEntry();
        for(User_Entry item : user_entryList){
            if(item.id == id){
                user_entry = item;
            }
        }
        return user_entry;
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

}
