package com.example.finalyearproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.DeniedByServerException;
import android.os.Build;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.finalyearproject.Models.AllergyModel;
import com.example.finalyearproject.Models.BasicIngredientModel;
import com.example.finalyearproject.Models.CommentModel;
import com.example.finalyearproject.Models.PantryIngredientModel;
import com.example.finalyearproject.Models.PlanModel;
import com.example.finalyearproject.Models.RecipeModel;
import com.example.finalyearproject.Models.UserModel;
import com.example.finalyearproject.fragments.AllergiesFragment;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.sql.SQLInput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public static final String TABLE_FAVOURITES = "FAVOURITES";
    public static final String COLUMN_NUM_OF_FAVOURITES = "NUM_OF_" + TABLE_FAVOURITES;
    public static final String COLUMN_RECIPE_ID = "RECIPE_ID";
    public static final String COLUMN_RATING = "RATING";
    public static final String TABLE_RATINGS = "RATINGS";
    public static final String COLUMN_IMAGE_URL = "IMAGE_URL";
    public static final String TABLE_IMAGES = "IMAGES";
    public static final String TABLE_COMMENTS = "COMMENTS";
    public static final String COLUMN_COMMENT_TEXT = "COMMENT_TEXT";
    public static final String COLUMN_MEAL_NUMBER = "MEAL_NUMBER";
    public static final String COLUMN_DATE_OF_PLAN = "DATE_OF_PLAN";
    public static final String TABLE_PLANS = "PLANS";
    public static final String COLUMN_IS_HEALTHY = "IS_HEALTHY";
    public static final String COLUMN_RECIPE_TYPE = "RECIPE_TYPE";

    public DatabaseMethods(@Nullable Context context) {
        super(context, "project.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);
        createAllergiesTable(db);
        createPantriesTable(db);
        createRecipesTable(db);
        createRatingsTable(db);
        createImagesTable(db);
        createCommentsTable(db);
        createFavouritesTable(db);
        createPlansTable(db);
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

    private void createRecipesTable(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + TABLE_RECIPES + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERID + " TEXT, " + COLUMN_RECIPE_NAME + " TEXT, " + COLUMN_INGREDIENTS + " TEXT, " + COLUMN_STEPS + " TEXT, " + COLUMN_COOKING_TIME + " TEXT, " + COLUMN_SERVES + " TEXT, " + COLUMN_NUM_OF_VIEWS + " INTEGER, " + COLUMN_NUM_OF_FAVOURITES + " INTEGER, " + COLUMN_IS_HEALTHY + " INTEGER, " + COLUMN_RECIPE_TYPE + " TEXT )";
        db.execSQL(createTableStatement);
    }

    private void createRatingsTable(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + TABLE_RATINGS + " (" + COLUMN_USERID + " TEXT, " + COLUMN_RECIPE_ID + " INTEGER, " + COLUMN_RATING + " REAL)";
        db.execSQL(createTableStatement);
    }

    private void createImagesTable(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + TABLE_IMAGES + " (" + COLUMN_RECIPE_ID + " INTEGER, " + COLUMN_IMAGE_URL + " TEXT)";
        db.execSQL(createTableStatement);
    }

    private void createCommentsTable(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + TABLE_COMMENTS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_RECIPE_ID + " INTEGER, " + COLUMN_USERID + " TEXT, " + COLUMN_COMMENT_TEXT + " TEXT)";
        db.execSQL(createTableStatement);
    }

    private void createFavouritesTable(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + TABLE_FAVOURITES + " (" + COLUMN_USERID + " TEXT, " + COLUMN_RECIPE_ID + " INTEGER)";
        db.execSQL(createTableStatement);
    }

    private void createPlansTable(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + TABLE_PLANS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERID + " TEXT, " + COLUMN_RECIPE_ID + " INTEGER, " + COLUMN_MEAL_NUMBER + " INTEGER, " + COLUMN_DATE_OF_PLAN + " TEXT)";
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
        db.close();
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

        ingredient = ingredient.toUpperCase();
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
        } else {
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
                        removeIngredient(ingredientName);
                        continue;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    ingredientName = "Failed to remove this expired ingredient";
                }

                PantryIngredientModel ingredient = new PantryIngredientModel(ingredientName, amount, measurementType, expiryDate);

                ingredients.add(ingredient);

            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return ingredients;
    }

    public boolean removeIngredient(String ingredient) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_PANTRIES + " WHERE " + COLUMN_USERID + " = '" + MainActivity.uid + "' AND " + COLUMN_INGREDIENT_NAME + " = '" + ingredient + "'";

        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    public void removeAmountOfIngredient(String ingredient, int amount, String expiry) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_PANTRIES + " SET " + COLUMN_AMOUNT + " = " + amount + " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + ingredient + "' AND " + COLUMN_USERID + " = '" + MainActivity.uid + "' AND " + COLUMN_EXPIRY_DATE + " = '" + expiry + "'";
        db.execSQL(query);
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
        now = now.minusDays(2);
        LocalDate expiryDate = simpleDateFormat.parse(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (expiryDate.isBefore(now)) {
            return true;
        } else {
            return false;
        }
    }


    public void addRecipe(String recipeName, String cookingTime, String serves, String ingredients, String steps, boolean isHealthy, String recipeType) {
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
        int isHealthyInt = 0;
        if (isHealthy) {
            isHealthyInt = 1;
        }
        contentValues.put(COLUMN_IS_HEALTHY, isHealthyInt);
        contentValues.put(COLUMN_RECIPE_TYPE, recipeType);

        db.insert(TABLE_RECIPES, null, contentValues);
        db.close();
    }

    public ArrayList<RecipeModel> getMyRecipes() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<RecipeModel> recipes = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_USERID + " = '" + MainActivity.uid + "'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String recipeName = cursor.getString(2);
                List<String> ingredients = stringToArrayFromDb(cursor.getString(3).replace(",", " - "));
                List<String> steps = stringToArrayFromDb(cursor.getString(4));
                String cookingTime = cursor.getString(5);
                String serves = cursor.getString(6);
                int rating = getRatingForRecipe(id);
                String url = getRecipeImageURL(id);
                int isHealthyInt = cursor.getInt(9);
                boolean isHealthy = false;
                if (isHealthyInt == 0) {
                    isHealthy = false;
                } else if (isHealthyInt == 1) {
                    isHealthy = true;
                }
                String recipeType = cursor.getString(10);

                RecipeModel recipe = new RecipeModel(id, recipeName, ingredients, steps, cookingTime, serves, rating, url, isHealthy, recipeType);

                recipes.add(recipe);

            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return recipes;
    }

    private List<String> stringToArrayFromDb(String textToSplit) {
        List<String> stringsArrayList = new ArrayList<>();
        String[] stringsArray = textToSplit.split("\\|");
        stringsArrayList = Arrays.asList(stringsArray);
        return stringsArrayList;
    }

    private int getRatingForRecipe(int recipeId) {
        int overallRating = 0;
        int numberOfRatings = 0;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT RATING FROM " + TABLE_RATINGS + " WHERE " + COLUMN_RECIPE_ID + " = '" + recipeId + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                overallRating = overallRating + cursor.getInt(0);
                numberOfRatings = numberOfRatings + 1;
            } while (cursor.moveToNext());
        }
        if (overallRating != 0) {
            overallRating = overallRating / numberOfRatings;
        }
        cursor.close();
        return overallRating;
    }

    public void uploadRecipeImageUrl(String recipeName, String imageuuid) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String recipeId = getRecipeIdForUser(recipeName);

        contentValues.put(COLUMN_RECIPE_ID, recipeId);
        contentValues.put(COLUMN_IMAGE_URL, imageuuid);

        db.insert(TABLE_IMAGES, null, contentValues);
        db.close();
    }

    public String getRecipeIdForUser(String recipeName) {
        String recipeId = null;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_RECIPES + " WHERE " + COLUMN_RECIPE_NAME + " = '" + recipeName + "' AND " + COLUMN_USERID + " = '" + MainActivity.uid + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            recipeId = cursor.getString(0);
        }
        return recipeId;
    }

    public String getRecipeImageURL(int recipeId) {
        String imageUid = null;
        String query = "SELECT " + COLUMN_IMAGE_URL + " FROM " + TABLE_IMAGES + " WHERE " + COLUMN_RECIPE_ID + " = '" + recipeId + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            imageUid = cursor.getString(0);
        }
        return imageUid;
    }

    public RecipeModel getIndividualRecipe(int recipeId) {
        RecipeModel recipeModel = null;
        String query = "SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_ID + " = '" + recipeId + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String recipeName = cursor.getString(2);
            List<String> ingredients = stringToArrayFromDb(cursor.getString(3).replace(",", " - "));
            List<String> steps = stringToArrayFromDb(cursor.getString(4));
            String cookingTime = cursor.getString(5);
            String serves = cursor.getString(6);
            int rating = getRatingForRecipe(recipeId);
            String uuid = getRecipeImageURL(recipeId);
            int isHealthyInt = cursor.getInt(9);
            System.out.println(isHealthyInt);
            boolean isHealthy = false;
            if (isHealthyInt == 0) {
                isHealthy = false;
            } else if (isHealthyInt == 1) {
                isHealthy = true;
            }
            System.out.println(isHealthy + " - ishealthy retrieved from db");
            String recipeType = cursor.getString(10);

            recipeModel = new RecipeModel(id, recipeName, ingredients, steps, cookingTime, serves, rating, uuid, isHealthy, recipeType);
        }
        return recipeModel;
    }

    public void setRecipeImage(ImageView imageView, RecipeModel recipeModel) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        final long MB = 1024 * 1024;
        storageReference.child("images/" + recipeModel.getUrl()).getBytes(MB).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(imageBitmap);
            }
        });
    }

    public void addRating(String recipeId, int rating) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if (checkRatingExists(recipeId)) {
            String query = "UPDATE " + TABLE_RATINGS + " SET " + COLUMN_RATING + " = " + rating + " WHERE " + COLUMN_USERID + " = '" + MainActivity.uid + "' AND " + COLUMN_RECIPE_ID + " = '" + recipeId + "'";
            db.execSQL(query);
        } else {
            contentValues.put(COLUMN_USERID, MainActivity.uid);
            contentValues.put(COLUMN_RECIPE_ID, recipeId);
            contentValues.put(COLUMN_RATING, rating);
            db.insert(TABLE_RATINGS, null, contentValues);
        }
        db.close();
    }

    public boolean checkRatingExists(String recipeId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_RATING + " FROM " + TABLE_RATINGS + " WHERE " + COLUMN_RECIPE_ID + " = '" + recipeId + "' AND " + COLUMN_USERID + " = '" + MainActivity.uid + "'";

        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst();

    }

    public void addComment(String recipeId, String commentText) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_RECIPE_ID, recipeId);
        contentValues.put(COLUMN_USERID, MainActivity.uid);
        contentValues.put(COLUMN_COMMENT_TEXT, commentText);
        db.insert(TABLE_COMMENTS, null, contentValues);
        db.close();
    }

    public ArrayList<CommentModel> getComments(String recipeId) {
        ArrayList<CommentModel> comments = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        int recipeIdInt = Integer.parseInt(recipeId);
        String query = "SELECT * FROM " + TABLE_COMMENTS + " WHERE " + COLUMN_RECIPE_ID + " = " + recipeIdInt;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String uid = cursor.getString(2);
                String user = getUsernameOfUser(uid);
                String comment = cursor.getString(3);
                CommentModel commentModel = new CommentModel(id, user, comment, uid, Integer.parseInt(recipeId));
                comments.add(commentModel);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return comments;
    }

    public void deleteComment(int commentId) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_COMMENTS + " WHERE " + COLUMN_ID + " = '" + commentId + "'";
        db.execSQL(query);
        db.close();
    }

    public String getUsernameOfUser(String uid) {
        SQLiteDatabase db = getReadableDatabase();
        String username = null;
        String query = "SELECT " + COLUMN_NAME + " FROM " + USERS_TABLE + " WHERE " + COLUMN_ID + " = '" + uid + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            username = cursor.getString(0);
        }
        return username;
    }

    public boolean isLoggedInUsersRecipe(String recipeId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_USERID + " FROM " + TABLE_RECIPES + " WHERE " + COLUMN_ID + " = '" + recipeId + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            db.close();
            return cursor.getString(0).equals(MainActivity.uid);
        } else {
            db.close();
            return false;
        }
    }

    public boolean isFavourite(String recipeId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_USERID + " FROM " + TABLE_FAVOURITES + " WHERE " + COLUMN_RECIPE_ID + " = '" + recipeId + "' AND " + COLUMN_USERID + " = '" + MainActivity.uid + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            db.close();
            return cursor.getString(0).equals(MainActivity.uid);
        } else {
            db.close();
            return false;
        }
    }

    public void removeRecipe(String recipeId) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_RECIPES + " WHERE " + COLUMN_ID + " = '" + recipeId + "'";
        db.execSQL(query);
        query = "DELETE FROM " + TABLE_FAVOURITES + " WHERE " + COLUMN_RECIPE_ID + " = " + recipeId;
        db.execSQL(query);
        query = "DELETE FROM " + TABLE_COMMENTS + " WHERE " + COLUMN_RECIPE_ID + " = '" + recipeId + "'";
        db.execSQL(query);
        query = "DELETE FROM " + TABLE_RATINGS + " WHERE " + COLUMN_RECIPE_ID + " = '" + recipeId + "'";
        db.execSQL(query);
        db.close();
    }

    public void reportRecipe(String recipeId, String reason) {
        Map<String, Object> reportData = new HashMap<>();
        reportData.put("ID", recipeId);
        reportData.put("Type", "Recipe");
        reportData.put("Reason", reason);
        FirebaseFirestore.getInstance().collection("Reports").add(reportData);
    }

    public void reportComment(String commentId, String reason) {
        Map<String, Object> reportData = new HashMap<>();
        reportData.put("ID", commentId);
        reportData.put("Type", "Comment");
        reportData.put("Reason", reason);
        FirebaseFirestore.getInstance().collection("Reports").add(reportData);
    }

    public String getRecipeIdForCommentId(String commentId) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_RECIPE_ID + " FROM " + TABLE_COMMENTS + " WHERE " + COLUMN_ID + " = '" + commentId + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            db.close();
            return cursor.getString(0);
        } else {
            db.close();
            return null;
        }

    }

    public void addToFavourites(String recipeId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RECIPE_ID, recipeId);
        contentValues.put(COLUMN_USERID, MainActivity.uid);
        db.insert(TABLE_FAVOURITES, null, contentValues);

        int numOfFaves = 0;
        String query = "SELECT " + COLUMN_NUM_OF_FAVOURITES + " FROM " + TABLE_RECIPES + " WHERE " + COLUMN_ID + " = '" + recipeId + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            numOfFaves = cursor.getInt(0);
        }
        numOfFaves = numOfFaves + 1;
        query = "UPDATE " + TABLE_RECIPES + " SET " + COLUMN_NUM_OF_FAVOURITES + " = " + numOfFaves + " WHERE " + COLUMN_ID + " = '" + recipeId + "'";
        db.execSQL(query);
        db.close();
    }

    public void removeFromFavourites(String recipeId) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_FAVOURITES + " WHERE " + COLUMN_RECIPE_ID + " = '" + recipeId + "' AND " + COLUMN_USERID + " = '" + MainActivity.uid + "'";
        db.execSQL(query);

        int numOfFaves = 0;
        query = "SELECT " + COLUMN_NUM_OF_FAVOURITES + " FROM " + TABLE_RECIPES + " WHERE " + COLUMN_ID + " = '" + recipeId + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            numOfFaves = cursor.getInt(0) - 1;
        }

        query = "UPDATE " + TABLE_RECIPES + " SET " + COLUMN_NUM_OF_FAVOURITES + " = " + numOfFaves + " WHERE " + COLUMN_ID + " = '" + recipeId + "'";
        db.execSQL(query);
        db.close();
    }

    public ArrayList<RecipeModel> getAllFavouritesForUser() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> recipeIds = new ArrayList<>();
        String query = "SELECT " + COLUMN_RECIPE_ID + " FROM " + TABLE_FAVOURITES + " WHERE " + COLUMN_USERID + " = '" + MainActivity.uid + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                recipeIds.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        ArrayList<RecipeModel> favouriteRecipes = new ArrayList<>();

        for (String recipeId : recipeIds) {
            favouriteRecipes.add(getIndividualRecipe(Integer.parseInt(recipeId)));
        }
        db.close();
        return favouriteRecipes;
    }

    public void updateRecipeViews(String recipeId) {
        SQLiteDatabase db = getWritableDatabase();
        int numOfFaves = 0;
        String query = "SELECT " + COLUMN_NUM_OF_VIEWS + " FROM " + TABLE_RECIPES + " WHERE " + COLUMN_ID + " = '" + recipeId + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            numOfFaves = cursor.getInt(0) + 1;
        }
        query = "UPDATE " + TABLE_RECIPES + " SET " + COLUMN_NUM_OF_VIEWS + " = " + numOfFaves + " WHERE " + COLUMN_ID + " = '" + recipeId + "'";
        db.execSQL(query);
        db.close();
    }

    public int getMyRating() {
        ArrayList<Integer> ratings = new ArrayList<>();
        ArrayList<RecipeModel> userRecipes = getMyRecipes();
        for (RecipeModel recipe : userRecipes) {
            ratings.add(recipe.getRating());
        }
        int overallrating = 0;
        int count = 0;
        for (int rating : ratings) {
            overallrating = overallrating + rating;
            count++;
        }
        if (count != 0) {
            return overallrating / count;
        } else {
            return 0;
        }
    }

    public void addPlan(String recipeId, int mealNumber, String dateOfPlan) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERID, MainActivity.uid);
        contentValues.put(COLUMN_RECIPE_ID, recipeId);
        contentValues.put(COLUMN_MEAL_NUMBER, mealNumber);
        contentValues.put(COLUMN_DATE_OF_PLAN, dateOfPlan);
        db.insert(TABLE_PLANS, null, contentValues);
        db.close();
    }

    public ArrayList<PlanModel> getPlanOnDate(String dateOfPlan) {
        ArrayList<PlanModel> plansForSelectedDate = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PLANS + " WHERE " + COLUMN_USERID + " = '" + MainActivity.uid + "' AND " + COLUMN_DATE_OF_PLAN + " = '" + dateOfPlan + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String uid = cursor.getString(1);
            int recipeId = cursor.getInt(2);
            int mealNumber = cursor.getInt(3);
            PlanModel planModel = new PlanModel(id, uid, recipeId, mealNumber, dateOfPlan);
            plansForSelectedDate.add(planModel);
        }
        db.close();
        return plansForSelectedDate;
    }

    public void removePlan(int planId) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_PLANS + " WHERE " + COLUMN_ID + " = " + planId;
        db.execSQL(query);
        db.close();
    }

    public void cookRecipeAndRemoveFromPantry(int recipeId) {
        ArrayList<BasicIngredientModel> ingredients = new ArrayList<>();
        String query = "SELECT " + COLUMN_INGREDIENTS + " FROM " + TABLE_RECIPES + " WHERE " + COLUMN_ID + " = '" + recipeId + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            List<String> ingredientsWithDetails = stringToArrayFromDb(cursor.getString(0));
            for (String ingredientWithDetails : ingredientsWithDetails) {
                String[] ingredientsInParts = ingredientWithDetails.split(",");
                BasicIngredientModel ingredientModel = new BasicIngredientModel(ingredientsInParts[0], ingredientsInParts[1]);
                ingredients.add(ingredientModel);
            }
        }

        SQLiteDatabase readDb = getReadableDatabase();
        for (BasicIngredientModel ingredientModel : ingredients) {
            query = "SELECT * FROM " + TABLE_PANTRIES + " WHERE " + COLUMN_INGREDIENT_NAME + " = '" + ingredientModel.getIngredientName() + "' AND " + COLUMN_USERID + " = '" + MainActivity.uid + "' ORDER BY date(" + COLUMN_EXPIRY_DATE + ") DESC LIMIT 1";
            cursor = readDb.rawQuery(query, null);
            int existingAmount = 0;
            int amountToRemove = Integer.parseInt(ingredientModel.getAmount());
            String mostRecentExpiry = null;
            if (cursor.moveToNext()) {
                existingAmount = cursor.getInt(3);
                mostRecentExpiry = cursor.getString(5);
                if (existingAmount - amountToRemove <= 0) {
                    removeIngredient(ingredientModel.getIngredientName());
                } else {
                    removeAmountOfIngredient(ingredientModel.getIngredientName(), (existingAmount - amountToRemove), mostRecentExpiry);
                }
            }
        }
        cursor.close();
        db.close();
    }

    public ArrayList<RecipeModel> getMostViewedRecipes() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<RecipeModel> recipes = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_RECIPES + " ORDER BY " + COLUMN_NUM_OF_VIEWS + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String recipeName = cursor.getString(2);
                List<String> ingredients = stringToArrayFromDb(cursor.getString(3).replace(",", " - "));
                List<String> steps = stringToArrayFromDb(cursor.getString(4));
                String cookingTime = cursor.getString(5);
                String serves = cursor.getString(6);
                int rating = getRatingForRecipe(id);
                String uuid = getRecipeImageURL(id);
                int isHealthyInt = cursor.getInt(9);
                boolean isHealthy = false;
                if (isHealthyInt == 0) {
                    isHealthy = false;
                } else if (isHealthyInt == 1) {
                    isHealthy = true;
                }
                String recipeType = cursor.getString(10);

                RecipeModel recipe = new RecipeModel(id, recipeName, ingredients, steps, cookingTime, serves, rating, uuid, isHealthy, recipeType);

                recipes.add(recipe);

            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        try {
            return (ArrayList<RecipeModel>) recipes.subList(0, 30);
        } catch (IndexOutOfBoundsException e) {
            return recipes;
        }
    }

    public ArrayList<RecipeModel> getMostFavouritedRecipes() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<RecipeModel> recipes = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_RECIPES + " ORDER BY " + COLUMN_NUM_OF_FAVOURITES + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String recipeName = cursor.getString(2);
                List<String> ingredients = stringToArrayFromDb(cursor.getString(3).replace(",", " - "));
                List<String> steps = stringToArrayFromDb(cursor.getString(4));
                String cookingTime = cursor.getString(5);
                String serves = cursor.getString(6);
                int rating = getRatingForRecipe(id);
                String uuid = getRecipeImageURL(id);
                int isHealthyInt = cursor.getInt(9);
                boolean isHealthy = false;
                if (isHealthyInt == 0) {
                    isHealthy = false;
                } else if (isHealthyInt == 1) {
                    isHealthy = true;
                }
                String recipeType = cursor.getString(10);

                RecipeModel recipe = new RecipeModel(id, recipeName, ingredients, steps, cookingTime, serves, rating, uuid, isHealthy, recipeType);

                recipes.add(recipe);

            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        try {
            return (ArrayList<RecipeModel>) recipes.subList(0, 30);
        } catch (IndexOutOfBoundsException e) {
            return recipes;
        }
    }

    public ArrayList<RecipeModel> getRecipesToMakeUnder20Minutes() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<RecipeModel> recipes = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_COOKING_TIME + " = '5 Minutes' OR " + COLUMN_COOKING_TIME + " = '10 Minutes' OR " + COLUMN_COOKING_TIME + " = '15 Minutes' OR " + COLUMN_COOKING_TIME + " = '20 Minutes'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String recipeName = cursor.getString(2);
                List<String> ingredients = stringToArrayFromDb(cursor.getString(3).replace(",", " - "));
                List<String> steps = stringToArrayFromDb(cursor.getString(4));
                String cookingTime = cursor.getString(5);
                String serves = cursor.getString(6);
                int rating = getRatingForRecipe(id);
                String uuid = getRecipeImageURL(id);
                int isHealthyInt = cursor.getInt(7);
                boolean isHealthy = false;
                if (isHealthyInt == 0) {
                    isHealthy = false;
                } else if (isHealthyInt == 1) {
                    isHealthy = true;
                }
                String recipeType = cursor.getString(8);

                RecipeModel recipe = new RecipeModel(id, recipeName, ingredients, steps, cookingTime, serves, rating, uuid, isHealthy, recipeType);

                recipes.add(recipe);

            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        try {
            return (ArrayList<RecipeModel>) recipes.subList(0, 30);
        } catch (IndexOutOfBoundsException e) {
            return recipes;
        }
    }

    private String getCorrectQuery(String serving, String cookingTime, String recipeType, int isHealthyInt) {
        String query = null;
        switch (cookingTime) {
            case "5 Minutes":
                query = "SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "5 Minutes'";
                break;
            case "10 Minutes":
                query = "SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "5 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "10 Minutes'";
                break;
            case "15 Minutes":
                query = "SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "5 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "10 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "15 Minutes'";
                break;
            case "20 Minutes":
                query = "SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "5 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "10 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "15 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "20 Minutes'";
                break;
            case "30 Minutes":
                query = "SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "5 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "10 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "15 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "20 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "30 Minutes'";
                break;
            case "45 Minutes":
                query = "SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "5 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "10 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "15 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "20 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "30 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "45 Minutes'";
                break;
            case "60 Minutes":
                query = "SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "5 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "10 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "15 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "20 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "30 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "45 Minutes'";
                query = query + " UNION SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "' AND " + COLUMN_COOKING_TIME + " = '" + "60 Minutes'";
                break;
            default:
                query = "SELECT * FROM " + TABLE_RECIPES + " WHERE " + COLUMN_SERVES + " = '" + serving + "' AND " + COLUMN_RECIPE_TYPE + " = '" + recipeType + "'";
        }

        return query;
    }

    public ArrayList<RecipeModel> getSearchResultRecipes(String serving, String cookingTime, String recipeType, boolean isHealthy) {
        SQLiteDatabase db = getWritableDatabase();
        int isHealthyInt = 0;
        if (isHealthy) {
            isHealthyInt = 1;
        }
        String query = getCorrectQuery(serving, cookingTime, recipeType, isHealthyInt);
        ArrayList<RecipeModel> recipes = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String recipeName = cursor.getString(2);
                List<String> ingredients = stringToArrayFromDb(cursor.getString(3).replace(",", " - "));
                List<String> steps = stringToArrayFromDb(cursor.getString(4));
                String cookingTimeForModel = cursor.getString(5);
                String serves = cursor.getString(6);
                int rating = getRatingForRecipe(id);
                String uuid = getRecipeImageURL(id);
                int isHealthyIntToConvert = cursor.getInt(9);
                System.out.println(isHealthyIntToConvert);
                boolean isHealthyForModel = false;
                if (isHealthyIntToConvert == 1) {
                    isHealthyForModel = true;
                }
                System.out.println(isHealthyForModel);
                String recipeTypeForModel = cursor.getString(10);

                RecipeModel recipe = new RecipeModel(id, recipeName, ingredients, steps, cookingTimeForModel, serves, rating, uuid, isHealthyForModel, recipeTypeForModel);

                recipes.add(recipe);
            } while (cursor.moveToNext());
        }

        return recipes;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
