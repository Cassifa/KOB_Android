package com.example.kob_android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kob_android.net.responseData.pojo.User;
import com.example.kob_android.pojo.RecordItem;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-06-03  16:04
 * @Description:
 */
public class RecordItemDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "kob";
    private static final String TABLE_NAME = "record_item";
    private static RecordItemDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    private RecordItemDBHelper(Context context) {
        //单例模式
        super(context, DB_NAME, null, 1);
    }

    public static RecordItemDBHelper getInstance(Context context) {
        if (mHelper == null) mHelper = new RecordItemDBHelper(context);
        return mHelper;
    }

    public SQLiteDatabase openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    public SQLiteDatabase openWriteLink() {
        if (mWDB == null || !mWDB.isOpen()) {
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    public void closeLink() {
        if (mRDB != null && mRDB.isOpen()) mRDB.close();
        if (mWDB != null && mWDB.isOpen()) mWDB.close();
    }

    @Override
    //创建数据库
    public void onCreate(SQLiteDatabase db) {
        String SQL = "CREATE TABLE if NOT EXISTS " + TABLE_NAME + "(" +
                "    a_photo    varchar(100) not null," +
                "    a_username varchar(100) not null," +
                "    b_photo    varchar(100) not null," +
                "    b_username varchar(100) not null," +
                "    record     varchar(500) not null," +
                "    result     varchar(10) not null); ";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据库版本更新
    }

    public void insert(RecordItem item) {
        ContentValues values = new ContentValues();
        values.put("a_photo", item.getA_photo());
        values.put("a_username", item.getA_username());
        values.put("b_photo", item.getB_photo());
        values.put("b_username", item.getB_username());
        values.put("record", new Gson().toJson(item.getRecord()));
        values.put("result", item.getResult());
        mWDB.insert(TABLE_NAME, null, values);
    }
}
