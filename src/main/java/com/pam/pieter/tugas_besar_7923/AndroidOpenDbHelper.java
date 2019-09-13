package com.pam.pieter.tugas_besar_7923;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by pieter on 11/23/2016.
 */
public class AndroidOpenDbHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME="db_u";
    public static final int DB_VERSION=1;

    public static String Table_Name="data_us";
    public static String Column_Name_Username="username";
    public static String Column_Name_Password="password";

    SQLiteDatabase sqliteDB;

    public AndroidOpenDbHelper(Context context){
        super(context, DB_NAME,null,DB_VERSION);
        sqliteDB=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sqlquery="create table if not exists " + Table_Name + " (" +
                BaseColumns._ID + " integer primary key autoincrement, " +
                Column_Name_Username + " text not null, " +
                Column_Name_Password + " text not null);";
        db.execSQL(sqlquery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<User> getData()
    {
        ArrayList<User> userlist=new ArrayList<User>();

        Cursor cursor=sqliteDB.query(Table_Name,null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            String u=cursor.getString(
                    cursor.getColumnIndex(Column_Name_Username));
            String p=cursor.getString(
                    cursor.getColumnIndex(Column_Name_Password));

            User user=new User();
            user.setUsername(u);
            user.setPassword(p);
            userlist.add(user);
        }
        return userlist;
    }

    public ArrayList<String> showUsernameInList()
    {
        ArrayList<String> userlist=new ArrayList<String>();

        Cursor cursor=sqliteDB.query(Table_Name,null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            String u=cursor.getString(
                    cursor.getColumnIndex(Column_Name_Username));
            userlist.add(u);
        }
        return userlist;
    }

    public void insert(User user){
        ContentValues contentValues=new ContentValues();

        contentValues.put(Column_Name_Username, user.getUsername());
        contentValues.put(Column_Name_Password, user.getPassword());

        sqliteDB.insert(Table_Name,null,contentValues);
        sqliteDB.close();
    }



}
