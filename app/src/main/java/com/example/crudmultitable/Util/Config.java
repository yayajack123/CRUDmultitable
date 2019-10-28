package com.example.crudmultitable.Util;

public class Config {

    public static final String DATABASE_NAME = "db_mhs";

    //column names of student table
    public static final String TABLE_MAHASISWA = "mahasiswa";
    public static final String COLUMN_MAHASISWA_ID = "_id";
    public static final String COLUMN_MAHASISWA_NAMA = "nama";
    public static final String COLUMN_MAHASISWA_NIM = "nim";
    public static final String COLUMN_MAHASISWA_PHONE = "phone";
    public static final String COLUMN_MAHASISWA_EMAIL = "email";

    //column names of subject table
    public static final String TABLE_MATAKULIAH = "matakuliah";
    public static final String COLUMN_MATAKULIAH_ID = "_id";
    public static final String COLUMN_NIM_NUMBER = "fk_nim";
    public static final String COLUMN_MATAKULIAH_NAME = "matakuliah";
    public static final String COLUMN_MATAKULIAH_CODE = "kode_matakuliah";
    public static final String COLUMN_MATAKULIAH_CREDIT = "nilai";
    public static final String MAHASISWA_SUB_CONSTRAINT = "mahasiswa_sub_unique";

    //others for general purpose key-value pair data
    public static final String TITLE = "title";
    public static final String CREATE_MAHASISWA = "create_student";
    public static final String UPDATE_MAHASISWA = "update_student";
    public static final String CREATE_MATAKULIAH = "create_subject";
    public static final String UPDATE_MATAKULIAH = "update_subject";
    public static final String MAHASISWA_REGISTRATION = "mahasiswa_registration";
}
