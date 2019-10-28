package com.example.crudmultitable.Database;

import android.bluetooth.BluetoothClass;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.example.crudmultitable.Features.MahasiswaCRUD.Mahasiswa;
import com.example.crudmultitable.Features.MataKuliahCRUD.Matakuliah;
import com.example.crudmultitable.Features.SelectMhsdanMatul.MahasiswaDetail;
import com.example.crudmultitable.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DatabaseQueryClass {

    private Context context;

    public DatabaseQueryClass(Context context) {
        this.context = context;
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public long insertMahasiswa(Mahasiswa mahasiswa) {

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_MAHASISWA_NAMA, mahasiswa.getName());
        contentValues.put(Config.COLUMN_MAHASISWA_NIM, mahasiswa.getNim());
        contentValues.put(Config.COLUMN_MAHASISWA_PHONE, mahasiswa.getPhoneNumber());
        contentValues.put(Config.COLUMN_MAHASISWA_EMAIL, mahasiswa.getEmail());

        try {
            id = sqLiteDatabase.insertOrThrow(Config.TABLE_MAHASISWA, null, contentValues);
        } catch (SQLiteException e) {
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }


    public List<MahasiswaDetail> getDetail(){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

//            cursor = sqLiteDatabase.query(Config.TABLE_MAHASISWA, null, null, null, null, null, null, null);


             // If you want to execute raw query then uncomment below 2 lines. And comment out above line.

                 String SELECT_QUERY = String.format("SELECT %s, %s, %s, %s FROM %s,%s WHERE %s = %s AND %s = %s",
                         Config.COLUMN_MAHASISWA_NAMA, Config.COLUMN_MAHASISWA_NIM,
                         Config.COLUMN_MATAKULIAH_NAME, Config.COLUMN_MATAKULIAH_CREDIT, Config.TABLE_MAHASISWA,
                         Config.TABLE_MATAKULIAH, Config.COLUMN_MAHASISWA_NIM,Config.COLUMN_NIM_NUMBER,Config.COLUMN_MAHASISWA_NIM,1705551031);
                 cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);


            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<MahasiswaDetail> mahasiswaDetails = new ArrayList<>();
                    do {
                        String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MAHASISWA_NAMA));
                        long registrationNumber = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_MAHASISWA_NIM));
                        String mtk = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MATAKULIAH_NAME));
                        double nilai = cursor.getDouble(cursor.getColumnIndex(Config.COLUMN_MATAKULIAH_CREDIT));

                        mahasiswaDetails.add(new MahasiswaDetail( name, registrationNumber, mtk, nilai));
                    }   while (cursor.moveToNext());

                    return mahasiswaDetails;
                }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }



    public List<Mahasiswa> getAllMahasiswa(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_MAHASISWA, null, null, null, null, null, null, null);

            /**
             // If you want to execute raw query then uncomment below 2 lines. And comment out above line.

             String SELECT_QUERY = String.format("SELECT %s, %s, %s, %s, %s FROM %s", Config.COLUMN_STUDENT_ID, Config.COLUMN_STUDENT_NAME, Config.COLUMN_STUDENT_REGISTRATION, Config.COLUMN_STUDENT_EMAIL, Config.COLUMN_STUDENT_PHONE, Config.TABLE_STUDENT);
             cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Mahasiswa> studentList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_MAHASISWA_ID));
                        String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MAHASISWA_NAMA));
                        long registrationNumber = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_MAHASISWA_NIM));
                        String email = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MAHASISWA_EMAIL));
                        String phone = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MAHASISWA_PHONE));

                        studentList.add(new Mahasiswa(id, name, registrationNumber, email, phone));
                    }   while (cursor.moveToNext());

                    return studentList;
                }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public Mahasiswa getMahasiswaByRegNum(long registrationNum){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        Mahasiswa mahasiswa = null;
        try {

            cursor = sqLiteDatabase.query(Config.TABLE_MAHASISWA, null,
                    Config.COLUMN_MAHASISWA_NIM + " = ? ", new String[]{String.valueOf(registrationNum)},
                    null, null, null);

            /**
             // If you want to execute raw query then uncomment below 2 lines. And comment out above sqLiteDatabase.query() method.

             String SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s = %s", Config.TABLE_MAHASISWA, Config.COLUMN_MAHASISWA_NIM, String.valueOf(registrationNum));
             cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */

            if(cursor.moveToFirst()){
                int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_MAHASISWA_ID));
                String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MAHASISWA_NAMA));
                long registrationNumber = cursor.getLong(cursor.getColumnIndex(Config.COLUMN_MAHASISWA_NIM));
                String phone = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MAHASISWA_PHONE));
                String email = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MAHASISWA_EMAIL));

                mahasiswa = new Mahasiswa(id, name, registrationNumber, phone, email);
            }
        } catch (Exception e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return mahasiswa;
    }

    public long updateMahasiswaInfo(Mahasiswa mahasiswa){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_MAHASISWA_NAMA, mahasiswa.getName());
        contentValues.put(Config.COLUMN_MAHASISWA_NIM, mahasiswa.getNim());
        contentValues.put(Config.COLUMN_MAHASISWA_PHONE, mahasiswa.getPhoneNumber());
        contentValues.put(Config.COLUMN_MAHASISWA_EMAIL, mahasiswa.getEmail());

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_MAHASISWA, contentValues,
                    Config.COLUMN_MAHASISWA_ID + " = ? ",
                    new String[] {String.valueOf(mahasiswa.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long deleteMahasiswaByRegNum(long registrationNum) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Config.TABLE_MAHASISWA,
                    Config.COLUMN_MAHASISWA_NIM + " = ? ",
                    new String[]{ String.valueOf(registrationNum)});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }

    public boolean deleteAllMahasiswa(){
        boolean deleteStatus = false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            //for "1" delete() method returns number of deleted rows
            //if you don't want row count just use delete(TABLE_NAME, null, null)
            sqLiteDatabase.delete(Config.TABLE_MAHASISWA, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_MAHASISWA);

            if(count==0)
                deleteStatus = true;

        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deleteStatus;
    }

    public long getNumberOfStudent(){
        long count = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_MAHASISWA);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return count;
    }

    // matakuliahs
    public long insertMataKuliah(Matakuliah matakuliah, long registrationNo){
        long rowId = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_MATAKULIAH_NAME, matakuliah.getName());
        contentValues.put(Config.COLUMN_MATAKULIAH_CODE, matakuliah.getCode());
        contentValues.put(Config.COLUMN_MATAKULIAH_CREDIT, matakuliah.getCredit());
        contentValues.put(Config.COLUMN_NIM_NUMBER, registrationNo);

        try {
            rowId = sqLiteDatabase.insertOrThrow(Config.TABLE_MATAKULIAH, null, contentValues);
        } catch (SQLiteException e){
            Logger.d(e);
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowId;
    }

    public Matakuliah getMatakuliahById(long matakuliahId){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Matakuliah matakuliah = null;

        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_MATAKULIAH, null,
                    Config.COLUMN_MATAKULIAH_ID + " = ? ", new String[] {String.valueOf(matakuliahId)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                String matakuliahName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MATAKULIAH_NAME));
                int matakuliahCode = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_MATAKULIAH_CODE));
                double matakuliahCredit = cursor.getDouble(cursor.getColumnIndex(Config.COLUMN_MATAKULIAH_CREDIT));

                matakuliah = new Matakuliah(matakuliahId, matakuliahName, matakuliahCode, matakuliahCredit);
            }
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return matakuliah;
    }

    public long updateMatakuliahInfo(Matakuliah matakuliah){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_MATAKULIAH_NAME, matakuliah.getName());
        contentValues.put(Config.COLUMN_MATAKULIAH_CODE, matakuliah.getCode());
        contentValues.put(Config.COLUMN_MATAKULIAH_CREDIT, matakuliah.getCredit());

        try {
            rowCount = sqLiteDatabase.update(Config.TABLE_MATAKULIAH, contentValues,
                    Config.COLUMN_MATAKULIAH_ID + " = ? ",
                    new String[] {String.valueOf(matakuliah.getId())});
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public List<Matakuliah> getAllMatakuliahByRegNo(long registrationNo){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        List<Matakuliah> matakuliahList = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(Config.TABLE_MATAKULIAH,
                    new String[] {Config.COLUMN_MATAKULIAH_ID, Config.COLUMN_MATAKULIAH_NAME, Config.COLUMN_MATAKULIAH_CODE, Config.COLUMN_MATAKULIAH_CREDIT},
                    Config.COLUMN_NIM_NUMBER + " = ? ",
                    new String[] {String.valueOf(registrationNo)},
                    null, null, null);

            if(cursor!=null && cursor.moveToFirst()){
                matakuliahList = new ArrayList<>();
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_MATAKULIAH_ID));
                    String matakuliahName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MATAKULIAH_NAME));
                    int matakuliahCode = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_MATAKULIAH_CODE));
                    double matakuliahCredit = cursor.getDouble(cursor.getColumnIndex(Config.COLUMN_MATAKULIAH_CREDIT));

                    matakuliahList.add(new Matakuliah(id, matakuliahName, matakuliahCode, matakuliahCredit));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return matakuliahList;
    }

    public boolean deleteMatakuliahById(long matakuliahId) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        int row = sqLiteDatabase.delete(Config.TABLE_MATAKULIAH,
                Config.COLUMN_MATAKULIAH_ID + " = ? ", new String[]{String.valueOf(matakuliahId)});

        return row > 0;
    }

    public boolean deleteAllMatakuliahByRegNum(long registrationNum) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        int row = sqLiteDatabase.delete(Config.TABLE_MATAKULIAH,
                Config.COLUMN_NIM_NUMBER + " = ? ", new String[]{String.valueOf(registrationNum)});

        return row > 0;
    }

    public long getNumberOfMatakuliah(){
        long count = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Config.TABLE_MATAKULIAH);
        } catch (SQLiteException e){
            Logger.d("Exception: " + e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return count;
    }

    public void getDetail(long registrationNum) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();


    }
}
