package com.example.bglresidentprofiling;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
public class DetailActivity extends AppCompatActivity {
    TextView detailFirst, detailMid, detailLast, detailAge, detailSex, detailBirthday, detailMarital, detailNumber, detailAddress, detailOccu, detailReg, detailHoNum, detailLevelOfEduc, detailParentSolo, detailSenCitiz, detailRegVoter;
    ImageView detailImage;
    FloatingActionButton deleteButton, editButton, printButton;
    String key = "";
    String imageUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailFirst = findViewById(R.id.detailFirst);
        detailImage = findViewById(R.id.detailImage);
        detailMid = findViewById(R.id.detailMid);
        deleteButton = findViewById(R.id.deleteButton);
        printButton = findViewById(R.id.printButton);
        detailAge = findViewById(R.id.detailAge);
        detailSex = findViewById(R.id.detailSex);
        detailBirthday = findViewById(R.id.detailBirthday);
        detailMarital = findViewById(R.id.detailMarital);
        detailNumber = findViewById(R.id.detailNumber);
        editButton = findViewById(R.id.editButton);
        detailLast = findViewById(R.id.detailLast);
        detailAddress = findViewById(R.id.detailAddress);
        detailOccu = findViewById(R.id.detailOccu);
        detailReg = findViewById(R.id.detailReg);
        detailHoNum = findViewById(R.id.detailHoNum);
        detailLevelOfEduc = findViewById(R.id.detailLevelEduc);
        detailParentSolo = findViewById(R.id.detailSoloParent);
        detailSenCitiz = findViewById(R.id.detailSeniorCitiz);
        detailRegVoter = findViewById(R.id.detailRegisVoter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailFirst.setText(bundle.getString("Firstname"));
            detailMid.setText(bundle.getString("Middlename"));
            detailLast.setText(bundle.getString("Lastname"));
            detailAge.setText(bundle.getString("Age"));
            detailSex.setText(bundle.getString("Sex"));
            detailBirthday.setText(bundle.getString("Birthday"));
            detailMarital.setText(bundle.getString("Marital"));
            detailNumber.setText(bundle.getString("Contact No."));
            detailAddress.setText(bundle.getString("Address"));
            detailOccu.setText(bundle.getString("Occupation"));
            detailReg.setText(bundle.getString("Religion"));
            detailHoNum.setText(bundle.getString("House No."));
            detailLevelOfEduc.setText(bundle.getString("Level of Education"));
            detailParentSolo.setText(bundle.getString("Solo Parent"));
            detailSenCitiz.setText(bundle.getString("Senior Citizen"));
            detailRegVoter.setText(bundle.getString("Registered Voter"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("residents information");
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(DetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, UpdateActivity.class)
                        .putExtra("Firstname", detailFirst.getText().toString())
                        .putExtra("Middlename", detailMid.getText().toString())
                        .putExtra("Lastname", detailLast.getText().toString())
                        .putExtra("Age", detailAge.getText().toString())
                        .putExtra("Sex", detailSex.getText().toString())
                        .putExtra("Birthday", detailBirthday.getText().toString())
                        .putExtra("Marital", detailMarital.getText().toString())
                        .putExtra("Contact No.", detailNumber.getText().toString())
                        .putExtra("Address", detailAddress.getText().toString())
                        .putExtra("Occupation", detailOccu.getText().toString())
                        .putExtra("Religion", detailReg.getText().toString())
                        .putExtra("House No.", detailHoNum.getText().toString())
                        .putExtra("Level of Education", detailLevelOfEduc.getText().toString())
                        .putExtra("Solo Parent", detailParentSolo.getText().toString())
                        .putExtra("Senior Citizen", detailSenCitiz.getText().toString())
                        .putExtra("Registered Voter", detailRegVoter.getText().toString())
                        .putExtra("Image", imageUrl)
                        .putExtra("Key", key);
                startActivity(intent);
            }
        });

        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, PrintActivity.class)
                        .putExtra("Firstname", detailFirst.getText().toString())
                        .putExtra("Middlename", detailMid.getText().toString())
                        .putExtra("Lastname", detailLast.getText().toString())
                        .putExtra("Age", detailAge.getText().toString())
                        .putExtra("Sex", detailSex.getText().toString())
                        .putExtra("Birthday", detailBirthday.getText().toString())
                        .putExtra("Marital", detailMarital.getText().toString())
                        .putExtra("Contact No.", detailNumber.getText().toString())
                        .putExtra("Address", detailAddress.getText().toString())
                        .putExtra("Occupation", detailOccu.getText().toString())
                        .putExtra("Religion", detailReg.getText().toString())
                        .putExtra("House No.", detailHoNum.getText().toString())
                        .putExtra("Level of Education", detailLevelOfEduc.getText().toString())
                        .putExtra("Solo Parent", detailParentSolo.getText().toString())
                        .putExtra("Senior Citizen", detailSenCitiz.getText().toString())
                        .putExtra("Registered Voter", detailRegVoter.getText().toString())
                        .putExtra("Image", imageUrl)
                        .putExtra("Key", key);
                startActivity(intent);
            }
        });
    }
}