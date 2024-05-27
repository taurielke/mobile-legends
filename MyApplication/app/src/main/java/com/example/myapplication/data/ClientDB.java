package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ClientDB {
    private ClientDB.DBHelper dbHelper;

    public ClientDB(Context context){
        dbHelper = new ClientDB.DBHelper(context);
    }

    public Client get(Client client){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("clients", null, "id = ?",
                new String[] {String.valueOf(client.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int tourIndex = c.getColumnIndex("tour");
            int isClientIndex = c.getColumnIndex("isClient");
            Client d = new Client();
            d.setId(c.getInt(idIndex));
            d.setName(c.getString(nameIndex));
            d.setTour(c.getString(tourIndex));
            d.setClient(c.getInt(isClientIndex));

            if (d.getId() == (client.getId())) {
                dbHelper.close();
                return d;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Client client){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", client.getName());
        cv.put("tour", client.getTour());
        cv.put("isClient", client.getIsClient());
        long clientId = db.insert("clients", null, cv);
        dbHelper.close();
    }

    public void update(Client client){
        if (get(client) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", client.getName());
        cv.put("tour", client.getTour());
        cv.put("isClient", client.getIsClient());
        db.update("clients", cv, "id = ?", new String[] {String.valueOf(client.getId())});
        dbHelper.close();
    }

    public void delete(Client client){
        if(get(client) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("clients", "id = " + client.getId(), null);
        dbHelper.close();
    }

    public List<Client> readAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Client> clientList = new ArrayList<Client>();
        Cursor c = db.query("clients", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int tourIndex = c.getColumnIndex("tour");
            int isClientIndex = c.getColumnIndex("isClient");
            do{
                Client d = new Client();
                d.setId(c.getInt(idIndex));
                d.setName(c.getString(nameIndex));
                d.setTour(c.getString(tourIndex));
                d.setClient(c.getInt(isClientIndex));
                clientList.add(d);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return clientList;
    }

    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("clients", null, null);
        dbHelper.close();
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "clients", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table clients ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "tour text,"
                    + "isClient integer" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
