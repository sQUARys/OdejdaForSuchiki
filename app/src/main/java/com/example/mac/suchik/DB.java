package com.example.mac.suchik;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB {

    private static final String DB_NAME = "clothes";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "clothes";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TEMP_MAX = "tempMax";
    public static final String COLUMN_TEMP_MIN = "tempMin";
    public static final String COLUMN_RAIN = "rain";
    public static final String COLUMN_WIND = "wind";
    public static final String COLUMN_CLOUD = "cloud";
    public static final String COLUMN_COLOR = "color";


    private static final String CATEGORY_TABLE_NAME = "categories";
    public static final String CATEGORY_COLUMN_ID = "_id";
    public static final String CATEGORY_COLUMN_NAME = "name";


    final String LOG_TAG = "myLog";

    private static final String CATEGORY_TABLE_CREATE =
            "CREATE TABLE " + CATEGORY_TABLE_NAME + "(" +
                    CATEGORY_COLUMN_ID + " integer primary key autoincrement, " +
                    CATEGORY_COLUMN_NAME + " varchar(255) "
                    + ");";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_CATEGORY + " integer, " +
                    COLUMN_NAME + " text, " +
                    COLUMN_TEMP_MIN + " integer, " +
                    COLUMN_TEMP_MAX + " integer, " +
                    COLUMN_RAIN + " integer, " +
                    COLUMN_WIND + " integer, " +
                    COLUMN_CLOUD + "integer, " +
                    COLUMN_COLOR + " text, " +
                    "FOREIGN KEY (" + COLUMN_CATEGORY + ") REFERENCES " + CATEGORY_TABLE_NAME + "(" + CATEGORY_COLUMN_ID + ")" +
                    ");";


    private static final String DB_INIT_CATEGORY_DATA = "INSERT INTO categories (name) VALUES (\"Головные уборы\")," + "(\"Перчатки\")," + "(\"Шарфы\")," +
            "(\"Верхняя одежда\")," + "(\"Брюки\")," + "(\"Футболки\")," + "(\"Обувь\")," + "(\"Аксессуары\")," + "(\"Термобелье\")," + "(\"Кофты\");";

    private static final String DB_INIT_CLOTHES_DATA = "INSERT INTO clothes (category, name, tempMin, tempMax, rain, wind, cloud, color) VALUES (0,\"Теплая шапка\", -30, -15, 1, 1, 1, \"\")," +
            "(0,\"Шапка\", -15, 0, 1, 1, 1, \"\")," +
            "(0,\"Легкая шапка\", 0, 15, 1, 2, 1, \"\")," +
            "(0,\"Кепка\", 15, 30, 0, 1, 2, \"\")," +
            "(0,\"Шляпа\", 15, 30, 0, 1, 2, \"\")," +
            "(1,\"Варежки\", -30, -15, 1, 1, 1, \"\")," +
            "(1,\"Тепл. перчатки\", -15, 0, 1, 1, 1, \"\")," +
            "(1,\"Перчатки\", 0, 15, 1, 1, 1, \"\")," +
            "(2,\"Теплый шарф\", -30, -15, 1, 1, 1, \"\")," +
            "(2,\"Шарф\", -15, 0, 1, 1, 1, \"\")," +
            "(2,\"Легкий шарф\", 0, 15, 1, 1, 1, \"\")," +
            "(3,\"Пуховик\", -30, -15, 2, 1, 1, \"\")," +
            "(3,\"Тепл. куртка\", -30, -15, 2, 1, 1, \"\")," +
            "(3,\"Шуба\", -30, -15, 2, 1, 1, \"\")," +
            "(3,\"Куртка\", -15, 0, 2, 1, 1, \"\")," +
            "(3,\"Пальто\", -15, 0, 0, 1, 1, \"\")," +
            "(3,\"Непром. куртка\", 0, 15, 2, 1, 1, \"\")," +
            "(3,\"Легкая куртка\", 0, 15, 0, 1, 1, \"\")," +
            "(3,\"Джинсовка\", 0, 15, 0, 1, 1, \"\")," +
            "(3,\"Дождевик\", 15, 30, 2, 2, 1, \"\")," +
            "(3,\"Ветровка\", 15, 30, 0, 2, 1, \"\")," +
            "(4,\"Теплые брюки\", -30, -15, 1, 1, 1, \"\")," +
            "(4,\"Брюки\", -15, 0, 1, 1, 1, \"\")," +
            "(4,\"Брюки\", 0, 15, 1, 1, 1, \"\")," +
            "(4,\"Легкие брюки\", 15, 30, 1, 1, 1, \"\")," +
            "(4,\"Шорты/юбка\", 15, 30, 1, 1, 1, \"\")," +
            "(5,\"Футболка\", -30, -15, 1, 1, 1, \"\")," +
            "(5,\"Футболка\", -15, 0, 1, 1, 1, \"\")," +
            "(5,\"Футболка\", 0, 15, 1, 1, 1, \"\")," +
            "(5,\"Футболка\", 15, 30, 1, 1, 1, \"\")," +
            "(6,\"Валенки\", -30, -15, 2, 1, 1, \"\")," +
            "(6,\"Теплые ботинки\", -30, -15, 2, 1, 1, \"\")," +
            "(6,\"Ботинки\", -15, 0, 2, 1, 1, \"\")," +
            "(6,\"Ботинки\", 0, 15, 0, 1, 1, \"\")," +
            "(6,\"Рез. сапоги\", 0, 15, 2, 1, 1, \"\")," +
            "(6,\"Рез. сапоги\", 15, 30, 2, 1, 1, \"\")," +
            "(6,\"Кроссовки\", 15, 30, 0, 1, 1, \"\")," +
            "(6,\"Сандалии\", 15, 30, 0, 1, 1, \"\")," +
            "(7,\"Зонт\", 15, 30, 2, 0, 1, \"\")," +
            "(7,\"Зонт\", 0, 15, 2, 0, 1, \"\")," +
            "(7,\"Солнечные очки\", 15, 30, 0, 1, 2, \"\")," +
            "(8,\"Термобелье\", -30, -15, 1, 1, 1, \"\")," +
            "(8,\"Термобелье\", -15, 0, 1, 1, 1, \"\")," +
            "(9,\"Кофта\", -30, -15, 1, 1, 1, \"\");";


    private final Context mCtx;


    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;


    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData(String name) {
        return mDB.query(name, null, null, null, null, null, null);
    }

    // получить данные из таблицы DB_TABLE
    public Cursor getData(String name, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return mDB.query(name, columns, selection, selectionArgs, groupBy, having, orderBy);
    }


    // добавить запись в DB_TABLE
    public void addRec(int category, String name, int minT, int maxT, int rain, int wind, int cloud, String color) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_TEMP_MIN, minT);
        cv.put(COLUMN_TEMP_MAX, maxT);
        cv.put(COLUMN_RAIN, rain);
        cv.put(COLUMN_WIND, wind);
        cv.put(COLUMN_CLOUD, cloud);
        cv.put(COLUMN_COLOR, color);
        long rowID = mDB.insert(DB_TABLE, null, cv);

        Log.d(LOG_TAG, "row inserted, ID = " + rowID);


    }

    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    public void delAll() {
        mDB.delete(DB_TABLE, null, null);
    }

    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CATEGORY_TABLE_CREATE);
            db.execSQL(DB_INIT_CATEGORY_DATA);
            db.execSQL(DB_CREATE);
            db.execSQL("alter table clothes add column cloud;");
            db.execSQL(DB_INIT_CLOTHES_DATA);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("alter table clothes add column cloud;");
        }
    }
}
