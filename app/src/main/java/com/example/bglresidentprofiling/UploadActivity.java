package com.example.bglresidentprofiling;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UploadActivity extends AppCompatActivity {
    ImageView uploadImage;
    Button saveButton;
    EditText uploadFirst, uploadLast, uploadMid, uploadAge, uploadBirthday, uploadMarital, uploadNumber, uploadAddress, uploadOccu, uploadReg, uploadHoNum, levelEduc, soloParent, seniorCitizen, registeredVoter;
    String imageURL;
    Uri uri;
    FirebaseDatabase database;
    DatabaseReference reference;

    Spinner sexSpinner, marStatus, regSpinner, educSpinner, addressSpinner, soloSpinner, voterSpinner, seniorSpinner;
    String[] seniorList = {
            "Registered Senior Citizen",
            "Yes",
            "No",
            "Not Applicable",
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

    private Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        uploadImage = findViewById(R.id.uploadImage);
        uploadLast = findViewById(R.id.uploadLast);
        uploadFirst = findViewById(R.id.uploadFirst);
        uploadMid = findViewById(R.id.uploadMid);
        uploadAge = findViewById(R.id.uploadAge);

        uploadBirthday = findViewById(R.id.uploadBirthday);

        uploadNumber = findViewById(R.id.uploadNumber);

        uploadOccu = findViewById(R.id.uploadOccu);

        uploadHoNum = findViewById(R.id.uploadHoNum);


        saveButton = findViewById(R.id.saveButton);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(UploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });


        sexSpinner = findViewById(R.id.simpleLangSpinner);
        sexSpinner.setAdapter(
                new ArrayAdapter<>(UploadActivity.this, android.R.layout.simple_spinner_dropdown_item, sexList));

        marStatus = findViewById(R.id.uploadMarital);
        marStatus.setAdapter(
                new ArrayAdapter<>(UploadActivity.this, android.R.layout.simple_spinner_dropdown_item, statusList));

        regSpinner = findViewById(R.id.uploadReg);
        regSpinner.setAdapter(
                new ArrayAdapter<>(UploadActivity.this, android.R.layout.simple_spinner_dropdown_item, religionList));

        educSpinner = findViewById(R.id.levelEduc);
        educSpinner.setAdapter(
                new ArrayAdapter<>(UploadActivity.this, android.R.layout.simple_spinner_dropdown_item, educationList));

        soloSpinner = findViewById(R.id.soloParent);
        soloSpinner.setAdapter(
                new ArrayAdapter<>(UploadActivity.this, android.R.layout.simple_spinner_dropdown_item, parentList));

        seniorSpinner = findViewById(R.id.seniorCitizen);
        seniorSpinner.setAdapter(
                new ArrayAdapter<>(UploadActivity.this, android.R.layout.simple_spinner_dropdown_item, seniorList));

        voterSpinner = findViewById(R.id.registeredVoter);
        voterSpinner.setAdapter(
                new ArrayAdapter<>(UploadActivity.this, android.R.layout.simple_spinner_dropdown_item, voterList));

        addressSpinner = findViewById(R.id.uploadAddress);
        addressSpinner.setAdapter(
                new ArrayAdapter<>(UploadActivity.this, android.R.layout.simple_spinner_dropdown_item, purokList));


        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Select Date");
        MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {

                    uploadBirthday.setText(new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(materialDatePicker.getSelection()));
                    uploadAge.setText(String.valueOf(calcAge(uploadBirthday.getText().toString())));
                });


        uploadBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "material_date_picker");
            }
        });
    }


    public void saveData() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
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
                imageURL = urlImage.toString();
                uploadData();
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


    public void uploadData() {
        String firstname = uploadFirst.getText().toString();
        String lastname = uploadLast.getText().toString();
        String middle = uploadMid.getText().toString();
        String sex = sexList[sexSpinner.getSelectedItemPosition()];
        String birthday = uploadBirthday.getText().toString();
        int age = calcAge(birthday);
        String marital = statusList[marStatus.getSelectedItemPosition()];
        String number = uploadNumber.getText().toString();
        String address = purokList[addressSpinner.getSelectedItemPosition()];
        String occupation = uploadOccu.getText().toString();
        String religion = religionList[regSpinner.getSelectedItemPosition()];
        String houseNo = uploadHoNum.getText().toString();
        String levelOfEduc = educationList[educSpinner.getSelectedItemPosition()];
        String parentSolo = parentList[soloSpinner.getSelectedItemPosition()];
        String seniorCitiz = seniorList[seniorSpinner.getSelectedItemPosition()];
        String regisVoter = voterList[voterSpinner.getSelectedItemPosition()];

        DatClass datClass = new DatClass(firstname, lastname, middle, String.valueOf(age), sex, birthday, marital, number, address, occupation, religion, houseNo, levelOfEduc, parentSolo, seniorCitiz, regisVoter, imageURL);
        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("residents information").child(currentDate)
                .setValue(datClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UploadActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

}