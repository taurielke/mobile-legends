package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DisciplineDB {
    private DisciplineDB.DBHelper dbHelper;

    public DisciplineDB(Context context){
        dbHelper = new DisciplineDB.DBHelper(context);
    }

    public Discipline get(Discipline discipline){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("disciplines", null, "id = ?",
                new String[] {String.valueOf(discipline.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int semesterIndex = c.getColumnIndex("semester");
            int teacherIdIndex = c.getColumnIndex("teacherId");
            Discipline d = new Discipline();
            d.setId(c.getInt(idIndex));
            d.setName(c.getString(nameIndex));
            d.setSemester(c.getInt(semesterIndex));
            d.setTeacherId(c.getInt(teacherIdIndex));

            if (d.getId() == (discipline.getId())) {
                dbHelper.close();
                return d;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Discipline discipline){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", discipline.getName());
        cv.put("semester", discipline.getSemester());
        cv.put("teacherId", discipline.getTeacherId());
        long disciplineId = db.insert("disciplines", null, cv);
        dbHelper.close();
    }

    public void update(Discipline discipline){
        if (get(discipline) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", discipline.getName());
        cv.put("semester", discipline.getSemester());
        cv.put("teacherId", discipline.getTeacherId());
        db.update("disciplines", cv, "id = ?", new String[] {String.valueOf(discipline.getId())});
        dbHelper.close();
    }

    public void delete(Discipline discipline){
        if(get(discipline) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("disciplines", "id = " + discipline.getId(), null);
        dbHelper.close();
    }

    public List<Discipline> readAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Discipline> disciplineList = new ArrayList<Discipline>();
        Cursor c = db.query("disciplines", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int semesterIndex = c.getColumnIndex("semester");
            int teacherIdIndex = c.getColumnIndex("teacherId");
            do{
                Discipline d = new Discipline();
                d.setId(c.getInt(idIndex));
                d.setName(c.getString(nameIndex));
                d.setSemester(c.getInt(semesterIndex));
                d.setTeacherId(c.getInt(teacherIdIndex));
                disciplineList.add(d);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return disciplineList;
    }

    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("disciplines", null, null);
        dbHelper.close();
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "disciplines", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table disciplines ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "semester integer,"
                    + "teacherId integer" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
