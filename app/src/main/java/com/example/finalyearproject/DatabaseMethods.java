package com.example.finalyearproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.finalyearproject.Models.AllergyModel;
import com.example.finalyearproject.Models.PantryIngredientModel;
import com.example.finalyearproject.Models.RecipeModel;
import com.example.finalyearproject.Models.UserModel;
import com.example.finalyearproject.fragments.AllergiesFragment;
import com.google.android.gms.common.internal.Objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class DatabaseMethods extends SQLiteOpenHelper {
    public static final String USERS_TABLE = "USERS";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_ID = "ID";
    public static final String TABLE_ALLERGIES = "ALLERGIES";
    public static final String COLUMN_ALLERGY_NAME = "ALLERGY_NAME";
    public static final String COLUMN_USERID = "USER" + COLUMN_ID;
    public static final String COLUMN_INGREDIENT_NAME = "INGREDIENT_NAME";
    public static final String COLUMN_AMOUNT = "AMOUNT";
    public static final String COLUMN_MEASUREMENT_TYPE = "MEASUREMENT_TYPE";
    public static final String COLUMN_EXPIRY_DATE = "EXPIRY_DATE";
    public static final String TABLE_PANTRIES = "PANTRIES";
    public static final String TABLE_RECIPES = "RECIPES";
    public static final String COLUMN_RECIPE_NAME = "RECIPE_NAME";
    public static final String COLUMN_INGREDIENTS = "INGREDIENTS";
    public static final String COLUMN_STEPS = "STEPS";
    public static final String COLUMN_COOKING_TIME = "COOKING_TIME";
    public static final String COLUMN_SERVES = "SERVES";
    public static final String COLUMN_NUM_OF_VIEWS = "NUM_OF_VIEWS";
    public static final String COLUMN_NUM_OF_FAVOURITES = "NUM_OF_FAVOURITES";

    public DatabaseMethods(@Nullable Context context) {
        super(context, "project.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);
        createAllergiesTable(db);
        createPantriesTable(db);
        createRecipesTable(db);
    }

    private void createUserTable(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + USERS_TABLE + " (" + COLUMN_ID + " TEXT PRIMARY KEY, " + COLUMN_NAME + " TEXT, " + COLUMN_USERNAME + " TEXT)";

        db.execSQL(createTableStatement);
    }

    private void createAllergiesTable(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + TABLE_ALLERGIES + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ALLERGY_NAME + " TEXT, " + COLUMN_USERID + " TEXT)";

        db.execSQL(createTableStatement);
    }

    private void createPantriesTable(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + TABLE_PANTRIES + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERID + " TEXT, " + COLUMN_INGREDIENT_NAME + " TEXT, " + COLUMN_AMOUNT + " INTEGER, " + COLUMN_MEASUREMENT_TYPE + " TEXT, " + COLUMN_EXPIRY_DATE + " TEXT)";
        db.execSQL(createTableStatement);
    }

    private void createRecipesTable(SQLiteDatabase db){
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + TABLE_RECIPES + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERID + " TEXT, " + COLUMN_RECIPE_NAME + " TEXT, " + COLUMN_INGREDIENTS + " TEXT, " + COLUMN_STEPS + " TEXT, " + COLUMN_COOKING_TIME + " TEXT, " + COLUMN_SERVES + " TEXT, " + COLUMN_NUM_OF_VIEWS + " INTEGER, " + COLUMN_NUM_OF_FAVOURITES + " INTEGER)";
        db.execSQL(createTableStatement);
    }

    public void addAllergyToDb(String allergyName) {
        if (!checkAllergyExists(allergyName)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_ALLERGY_NAME, allergyName.toUpperCase());
            contentValues.put(COLUMN_USERID, MainActivity.uid);

            db.insert(TABLE_ALLERGIES, null, contentValues);
            db.close();
        }
    }

    public boolean deleteAllergy(String allergy) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_ALLERGIES + " WHERE " + COLUMN_USERID + " = '" + MainActivity.uid + "' AND " + COLUMN_ALLERGY_NAME + " = '" + allergy + "'";

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

    public void updateUserInfo(String updateInfo, String updateColumn) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + USERS_TABLE + " SET " + updateColumn + " = '" + updateInfo + "' WHERE " + COLUMN_ID + " = '" + MainActivity.uid + "'";
        db.execSQL(query);
        db.close();
    }

    public void addIngredientToPantry(String ingredient, int amount, String measurementType, String expiryDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        ingredient=ingredient.toUpperCase();
        contentValues.put(COLUMN_USERID, MainActivity.uid);
        contentValues.put(COLUMN_INGREDIENT_NAME, ingredient);
        contentValues.put(COLUMN_AMOUNT, amount);
        contentValues.put(COLUMN_MEASUREMENT_TYPE, measurementType);
        contentValues.put(COLUMN_EXPIRY_DATE, expiryDate);

        if (ingredientAndMeasurementTypeExists(ingredient, measurementType)) {
            int existingAmount = getExistingAmountOfIngredient(ingredient, measurementType, expiryDate);
            amount = amount + existingAmount;
            String query = "UPDATE " + TABLE_PANTRIES + " SET " + COLUMN_AMOUNT + " = " + amount + " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + ingredient + "' AND " + COLUMN_USERID + " = '" + MainActivity.uid + "' AND " + COLUMN_MEASUREMENT_TYPE + " = '" + measurementType + "'";
            db.execSQL(query);
        }else{
            db.insert(TABLE_PANTRIES, null, contentValues);
        }
        db.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<PantryIngredientModel> getPantryForUser() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<PantryIngredientModel> ingredients = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_PANTRIES + " WHERE " + COLUMN_USERID + " = '" + MainActivity.uid + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                String ingredientName = cursor.getString(2);
                String amount = String.valueOf(cursor.getInt(3));
                String measurementType = cursor.getString(4);
                String expiryDate = cursor.getString(5);

                try {
                    if (checkDateIs2DaysAfterToday(expiryDate)) {
                        removeIngredient(ingredientName,expiryDate);
                        continue;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    ingredientName = "Failed to move this expired ingredient";
                }

                PantryIngredientModel ingredient = new PantryIngredientModel(ingredientName, amount, measurementType, expiryDate);

                ingredients.add(ingredient);

            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return ingredients;
    }

    public boolean removeIngredient(String ingredient, String expiryDate) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_PANTRIES + " WHERE " + COLUMN_USERID + " = '" + MainActivity.uid + "' AND " + COLUMN_INGREDIENT_NAME + " = '" + ingredient + "' AND " + COLUMN_EXPIRY_DATE + " = '" + expiryDate + "'";

        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    public boolean ingredientAndMeasurementTypeExists(String ingredient, String measurementType) {
        SQLiteDatabase readDb = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_MEASUREMENT_TYPE + " FROM " + TABLE_PANTRIES + " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + ingredient + "' AND " + COLUMN_USERID + " = '" + MainActivity.uid + "'";
        Cursor cursor = readDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(0).equals(measurementType)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }

    public int getExistingAmountOfIngredient(String ingredient, String measurementType, String expiryDate) {
        SQLiteDatabase readDb = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_AMOUNT + " FROM " + TABLE_PANTRIES + " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + ingredient + "' AND " + COLUMN_USERID + " = '" + MainActivity.uid + "' AND " + COLUMN_MEASUREMENT_TYPE + " = '" + measurementType + "' AND " + COLUMN_EXPIRY_DATE + " = '" + expiryDate + "'";
        Cursor cursor = readDb.rawQuery(query, null);
        int existingAmount = 0;
        if (cursor.moveToNext()) {
            existingAmount = cursor.getInt(0);
        }
        cursor.close();
        return existingAmount;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean checkDateIs2DaysAfterToday(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        LocalDate now = LocalDate.now();
        now = now.plusDays(2);
        LocalDate expiryDate = simpleDateFormat.parse(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (expiryDate.isBefore(now)) {
            return true;
        } else {
            return false;
        }
    }


    public void addRecipe(String recipeName, String cookingTime, String serves, String ingredients, String steps){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        recipeName = recipeName.toUpperCase();
        ingredients = ingredients.toUpperCase();
        steps = steps.toUpperCase();
        contentValues.put(COLUMN_USERID, MainActivity.uid);
        contentValues.put(COLUMN_RECIPE_NAME, recipeName);
        contentValues.put(COLUMN_COOKING_TIME, cookingTime);
        contentValues.put(COLUMN_SERVES, serves);
        contentValues.put(COLUMN_INGREDIENTS, ingredients);
        contentValues.put(COLUMN_STEPS, steps);

        db.insert(TABLE_RECIPES, null, contentValues);
        db.close();
    }

    public ArrayList<RecipeModel> getMyRecipes(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<RecipeModel> recipes = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_USERID + " = '" + MainActivity.uid + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String recipeName = cursor.getString(2);
                ArrayList<String> ingredients = stringToArrayFromDb(cursor.getString(3));
                ArrayList<String> steps = stringToArrayFromDb(cursor.getString(4));
                String cookingTime = cursor.getString(5);
                String serves = cursor.getString(6);

                RecipeModel recipe = new RecipeModel(id, recipeName, ingredients, steps, cookingTime, serves);

                recipes.add(recipe);

            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return recipes;
    }

    private ArrayList<String> stringToArrayFromDb(String textToSplit){
        return (ArrayList<String>) Arrays.asList(textToSplit.split("\\|"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
