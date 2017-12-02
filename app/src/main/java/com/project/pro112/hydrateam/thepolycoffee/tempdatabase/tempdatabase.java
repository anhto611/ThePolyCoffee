package com.project.pro112.hydrateam.thepolycoffee.tempdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.pro112.hydrateam.thepolycoffee.models.OrderedFood;

import java.util.ArrayList;

/**
 * Created by VI on 02/12/2017.
 */

public class tempdatabase extends SQLiteOpenHelper{
    private static final String TABLE_FOODS = "TABLE_FOODS";
    private static final String NAME = "NAME";
    private static final String AMOUNT = "AMOUNT";
    private static final String PRICE = "PRICE";
    private static final String IMAGE = "IMAGE";
    private static final String DES = "DES";
    private static final String ID = "_ID";


    private SQLiteDatabase db;
    public tempdatabase(Context context) {
        super(context, "tempdatabase", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTABLE_FOODS = "CREATE TABLE " + TABLE_FOODS + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                AMOUNT + " INTEGER, " +
                PRICE + " REAL, " +
                IMAGE + " TEXT, " +
                DES + " TEXT);";
        db.execSQL(createTABLE_FOODS);
    }

    public void deleteAlldata() {
        db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_FOODS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String droptb2 = "DROP TABLE IF EXISTS " + TABLE_FOODS + ";";
        db.execSQL(droptb2);
    }

    public long insertFood(OrderedFood food) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, food.getName());
        values.put(AMOUNT, food.getAmount());
        values.put(DES, food.getDiscription());
        values.put(PRICE, food.getPrice());
        values.put(IMAGE, food.getImage());
        return db.insertOrThrow(TABLE_FOODS, null, values);
    }

    public ArrayList<OrderedFood> getOrderedFoods() {
        String sql = "SELECT * FROM " + TABLE_FOODS;
        return getOrderedFoods(sql);
    }

    public ArrayList<OrderedFood> getOrderedFoods(String sql, String... SelectionArgs) {
        db = this.getReadableDatabase();
        ArrayList<OrderedFood> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, SelectionArgs);
        while (cursor.moveToNext()) {
            OrderedFood food = new OrderedFood();
            food.set_id(cursor.getInt(cursor.getColumnIndex(ID)));
            food.setAmount(cursor.getInt(cursor.getColumnIndex(AMOUNT)));
            food.setPrice(cursor.getDouble(cursor.getColumnIndex(PRICE)));
            food.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            food.setDiscription(cursor.getString(cursor.getColumnIndex(DES)));
            food.setImage(cursor.getString(cursor.getColumnIndex(IMAGE)));
            list.add(food);
        }
        cursor.close();
        return list;
    }

    public int updateOrderedFood(OrderedFood food) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,food.getName());
        values.put(DES,food.getDiscription());
        values.put(AMOUNT,food.getAmount());
        values.put(IMAGE,food.getImage());
        values.put(PRICE,food.getPrice());
        return db.update(TABLE_FOODS, values, NAME + " = ?", new String[]{String.valueOf(food.getName())});
    }

    public int deleteOrderedFood(String name) {
        db = this.getWritableDatabase();
        return db.delete(TABLE_FOODS, NAME + " = ?", new String[]{String.valueOf(name)});
    }
}
