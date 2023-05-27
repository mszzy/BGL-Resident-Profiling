package com.example.bglresidentprofiling;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
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

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PrintActivity extends AppCompatActivity {

    TextView detailFirst, detailMid, detailLast, detailAge, detailSex, detailBirthday, detailMarital, detailNumber, detailAddress, detailOccu, detailReg, detailHoNum, detailLevelOfEduc, detailParentSolo, detailSenCitiz, detailRegVoter;
    ImageView detailImage;
    Button printProfile;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        detailFirst = findViewById(R.id.detailFirst);
        detailImage = findViewById(R.id.detailImage);
        detailMid = findViewById(R.id.detailMid);
        detailAge = findViewById(R.id.detailAge);
        detailSex = findViewById(R.id.detailSex);
        detailBirthday = findViewById(R.id.detailBirthday);
        detailMarital = findViewById(R.id.detailMarital);
        detailNumber = findViewById(R.id.detailNumber);
        detailLast = findViewById(R.id.detailLast);
        detailAddress = findViewById(R.id.detailAddress);
        detailOccu = findViewById(R.id.detailOccu);
        detailReg = findViewById(R.id.detailReg);
        detailHoNum = findViewById(R.id.detailHoNum);
        detailLevelOfEduc = findViewById(R.id.detailLevelEduc);
        detailParentSolo = findViewById(R.id.detailSoloParent);
        detailSenCitiz = findViewById(R.id.detailSeniorCitiz);
        detailRegVoter = findViewById(R.id.detailRegisVoter);
        printProfile = findViewById(R.id.printProfile);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
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
            ActivityCompat.requestPermissions(PrintActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        }

        printProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMyPDF(
                        detailFirst.getText().toString(),
                        detailMid.getText().toString(),
                        detailLast.getText().toString(),
                        detailAge.getText().toString(),
                        detailSex.getText().toString(),
                        detailBirthday.getText().toString(),
                        detailMarital.getText().toString(),
                        detailNumber.getText().toString(),
                        detailAddress.getText().toString(),
                        detailOccu.getText().toString(),
                        detailReg.getText().toString(),
                        detailHoNum.getText().toString(),
                        detailLevelOfEduc.getText().toString(),
                        detailParentSolo.getText().toString(),
                        detailSenCitiz.getText().toString(),
                        detailRegVoter.getText().toString()
                );
            }
        });
    }

    public void createMyPDF(String detailFirst, String detailMid, String detailLast, String detailAge, String detailSex, String detailBirthday, String detailMarital, String detailNumber, String detailAddress, String detailOccu, String detailReg, String detailHoNum, String detailLevelOfEduc, String detailParentSolo, String detailSenCitiz, String detailRegVoter) {
        PdfDocument myPdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(500, 550, 1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);


        Paint myPaint = new Paint();

        String myString = "   " + "\n" +
                "--------------------------------------------------------------- "+"Personal Information" + " ---------------------------------------------------------" + "\n" +
                "     " + "\n" +
                "     "+"Firstname: " + detailFirst + "\n" +
                "     "+"Middlename: " + detailMid + "\n" +
                "     "+"Lastname: " + detailLast + "\n" +
                "     "+"Age: " + detailAge + "\n" +
                "     "+"Sex: " + detailSex + "\n" +
                "     "+"Birthday: " + detailBirthday + "\n" +
                "     "+"Marital: " + detailMarital + "\n" +
                "     "+"Contact No.: " + detailNumber + "\n" +
                "     "+"Address: " + detailAddress + "\n" +
                "     "+"Occupation: " + detailOccu + "\n" +
                "     "+"Religion: " + detailReg + "\n" +
                "     "+"House No.: " + detailHoNum + "\n" +
                "     "+"Level of Education: " + detailLevelOfEduc + "\n" +
                "     "+"Solo Parent: " + detailParentSolo + "\n" +
                "     "+"Senior Citizen: " + detailSenCitiz + "\n" +
                "     "+"Registered Voter: " + detailRegVoter + "\n"+
                "      "+ "\n" +
        "----------------------------------------------------------------------------------------------------------------------------------------------------------------" + "\n";

        int x = 10, y = 25;

        for (String line : myString.split("\n")) {
            myPage.getCanvas().drawText(line, x, y, myPaint);
            y += myPaint.descent() - myPaint.ascent();
        }

        myPdfDocument.finishPage(myPage);

        try {
            String myFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File myFile = new File(myFilePath, "Resident Information.pdf");

            OutputStream outputStream = new FileOutputStream(myFile);
            myPdfDocument.writeTo(outputStream);
            Toast.makeText(this, "Printed successfully", Toast.LENGTH_SHORT).show();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error printing", Toast.LENGTH_SHORT).show();
        }

        myPdfDocument.close();
    }
}