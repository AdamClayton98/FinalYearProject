package com.example.finalyearproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.finalyearproject.Models.AllergyModel;
import com.example.finalyearproject.Models.UserModel;
import com.example.finalyearproject.fragments.AllergiesFragment;
import com.google.android.gms.common.internal.Objects;

import java.util.ArrayList;
import java.util.List;


public class DatabaseMethods extends SQLiteOpenHelper {
    public static final String USERS_TABLE = "USERS";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_ID = "ID";
    public static final String TABLE_ALLERGIES = "ALLERGIES";
    public static final String COLUMN_ALLERGY_NAME = "ALLERGY_NAME";
    public static final String COLUMN_USERID = "USERID";

    public DatabaseMethods(@Nullable Context context) {
        super(context, "project.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);
        createAllergiesTable(db);
    }

    private void createUserTable(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + USERS_TABLE + " (" + COLUMN_ID + " TEXT PRIMARY KEY, " + COLUMN_NAME + " TEXT, " + COLUMN_USERNAME + " TEXT)";

        db.execSQL(createTableStatement);
    }

    private void createAllergiesTable(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + TABLE_ALLERGIES + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ALLERGY_NAME + " TEXT, " + COLUMN_USERID + " TEXT)";

        db.execSQL(createTableStatement);
    }

    public void addAllergyToDb(String allergyName) {
        if(!checkAllergyExists(allergyName)){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_ALLERGY_NAME, allergyName);
            contentValues.put(COLUMN_USERID, MainActivity.uid);

            db.insert(TABLE_ALLERGIES, null, contentValues);
            db.close();
        }
    }

    public boolean deleteAllergy(String allergy){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_ALLERGIES + " WHERE " + COLUMN_USERID + " = '" + MainActivity.uid + "' AND " + COLUMN_ALLERGY_NAME  + " = '" + allergy + "'";

        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    public boolean checkAllergyExists(String allergyName) {
        SQLiteDatabase readDb = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_ALLERGY_NAME + " FROM " + TABLE_ALLERGIES + " WHERE " + COLUMN_ALLERGY_NAME + " = '" + allergyName + "' AND " + COLUMN_USERID + " = '" + MainActivity.uid + "'";
        Cursor cursor = readDb.rawQuery(query, null);
        boolean exists;
        exists = cursor.moveToFirst();
        cursor.close();
        readDb.close();
        return exists;
    }

    public List<String> getAllergiesForUser() {
        List<String> allergies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_ALLERGIES + " WHERE " + COLUMN_USERID + " = '" + MainActivity.uid + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String allergyName = cursor.getString(1);
                allergies.add(allergyName);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return allergies;
    }

    public void addUser(UserModel userModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID, userModel.getId());
        contentValues.put(COLUMN_NAME, userModel.getName());
        contentValues.put(COLUMN_USERNAME, userModel.getUsername());

        db.insert(USERS_TABLE, null, contentValues);
    }

    public void updateUserInfo(String updateInfo, String updateColumn){
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + USERS_TABLE + " SET " + updateColumn + " = '" + updateInfo + "' WHERE " + COLUMN_ID + " = '" + MainActivity.uid + "'";
        db.execSQL(query);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
