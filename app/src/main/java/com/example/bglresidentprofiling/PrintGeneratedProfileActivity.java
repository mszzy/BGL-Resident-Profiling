package com.example.bglresidentprofiling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PrintGeneratedProfileActivity extends AppCompatActivity {

    ListView myListView;
    List<DatClass> datClasses;
    Button printRL;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_generated_profile);
        printRL = findViewById(R.id.printRL);

        myListView = findViewById(R.id.myListView);
        datClasses = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("residents information");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                datClasses.clear();

                for (DataSnapshot residentDatasnap : dataSnapshot.getChildren()) {

                    DatClass datClass = residentDatasnap.getValue(DatClass.class);
                    datClasses.add(datClass);
                }

                ListAdapter adapter = new ListAdapter(PrintGeneratedProfileActivity.this, datClasses);
                myListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ActivityCompat.requestPermissions(PrintGeneratedProfileActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        printRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPDF();
            }
        });
    }

    public void createPDF() {
        PdfDocument myPdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(450, 600, 1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);


        Paint myPaint = new Paint();

        String myString = "   " + "\n" +
                "------------------------------------------------------- " + "List of Residents" + " -------------------------------------------------------" + "\n" +
                "   " + "\n" +
                "          " + "Lastname  |  Firstname   |  Middlename" + "   " + "\n" +
                "   " + "\n" +
                "     " + "1. Alonzo, Angeline C." + "\n" +
                "     " + "2. Alonzo, Rhoden F." + "\n" +
                "     " + "3. Aquino, Alliya C." + "\n" +
                "     " + "4. Basa, Camille D." + "\n" +
                "     " + "5. Cabrera, Mari Ystinelli A." + "\n" +
                "     " + "6. De Jesus, Carlo John B." + "\n" +
                "     " + "7. Francisco, Arnel D." + "\n" +
                "     " + "8. Frenilla, Nina B." + "\n" +
                "     " + "9. Hernandez, Gabriel Hanz M." + "\n" +
                "     " + "10. Jardino, Robert S." + "\n" +
                "     " + "11. Malit, Jenny B." + "\n" +
                "     " + "12. Naquil, Kristine Den H." + "\n" +
                "     " + "13. Pangan, Micaella A." + "\n" +
                "     " + "14. Paule, Susan B." + "\n" +
                "     " + "15. Penaloza, Maria S." + "\n" +
                "     " + "16. Samoza, Celine M." + "\n" +
                "     " + "17. Sanchez, Maycie D." + "\n" +
                "     " + "18. Sanggalang, Marry Ann S." + "\n" +
                "     " + "19. Santos, Andrew R." + "\n" +
                "     " + "20. Singca, Karen K." + "\n" +
                "     " + "21. Singca, Rolando T." + "\n" +
                "     " + "22. Sison, Gene L." + "\n" +
                "     " + "          " + "    " + "\n";

        int x = 10, y = 25;

        for (String line : myString.split("\n")) {
            myPage.getCanvas().drawText(line, x, y, myPaint);
            y += myPaint.descent() - myPaint.ascent();
        }

        myPdfDocument.finishPage(myPage);

        try {
            String myFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File myFile = new File(myFilePath, "Residents List.pdf");

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