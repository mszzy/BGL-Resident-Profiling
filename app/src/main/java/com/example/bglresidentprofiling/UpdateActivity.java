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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
public class UpdateActivity extends AppCompatActivity {
    ImageView updateImage;
    Button updateButton;
    EditText updateFirst, updateLast, updateMid, updateAge, updateSex, updateBirthday, updateMarital, updateNumber, updateAddress, updateOccu, updateReg, updateHoNum, updateLevelEduc, updateSoloParent, updateSeniorCitizen, updateRegisteredVoter;
    String firstname, lastname, middle, age, sex, birthday, marital, number, address, occupation, religion, houseNo, householdMem;
    String imageUrl;
    String key, oldImageURL;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    DatabaseReference reference;

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
        updateSex = findViewById(R.id.updateSex);
        updateBirthday = findViewById(R.id.updateBirthday);
        updateMarital = findViewById(R.id.updateMarital);
        updateNumber = findViewById(R.id.updateNumber);
        updateAddress = findViewById(R.id.updateAddress);
        updateOccu = findViewById(R.id.updateOccu);
        updateReg = findViewById(R.id.updateReg);
        updateHoNum = findViewById(R.id.updateHoNum);
        updateLevelEduc = findViewById(R.id.updateLevelEduc);
        updateSoloParent = findViewById(R.id.updateSoloParent);
        updateSeniorCitizen = findViewById(R.id.updateSeniorCitizen);
        updateRegisteredVoter = findViewById(R.id.updateRegisteredVoter);


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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Glide.with(UpdateActivity.this).load(bundle.getString("Image")).into(updateImage);
            updateFirst.setText(bundle.getString("Firstname"));
            updateMid.setText(bundle.getString("Middlename"));
            updateLast.setText(bundle.getString("Lastname"));
            updateAge.setText(bundle.getString("Age"));
            updateSex.setText(bundle.getString("Sex"));
            updateBirthday.setText(bundle.getString("Birthday"));
            updateMarital.setText(bundle.getString("Marital"));
            updateNumber.setText(bundle.getString("Contact No."));
            updateAddress.setText(bundle.getString("Address"));
            updateOccu.setText(bundle.getString("Occupation"));
            updateReg.setText(bundle.getString("Religion"));
            updateHoNum.setText(bundle.getString("House No."));
            updateLevelEduc.setText(bundle.getString("Level of Education"));
            updateSoloParent.setText(bundle.getString("Solo Parent"));
            updateSeniorCitizen.setText(bundle.getString("Senior Citizen"));
            updateRegisteredVoter.setText(bundle.getString("Registered Voter"));
            key = bundle.getString("Key");
            oldImageURL = bundle.getString("Image");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("residents information").child(key);
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

    public void updateData() {
        String firstname = updateFirst.getText().toString().trim();
        String middle = updateMid.getText().toString().trim();
        String lastname = updateLast.getText().toString();
        String age = updateAge.getText().toString();
        String sex = updateSex.getText().toString();
        String birthday = updateBirthday.getText().toString();
        String marital = updateMarital.getText().toString();
        String number = updateNumber.getText().toString();
        String address = updateAddress.getText().toString();
        String occupation = updateOccu.getText().toString();
        String religion = updateReg.getText().toString();
        String houseNo = updateHoNum.getText().toString();
        String levelOfEduc = updateLevelEduc.getText().toString();
        String parentSolo = updateSoloParent.getText().toString();
        String seniorCitiz = updateSeniorCitizen.getText().toString();
        String regisVoter = updateRegisteredVoter.getText().toString();
        DatClass datClass = new DatClass(firstname, lastname, middle, age, sex, birthday, marital, number, address, occupation, religion, houseNo, levelOfEduc, parentSolo, seniorCitiz, regisVoter, imageUrl);
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