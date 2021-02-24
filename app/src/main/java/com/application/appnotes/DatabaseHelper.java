package com.application.appnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "AppNotes";
    private static final String TABLE_NAME = "tbl_notes";
    private static final String KEY_JUDUL = "judul";
    private static final String KEY_DESKRIPSI = "deskripsi";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "Create Table " + TABLE_NAME + "(" + KEY_JUDUL + " TEXT PRIMARY KEY, "+KEY_DESKRIPSI+" TEXT "+")";
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = ("drop table if exists " + TABLE_NAME);
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert(PersonBean personBean) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JUDUL, personBean.getJudul());
        values.put(KEY_DESKRIPSI, personBean.getDeskripsi());
        db.insert(TABLE_NAME, null, values);
    }

    public List<PersonBean> selectUserData() {
        ArrayList<PersonBean> userList = new ArrayList<PersonBean>();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {KEY_JUDUL, KEY_DESKRIPSI};
        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while (c.moveToNext()) {
            String judul = c.getString(0);
            String deskripsi = c.getString(1);
            PersonBean personBean = new PersonBean();
            personBean.setJudul(judul);
            personBean.setDeskripsi(deskripsi);
            userList.add(personBean);
        }
        return userList;
    }

    public void delete(String judul) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = KEY_JUDUL + "='" + judul + "'";
        db.delete(TABLE_NAME, whereClause, null);
    }

    public void update(PersonBean personBean) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DESKRIPSI, personBean.getDeskripsi());
        String whereClause = KEY_JUDUL + "='" + personBean.getJudul() + "'";
        db.update(TABLE_NAME, values, whereClause, null);
    }
}