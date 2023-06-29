package com.example.bglresidentprofiling;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity {
    ImageView updateImage;
    Button updateButton;
    TextView updateBirthday;
    EditText updateFirst, updateLast, updateMid, updateAge, updateNumber, updateOccu, updateHoNum;
    String imageUrl;
    String key, oldImageURL;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    DatabaseReference reference;

    Spinner sexSpinner, marStatus, regSpinner, educSpinner, addressSpinner, soloSpinner, voterSpinner, seniorSpinner;
    String[] sexList = {
            "Sex",
            "Female",
            "Male",
    };

    String[] statusList = {
            "Marital Status",
            "Single",
            "Married",
            "Widowed",
            "Separated",
    };

    String[] purokList = {
            "Purok Number",
            "Purok 1",
            "Purok 2",
            "Purok 3",
            "Purok 4",
            "Purok 5",
            "Purok 6",
            "Purok 7",
            "Purok 8",
    };


    String[] religionList = {
            "Religion",
            "Roman Catholic",
            "Iglesia ni Cristo",
            "Born Again Christian",
            "Islam",
            "Seventh Day Adventist",
            "Aglipay",
            "Iglesia Filipina Independiente",
            "Bible Baptist Church",
            "United Church of Christ in the Philippines",
            "Jehovah's Witness",
            "Church of Christ",
            "Other religious affiliations",
            "None"
    };

    String[] educationList = {
            "Highest Educational Attainment",
            "Elementary Level",
            "Elementary Graduate",
            "High School Graduate",
            "High School Level",
            "Vocational Level",
            "College Level",
            "College Graduate",
    };

    String[] seniorList = {
            "Registered Senior Citizen",
            "Yes",
            "No",
            "Not Applicable",
    };
    String[] voterList = {
            "Registered Voter",
            "Yes",
            "No",
            "Not Applicable",
    };
    String[] parentList = {
            "Solo Parent",
            "Yes",
            "No",
            "Not Applicable",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        reference = FirebaseDatabase.getInstance().getReference("residents information");
        updateButton = findViewById(R.id.updateButton);
        updateMid = findViewById(R.id.updateMiddle);
        updateImage = findViewById(R.id.updateImage);
        updateLast = findViewById(R.id.updateLast);
        updateFirst = findViewById(R.id.updateFirst);
        updateAge = findViewById(R.id.updateAge);

        updateBirthday = findViewById(R.id.updateBirthday);

        updateNumber = findViewById(R.id.updateNumber);

        updateOccu = findViewById(R.id.updateOccu);

        updateHoNum = findViewById(R.id.updateHoNum);
        voterSpinner = findViewById(R.id.updateRegisteredVoter);
        sexSpinner = findViewById(R.id.updateSex);
        marStatus = findViewById(R.id.updateMarital);
        regSpinner = findViewById(R.id.updateReg);
        educSpinner = findViewById(R.id.updateLevelEduc);
        addressSpinner = findViewById(R.id.updateAddress);
        soloSpinner = findViewById(R.id.updateSoloParent);
        seniorSpinner = findViewById(R.id.updateSeniorCitizen);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            updateImage.setImageURI(uri);
                        } else {
                            Toast.makeText(UpdateActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );



        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        sexSpinner.setAdapter(
                new ArrayAdapter<>(UpdateActivity.this, android.R.layout.simple_spinner_dropdown_item, sexList));

        marStatus.setAdapter(
                new ArrayAdapter<>(UpdateActivity.this, android.R.layout.simple_spinner_dropdown_item, statusList));

        regSpinner.setAdapter(
                new ArrayAdapter<>(UpdateActivity.this, android.R.layout.simple_spinner_dropdown_item, religionList));

        educSpinner.setAdapter(
                new ArrayAdapter<>(UpdateActivity.this, android.R.layout.simple_spinner_dropdown_item, educationList));

        addressSpinner.setAdapter(
                new ArrayAdapter<>(UpdateActivity.this, android.R.layout.simple_spinner_dropdown_item, purokList));


        soloSpinner.setAdapter(
                new ArrayAdapter<>(UpdateActivity.this, android.R.layout.simple_spinner_dropdown_item, parentList));

        seniorSpinner.setAdapter(
                new ArrayAdapter<>(UpdateActivity.this, android.R.layout.simple_spinner_dropdown_item, seniorList));

        voterSpinner.setAdapter(
                new ArrayAdapter<>(UpdateActivity.this, android.R.layout.simple_spinner_dropdown_item, voterList));

        MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Select Date");
        MaterialDatePicker<Long> materialDatePicker = materialDateBuilder.build();

        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {

                    updateBirthday.setText(new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(materialDatePicker.getSelection()));
                    updateAge.setText(String.valueOf(calcAge(updateBirthday.getText().toString())));
                });
        updateBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!materialDatePicker.isAdded()){

                materialDatePicker.show(getSupportFragmentManager(), "material_date_picker");
                }
            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Glide.with(UpdateActivity.this).load(bundle.getString("Image")).into(updateImage);
            updateFirst.setText(bundle.getString("Firstname"));
            updateMid.setText(bundle.getString("Middlename"));
            updateLast.setText(bundle.getString("Lastname"));
            updateAge.setText(bundle.getString("Age"));
            sexSpinner.setSelection( Arrays.asList(sexList).indexOf(bundle.getString("Sex")));
            updateBirthday.setText(bundle.getString("Birthday"));
            marStatus.setSelection( Arrays.asList(statusList).indexOf(bundle.getString("Marital")));
            updateNumber.setText(bundle.getString("Contact No."));
            addressSpinner.setSelection( Arrays.asList(purokList).indexOf(bundle.getString("Address")));
            updateOccu.setText(bundle.getString("Occupation"));
            regSpinner.setSelection( Arrays.asList(purokList).indexOf(bundle.getString("Religion")));
            updateHoNum.setText(bundle.getString("House No."));
            educSpinner.setSelection( Arrays.asList(educationList).indexOf(bundle.getString("Level of Education")));
            soloSpinner.setSelection( Arrays.asList(parentList).indexOf(bundle.getString("Solo Parent")));
            seniorSpinner.setSelection( Arrays.asList(seniorList).indexOf(bundle.getString("Senior Citizen")));
            voterSpinner.setSelection( Arrays.asList(voterList).indexOf(bundle.getString("Registered Voter")));
            key = bundle.getString("Key");
            oldImageURL = bundle.getString("Image");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("residents information").child(key);
    }

    public void saveData() {
        storageReference = FirebaseStorage.getInstance().getReference().child("Android Images").child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                updateData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public int calcAge(String birthday) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        try {
            Date birthDate = sdf.parse(birthday);
            Calendar birthCal = Calendar.getInstance();
            birthCal.setTime(birthDate);

            Calendar currentCal = Calendar.getInstance();

            int age = currentCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);
            if (currentCal.get(Calendar.MONTH) < birthCal.get(Calendar.MONTH)) {
                age--;
            } else if (currentCal.get(Calendar.MONTH) == birthCal.get(Calendar.MONTH)
                    && currentCal.get(Calendar.DAY_OF_MONTH) < birthCal.get(Calendar.DAY_OF_MONTH)) {
                age--;
            }
            return age;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateData() {
        String firstname = updateFirst.getText().toString().trim();
        String middle = updateMid.getText().toString().trim();
        String lastname = updateLast.getText().toString();
        String sex =sexList[sexSpinner.getSelectedItemPosition()];
        String birthday = updateBirthday.getText().toString();
        int age = calcAge(birthday);
        String marital = statusList[marStatus.getSelectedItemPosition()];
        String number = updateNumber.getText().toString();
        String address = purokList[addressSpinner.getSelectedItemPosition()];
        String occupation = updateOccu.getText().toString();
        String religion = religionList[regSpinner.getSelectedItemPosition()];
        String houseNo = updateHoNum.getText().toString();
        String levelOfEduc = educationList[educSpinner.getSelectedItemPosition()];
        String parentSolo = parentList[soloSpinner.getSelectedItemPosition()];
        String seniorCitiz = seniorList[seniorSpinner.getSelectedItemPosition()];
        String regisVoter = voterList[voterSpinner.getSelectedItemPosition()];
        DatClass datClass = new DatClass(firstname, lastname, middle, String.valueOf(age), sex, birthday, marital, number, address, occupation, religion, houseNo, levelOfEduc, parentSolo, seniorCitiz, regisVoter, imageUrl);
        databaseReference.setValue(datClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL);
                    reference.delete();
                    Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}