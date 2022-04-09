package com.marathon.airracing.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.marathon.airracing.R;
import com.marathon.airracing.models.RaceModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RacingRecord";
    private static final String TABLE_NAME = "record";
    private static final String COLUMN_RACE_NAME = "racename";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_TICKETS = "tickets";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_PRICE = "price";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME +" (" + COLUMN_RACE_NAME + " text, " + COLUMN_DATE + " text, " + COLUMN_TIME + " text, " + COLUMN_TICKETS + " text, " + COLUMN_TYPE + " text, " + COLUMN_PRICE + " text) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String getDBName() {
        return DATABASE_NAME;
    }

    //Method to insert DB records
    public boolean insertRecord(RaceModel raceModel) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(COLUMN_RACE_NAME, raceModel.getRaceName());
            contentValues.put(COLUMN_DATE, raceModel.getDate());
            contentValues.put(COLUMN_TIME, raceModel.getTime());
            contentValues.put(COLUMN_TICKETS, raceModel.getTickets());
            contentValues.put(COLUMN_PRICE, raceModel.getPrice());
            contentValues.put(COLUMN_TYPE, raceModel.getTicketType());

            database.insert(TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //Method to fetch DB Data
    public List<RaceModel> fetchRecords() {
        List<RaceModel> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        try {

            Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(new RaceModel(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RACE_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TICKETS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE))));
                cursor.moveToNext();
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
