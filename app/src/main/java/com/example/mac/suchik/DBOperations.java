package com.example.mac.suchik;

import android.content.Context;

public class DBOperations {

    private Context mCtx;

    DB db;

    public DBOperations(Context context) {
        this.mCtx = context;
    }

    public void addItem(ClothesData item){
        db = new DB(mCtx);
        db.open();

        db.addRec(item.getCategory(), item.getName(), item.getMinTemp(), item.getMaxTemp(), item.getRain(), item.getWind(),
                item.getCloud(), item.getColor());

        db.close();
    }


}
