package com.example.crudmultitable.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.example.crudmultitable.Util.Config;
import com.orhanobut.logger.Logger;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = Config.DATABASE_NAME;

    // Constructor
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static DatabaseHelper getInstance(Context context) {
        if(databaseHelper==null){
            synchronized (DatabaseHelper.class) {
                if(databaseHelper==null)
                    databaseHelper = new DatabaseHelper(context);
            }
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tables SQL execution
        String CREATE_MAHASISWA_TABLE = "CREATE TABLE " + Config.TABLE_MAHASISWA + "("
                + Config.COLUMN_MAHASISWA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_MAHASISWA_NAMA + " TEXT NOT NULL, "
                + Config.COLUMN_MAHASISWA_NIM + " INTEGER NOT NULL UNIQUE, "
                + Config.COLUMN_MAHASISWA_PHONE + " TEXT, " //nullable
                + Config.COLUMN_MAHASISWA_EMAIL + " TEXT " //nullable
                + ")";

        String CREATE_MATAKULIAH_TABLE = "CREATE TABLE " + Config.TABLE_MATAKULIAH + "("
                + Config.COLUMN_MATAKULIAH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_NIM_NUMBER + " INTEGER NOT NULL, "
                + Config.COLUMN_MATAKULIAH_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_MATAKULIAH_CODE + " INTEGER NOT NULL, "
                + Config.COLUMN_MATAKULIAH_CREDIT + " REAL, " //nullable
                + "FOREIGN KEY (" + Config.COLUMN_NIM_NUMBER + ") REFERENCES " + Config.TABLE_MAHASISWA +
                "(" + Config.COLUMN_MAHASISWA_NIM + ") ON UPDATE CASCADE ON DELETE CASCADE, "
                + "CONSTRAINT " + Config.MAHASISWA_SUB_CONSTRAINT +
                " UNIQUE (" + Config.COLUMN_NIM_NUMBER + "," + Config.COLUMN_MATAKULIAH_CODE + ")"
                + ")";

        db.execSQL(CREATE_MAHASISWA_TABLE);
        db.execSQL(CREATE_MATAKULIAH_TABLE);

        Logger.d("DB created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_MAHASISWA);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_MATAKULIAH);

        // Create tables again
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
        db.execSQL("PRAGMA foreign_keys=ON;");
    }
}
