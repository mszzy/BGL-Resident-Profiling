package com.example.bglresidentprofiling;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.text.DateFormat;
import java.util.Calendar;

public class UploadActivity extends AppCompatActivity {
    ImageView uploadImage;
    Button saveButton;
    EditText uploadFirst, uploadLast, uploadMid, uploadAge, uploadSex, uploadBirthday, uploadMarital, uploadNumber, uploadAddress, uploadOccu, uploadReg, uploadHoNum, levelEduc, soloParent, seniorCitizen, registeredVoter;
    String imageURL;
    Uri uri;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        uploadImage = findViewById(R.id.uploadImage);
        uploadLast = findViewById(R.id.uploadLast);
        uploadFirst = findViewById(R.id.uploadFirst);
        uploadMid = findViewById(R.id.uploadMid);
        uploadAge = findViewById(R.id.uploadAge);
        uploadSex = findViewById(R.id.uploadSex);
        uploadBirthday = findViewById(R.id.uploadBirthday);
        uploadMarital = findViewById(R.id.uploadMarital);
        uploadNumber = findViewById(R.id.uploadNumber);
        uploadAddress = findViewById(R.id.uploadAddress);
        uploadOccu = findViewById(R.id.uploadOccu);
        uploadReg = findViewById(R.id.uploadReg);
        uploadHoNum = findViewById(R.id.uploadHoNum);
        levelEduc = findViewById(R.id.levelEduc);
        soloParent = findViewById(R.id.soloParent);
        seniorCitizen = findViewById(R.id.seniorCitizen);
        registeredVoter = findViewById(R.id.registeredVoter);

        saveButton = findViewById(R.id.saveButton);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
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
    }

    public void saveData(){
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
                while (!uriTask.isComplete());
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
    public void uploadData(){
        String firstname = uploadFirst.getText().toString();
        String lastname = uploadLast.getText().toString();
        String middle = uploadMid.getText().toString();
        String age = uploadAge.getText().toString();
        String sex = uploadSex.getText().toString();
        String birthday = uploadBirthday.getText().toString();
        String marital = uploadMarital.getText().toString();
        String number = uploadNumber.getText().toString();
        String address = uploadAddress.getText().toString();
        String occupation = uploadOccu.getText().toString();
        String religion = uploadReg.getText().toString();
        String houseNo = uploadHoNum.getText().toString();
        String levelOfEduc = levelEduc.getText().toString();
        String parentSolo = soloParent.getText().toString();
        String seniorCitiz = seniorCitizen.getText().toString();
        String regisVoter = registeredVoter.getText().toString();

        DatClass datClass = new DatClass(firstname, lastname, middle, age, sex, birthday, marital, number, address, occupation, religion, houseNo, levelOfEduc, parentSolo, seniorCitiz, regisVoter, imageURL);
        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.
        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        FirebaseDatabase.getInstance().getReference("residents information").child(currentDate)
                .setValue(datClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
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