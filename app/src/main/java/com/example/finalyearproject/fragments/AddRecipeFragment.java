package com.example.finalyearproject.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalyearproject.DatabaseMethods;
import com.example.finalyearproject.R;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRecipeFragment extends Fragment {

    View view;
    EditText recipeNameInput;
    Spinner cookingTimeDropdown;
    Spinner servingDropdown;
    LinearLayout ingredientListLayout;
    Button addIngredientButton;
    String[] measurementTypes = new String[]{"ML", "Grams", "Tbsp", "oz", "lb", "Unit(s)"};
    String[] cookingTimes = new String[]{"5 Minutes", "10 Minutes", "15 Minutes", "20 Minutes", "30 Minutes", "45 Minutes", "60 Minutes", "Over 60 Minutes"};
    String[] serves = new String[]{"1 Person", "2 People", "1-2 People", "2-4 People", "4-6 People", "6+ People"};
    String[] recipeTypes = new String[]{"All", "Vegetarian", "Vegan"};
    Button addStepButton;
    LinearLayout stepListLayout;
    Button uploadRecipeButton;
    DatabaseMethods databaseMethods;
    Button addImageButton;
    ImageView recipeImagePlaceholder;
    String imageuuid;
    CheckBox isHealthyCheckbox;
    Spinner recipeTypeSpinner;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST=71;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;


    public AddRecipeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRecipeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRecipeFragment newInstance(String param1, String param2) {
        AddRecipeFragment fragment = new AddRecipeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_add_recipe, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Recipe");

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        databaseMethods = new DatabaseMethods(getContext());

        recipeNameInput = view.findViewById(R.id.addRecipeNameInput);

        ingredientListLayout = view.findViewById(R.id.layout_ingredient_list);
        addIngredientButton = view.findViewById(R.id.addIngredientToFormButton);

        stepListLayout = view.findViewById(R.id.layout_step_list);
        addStepButton = view.findViewById(R.id.addStepToFormButton);

        addImageButton = view.findViewById(R.id.addImageButton);

        uploadRecipeButton = view.findViewById(R.id.uploadRecipeButton);

        servingDropdown = view.findViewById(R.id.servingDropdown);
        ArrayAdapter servingAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, serves);
        servingDropdown.setAdapter(servingAdapter);

        cookingTimeDropdown = view.findViewById(R.id.cookingTimeSpinner);
        ArrayAdapter cookingTimeAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, cookingTimes);
        cookingTimeDropdown.setAdapter(cookingTimeAdapter);

        isHealthyCheckbox=view.findViewById(R.id.recipeIsHealthyCheckbox);

        recipeTypeSpinner=view.findViewById(R.id.recipeTypeSpinner);
        recipeTypeSpinner.setAdapter(new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, recipeTypes));

        recipeImagePlaceholder = view.findViewById(R.id.recipeImagePlaceholder);

        setAddImageButtonListener();

        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredientView();
            }
        });

        addStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStepView();
            }
        });

        uploadRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadRecipe();
            }
        });

        return view;
    }

    private void addIngredientView() {
        View ingredientView = getLayoutInflater().inflate(R.layout.row_add_ingredient, null, false);

        Spinner measurementType = ingredientView.findViewById(R.id.measurementTypeDropdownRecipe);
        ImageView removeImage = ingredientView.findViewById(R.id.imageRemove);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, measurementTypes);
        measurementType.setAdapter(arrayAdapter);

        ingredientListLayout.addView(ingredientView);

        removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeIngredientView(ingredientView);
            }
        });
    }

    private void removeIngredientView(View v) {
        ingredientListLayout.removeView(v);
    }

    private void addStepView() {
        View stepView = getLayoutInflater().inflate(R.layout.row_add_step, null, false);

        ImageView removeStepImage = stepView.findViewById(R.id.imageRemoveStep);

        stepListLayout.addView(stepView);

        removeStepImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeStepView(stepView);
            }
        });
    }

    private void removeStepView(View v) {
        stepListLayout.removeView(v);
    }


    private ArrayList<String> getAllIngredients() {
        ArrayList<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientListLayout.getChildCount(); i++) {
            EditText ingredientInput = ingredientListLayout.getChildAt(i).findViewById(R.id.ingredientNameInput);
            EditText amountInput = ingredientListLayout.getChildAt(i).findViewById(R.id.ingredientAmountInput);
            Spinner measurementTypeDropdown = ingredientListLayout.getChildAt(i).findViewById(R.id.measurementTypeDropdownRecipe);
            String ingredient = ingredientInput.getText().toString();
            String amount = amountInput.getText().toString();
            String measurementType = measurementTypeDropdown.getSelectedItem().toString();
            if (ingredient.isEmpty()) {
                Toast.makeText(getContext(), "You cannot leave any fields blank", Toast.LENGTH_SHORT).show();
                return null;
            } else if (amount.isEmpty() || amount.equals("0")) {
                Toast.makeText(getContext(), "You cannot leave any fields blank", Toast.LENGTH_SHORT).show();
                return null;
            }
            ingredients.add(ingredient + "," + amount + "," + measurementType);
        }
        return ingredients;
    }

    private ArrayList<String> getAllSteps() {
        ArrayList<String> steps = new ArrayList<>();
        for (int i = 0; i < stepListLayout.getChildCount(); i++) {
            EditText stepInput = stepListLayout.getChildAt(i).findViewById(R.id.stepInput);
            String step = stepInput.getText().toString();
            steps.add(step);
        }
        return steps;
    }

    public void setAddImageButtonListener(){
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                recipeImagePlaceholder.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Unable to upload this image", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void uploadRecipe() {
        if (ingredientListLayout.getChildCount() == 0) {
            Toast.makeText(getContext(), "You must add at least 1 ingredient", Toast.LENGTH_SHORT).show();
            return;
        }

        if (stepListLayout.getChildCount() == 0) {
            Toast.makeText(getContext(), "You must add at least 1 step", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<String> ingredients = getAllIngredients();
        ArrayList<String> steps = getAllSteps();

        String recipeName = recipeNameInput.getText().toString().toUpperCase();
        String cookingTime = cookingTimeDropdown.getSelectedItem().toString();
        String serves = servingDropdown.getSelectedItem().toString();
        String recipeType=recipeTypeSpinner.getSelectedItem().toString();
        boolean isHealthy=isHealthyCheckbox.isChecked();


        if (ingredients.isEmpty()) {
            return;
        } else if (steps.isEmpty()) {
            return;
        }

        if (recipeName.isEmpty()) {
            Toast.makeText(getContext(), "You must add a recipe name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(recipeImagePlaceholder.getDrawable()==null){
            Toast.makeText(getContext(), "You must upload a recipe image", Toast.LENGTH_SHORT).show();
            return;
        }

        String ingredientsForDb = "";
        String stepsForDb = "";

        for(int i=0;i<ingredients.size();i++){
            if(i!=ingredients.size()-1){
                ingredientsForDb = ingredientsForDb + (ingredients.get(i)) + "|";
            }else{
                ingredientsForDb = ingredientsForDb + (ingredients.get(i));
            }
        }


        for(int i=0;i<steps.size();i++){
            if(i!=steps.size()-1){
                stepsForDb = stepsForDb + (steps.get(i)) + "|";
            }else{
                stepsForDb = stepsForDb + (steps.get(i));
            }
        }


        databaseMethods.addRecipe(recipeName, cookingTime, serves, ingredientsForDb, stepsForDb, isHealthy, recipeType);
        uploadImage();
        databaseMethods.uploadRecipeImageUrl(recipeName, imageuuid);

        getFragmentManager().beginTransaction().replace(R.id.fl_wrapper, new MyRecipesFragment()).addToBackStack(null).commit();
    }

    public void uploadImage(){
        imageuuid = UUID.randomUUID().toString();
        if(filePath!=null){
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading Image...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + imageuuid);
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                }
            });
        }
    }

}