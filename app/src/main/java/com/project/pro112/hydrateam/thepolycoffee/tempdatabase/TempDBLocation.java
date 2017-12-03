package com.project.pro112.hydrateam.thepolycoffee.tempdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.pro112.hydrateam.thepolycoffee.models.AddressLocation;

import java.util.ArrayList;

/**
 * Created by Huyn_TQT on 12/3/2017.
 */

public class TempDBLocation extends SQLiteOpenHelper {

    private static final String TABLE_LOCATION = "TABLE_LOCATION";
    private static final String ID = "ID";
    private static final String ADDRESS = "ADDRESS";
    private static final String LATITUDE = "LATITUDE";
    private static final String LONGITUDE = "LONGITUDE";

    private SQLiteDatabase db;

    public TempDBLocation(Context context) {
        super(context, "tempdblocation", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTABLE_LOCATION = "CREATE TABLE " + TABLE_LOCATION + " (" +
                ID + " TEXT, " +
                ADDRESS + " TEXT, " +
                LATITUDE + " REAL, " +
                LONGITUDE + " REAL);";
        db.execSQL(createTABLE_LOCATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String droptb2 = "DROP TABLE IF EXISTS " + TABLE_LOCATION + ";";
        db.execSQL(droptb2);
    }

    public long insertLocation(AddressLocation addressLocation) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, addressLocation.getId());
        values.put(ADDRESS, addressLocation.getAddress());
        values.put(LATITUDE, addressLocation.getLatitude());
        values.put(LONGITUDE, addressLocation.getLongitude());
        return db.insertOrThrow(TABLE_LOCATION, null, values);
    }

    public int updateLocation(AddressLocation addressLocation) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, addressLocation.getId());
        values.put(ADDRESS, addressLocation.getAddress());
        values.put(LATITUDE, addressLocation.getLatitude());
        values.put(LONGITUDE, addressLocation.getLongitude());
        return db.update(TABLE_LOCATION, values, ID + " = ?", new String[]{String.valueOf(addressLocation.getId())});
    }

    public ArrayList<AddressLocation> getLocation(String id) {
        String sql = "SELECT * FROM " + TABLE_LOCATION + " WHERE " + ID + "= '" + id + "'";
        return getLocations(sql);
    }

    public ArrayList<AddressLocation> getAllLocation() {
        String sql = "SELECT * FROM " + TABLE_LOCATION;
        return getLocations(sql);
    }

    public ArrayList<AddressLocation> getLocations(String sql, String... SelectionArgs) {
        db = this.getReadableDatabase();
        ArrayList<AddressLocation> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, SelectionArgs);
        while (cursor.moveToNext()) {
            AddressLocation addressLocation = new AddressLocation();
            addressLocation.setId(cursor.getString(cursor.getColumnIndex(ID)));
            addressLocation.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
            addressLocation.setLatitude(cursor.getDouble(cursor.getColumnIndex(LATITUDE)));
            addressLocation.setLongitude(cursor.getDouble(cursor.getColumnIndex(LONGITUDE)));
            list.add(addressLocation);
        }
        cursor.close();
        return list;
    }
}
