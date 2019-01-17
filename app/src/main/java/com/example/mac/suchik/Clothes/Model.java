package com.example.mac.suchik.Clothes;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Model implements LoaderManager.LoaderCallbacks<Cursor> {
    private static Context mContext;


    DB db;

    String[] columns;
    String selection;
    String[] selectionArgs;
    String groupBy;
    String having;
    String orderBy;


    final String LOG_TAG = "myLog";

    public Model(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<String> getClothes(Weather weather){
        // открываем подключение к БД

        //Weather weather = getWeather();


        db = new DB(mContext);
        db.open();
        columns = new String[] {db.COLUMN_NAME, db.COLUMN_CATEGORY};


        if (weather.temperature > 15){
            selection = (db.COLUMN_TEMP_MAX + " < 31");
            selection += (" and " + db.COLUMN_TEMP_MIN + " > 14");
            switch (weather.rain){
                case "rain":
                    selection += (" and " + db.COLUMN_RAIN + " > 0");
                    if (weather.wind > 10){
                        selection += (" and " + db.COLUMN_WIND + " > 0");
                        // Дождевик, футболка, резиновые сапоги, шорты/юбка/легкие брюки
                    } else{
                        selection += (" and " + db.COLUMN_WIND + " < 2");
                        // Зонт, резиновые сапоги, футболка, шорты/юбка/легкие брюки
                    }
                    break;
                case "no":
                    selection += (" and " + db.COLUMN_RAIN + " < 2");
                    if (weather.wind > 10){
                        selection += (" and " + db.COLUMN_WIND + " > 0");
                        if (weather.cloud == "no"){
                            selection += (" and " + db.COLUMN_CLOUD + " > 0");
                        }
                        // Кепка/шляпа, ветровка, легкие брюки/шорты/юбка, футболка, кроссовки/сандалии, солнечные очки
                    } else{
                        selection += (" and " + db.COLUMN_WIND + " < 2");
                        if (weather.cloud == "no"){
                            selection += (" and " + db.COLUMN_CLOUD + " > 0");
                        }
                        // Кепка/шляпа, легкие брюки/шорты/юбка, футболка, кроссовки/сандалии, солнечные очки
                    }
                    break;
            }
        } else if (weather.temperature > 0){
            selection = (db.COLUMN_TEMP_MAX + " < 16");
            selection += (" and " + db.COLUMN_TEMP_MIN + " > -1");
            switch (weather.rain){
                case "rain":
                    selection += (" and " + db.COLUMN_RAIN + " > 0");
                    //selection += (" and " + db.COLUMN_CLOUD + " < 2");
                    if (weather.wind > 10){
                        selection += (" and " + db.COLUMN_WIND + " > 0");
                        // Легкая шапка, перчатки, легкий шарф, непромокаемая куртка, брюки, футболка, резиновые сапоги
                    } else{
                        selection += (" and " + db.COLUMN_WIND + " < 2");
                        // Перчатки, легкий шарф, непромокаемая куртка, брюки, футболка, резиновые сапоги, зонт
                    }
                    break;
                case "no":
                    selection += (" and " + db.COLUMN_RAIN + " < 2");
                    // Легкая шапка, перчатки, легкий шарф, легкая куртка/джинсовка, брюки, футболка, ботинки
                    break;
            }
            //Шапка, шарф, перчатки, брюки

        } else if (weather.temperature > -15){
            selection = (db.COLUMN_TEMP_MAX + " < 1");
            selection += (" and " + db.COLUMN_TEMP_MIN + " > -16");
            //Шапка, теплые перчатки, шарф, куртка/пальто, термобелье, брюки, ботинки, футболка
        } else{
            selection = (db.COLUMN_TEMP_MAX + " < -14");
            selection += (" and " + db.COLUMN_TEMP_MIN + " > -31");
            //Теплая шапка, варежки, теплый шарф, шуба/пуховик/теплая куртка, футболка, валенки/теплые ботинки, кофта, терфомбелье, теплые брюки
        }

        Cursor c = db.getData(columns, selection, null, null, null, null);

        Log.d(LOG_TAG, "selection = " + selection);

        //ArrayList<String> clothes = new ArrayList<>();

        TreeMap<String, ArrayList<String>> clothes = new TreeMap<>();

        if (c.moveToFirst()) {
            int nameColIndex = c.getColumnIndex("name");
            int categoryColIndex = c.getColumnIndex("category");

            do {
                //Log.d(LOG_TAG, "name = " + c.getString(nameColIndex) + " category = " + c.getString(categoryColIndex));
                String category = c.getString(categoryColIndex);
                if (clothes.containsKey(category)){
                    clothes.get(category).add(c.getString(nameColIndex));
                } else{
                    ArrayList<String> list = new ArrayList<>();
                    list.add(c.getString(nameColIndex));
                    clothes.put(category, list);
                }

            } while (c.moveToNext());

        } else
            Log.d(LOG_TAG, "0 rows");

        db.close();

        for (Map.Entry<String, ArrayList<String>> stringArrayListEntry : clothes.entrySet()) {
            Log.d(LOG_TAG, stringArrayListEntry.getKey() + stringArrayListEntry.getValue());
        }

        ArrayList<String> recomendations = new ArrayList<>();

        for(TreeMap.Entry e : clothes.entrySet()){
            ArrayList<String> list = (ArrayList) e.getValue();
            int rnd = new Random().nextInt(list.size());
            recomendations.add(list.get(rnd));
        }
        Log.d(LOG_TAG, "RESULT:");
        for (String recomendation : recomendations) {
            Log.d(LOG_TAG, recomendation);
        }

        return recomendations;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new AddItemActivity.MyCursorLoader(mContext, db);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class MyCursorLoader extends CursorLoader {

        DB db;

        public MyCursorLoader(Context context, DB db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return cursor;
        }

    }


    public class Weather{
        public Integer temperature;
        public String rain;
        public Integer wind;
        public String cloud;
    }

}
