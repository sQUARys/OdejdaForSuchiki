package com.example.mac.suchik;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

public class DBOperations {

    private Context mCtx;
    private int id;

    DB db;

    public DBOperations(Context context) {
        this.mCtx = context;
    }

    public void addItem(ClothesData item){
        db = new DB(mCtx);
        db.open();

        Cursor c = db.getData("categories", new String[]{db.CATEGORY_COLUMN_ID}, db.CATEGORY_COLUMN_NAME + " = ?",
                new String[]{item.category}, null, null, null);

        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex(db.CATEGORY_COLUMN_ID);
            id = c.getInt(idColIndex);

        } else
            Log.d("DBOperation", "0 rows");

        db.addRec(id, item.name, item.minTemp, item.maxTemp, item.rain, item.wind, item.cloud, item.color);

        db.close();
    }

    public void deleteItem(ClothesData item){
        db = new DB(mCtx);
        db.open();

        Cursor c = db.getData("clothes", new String[]{db.COLUMN_ID}, db.COLUMN_NAME + " = ?",
                new String[]{item.name}, null, null, null);

        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex(db.COLUMN_ID);
            id = c.getInt(idColIndex);

        } else
            Log.d("DBOperation", "0 rows");

        db.delRec(id);

        db.close();
    }


}
