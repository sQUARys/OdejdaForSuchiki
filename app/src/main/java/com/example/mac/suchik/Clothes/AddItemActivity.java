package com.example.mac.suchik.Clothes;

import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mac.suchik.R;

public class AddItemActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>, View.OnClickListener {

    private static final int CM_DELETE_ID = 2;
    ListView lvData;
    DB db;
    SimpleCursorAdapter scAdapter;

    Button btnAdd, btnRead, btnClear;
    EditText etName, etRain, etColor, etWind, etTempMax, etTempMin, etCloud, etCategory;

    final String LOG_TAG = "myLog";

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_operations);


        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etName = findViewById(R.id.etName);
        etTempMax = findViewById(R.id.etTempMax);
        etRain = findViewById(R.id.etRain);
        etColor = findViewById(R.id.etColor);
        etWind = findViewById(R.id.etWind);
        etTempMin = findViewById(R.id.etTempMin);
        etCloud = findViewById(R.id.etCloud);
        etCategory = findViewById(R.id.etCategory);


        // открываем подключение к БД
        db = new DB(this);
        db.open();


        /*
        db.addRec("Кепка", 30, 15, 0, 1, "df", 2);
        db.addRec("Шляпа", 30, 15, 0, 1, "df", 2);
        db.addRec("Шарф", 0, -15, 1, 1, "df", 1);
        db.addRec("Валенки", -15, -30, 2, 1, "df", 1);
        */

        LoaderManager.getInstance(this).initLoader(0, null, this);

    }

    // обработка нажатия кнопки
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.btnAdd:

                Integer category = Integer.valueOf(etCategory.getText().toString());
                String name = etName.getText().toString();
                Integer tempMin = Integer.valueOf(etTempMin.getText().toString());
                Integer tempMax = Integer.valueOf(etTempMax.getText().toString());
                Integer wind = Integer.valueOf(etWind.getText().toString());
                String color = etColor.getText().toString();
                Integer rain = Integer.valueOf(etRain.getText().toString());
                Integer cloud = Integer.valueOf(etCloud.getText().toString());

                // добавляем запись
                db.addRec(category, name, tempMin, tempMax, rain, wind, cloud, color);
                // получаем новый курсор с данными
                //LoaderManager.getInstance(this).initLoader()
                LoaderManager.getInstance(this).getLoader(0).forceLoad();

                break;
            case R.id.btnClear:
                db.delAll();
                //db.delRec(1);
                break;
            case R.id.btnRead:

                Cursor c = db.getAllData();


                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int nameColIndex = c.getColumnIndex("name");
                    int rainColIndex = c.getColumnIndex("rain");
                    int windColIndex = c.getColumnIndex("wind");
                    int cloudColIndex = c.getColumnIndex("cloud");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG, "name = " + c.getString(nameColIndex) +
                                " rain = " + c.getString(rainColIndex) +
                                " wind = " + c.getString(windColIndex) +
                                " cloud = " + c.getString(cloudColIndex));

                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");

                break;
        }
    }

    //Charles
    //Fiddler
    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        db.close();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new MyCursorLoader(this, db);
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
}

