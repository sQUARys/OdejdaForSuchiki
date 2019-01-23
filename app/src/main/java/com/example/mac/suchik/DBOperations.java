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


        db.addRec(item.category, item.name, item.minTemp, item.maxTemp, item.rain, item.wind, item.cloud, item.color);


        Cursor c = db.getAllData("clothes");
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("_id");
            int nameColIndex = c.getColumnIndex("name");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("clothes",
                        "ID = " + c.getString(idColIndex) +
                                ", name = " + c.getString(nameColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d("clothes", "0 rows");
        c.close();

        db.close();
    }

    public void deleteItem(String name){
        db = new DB(mCtx);
        db.open();

        Cursor c = db.getData("clothes", new String[]{db.COLUMN_ID}, db.COLUMN_NAME + " = ?",
                new String[]{name}, null, null, null);

        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex(db.COLUMN_ID);
            id = c.getInt(idColIndex);

        } else
            Log.d("DBOperation", "0 rows");

        db.delRec(id);

        db.close();
    }


    public ArrayList<String> getData(String name, String[] columns, String selection, String[] selectionArgs,
                                     String groupBy, String having, String orderBy) {
        ArrayList<String> result = new ArrayList<>();
        db = new DB(mCtx);
        db.open();

        Cursor c = db.getData(name,columns,selection,selectionArgs,groupBy,having,orderBy);

        if (c.move(45)) {

            // определяем номера столбцов по имени в выборке
            int nameColIndex = c.getColumnIndex("name");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("clothes","name = " + c.getString(nameColIndex));
                result.add(c.getString(nameColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d("clothes", "0 rows");
        c.close();


        return result;
    }

}
