package com.example.bglresidentprofiling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class PrintGenderActivity extends AppCompatActivity {

    Button printGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_gender);
        printGender = findViewById(R.id.printGender);

        PieChart pieChart = findViewById(R.id.genderChart);

        ArrayList<PieEntry> profiles = new ArrayList<>();
        profiles.add(new PieEntry(14, "Female"));
        profiles.add(new PieEntry(8, "Male"));

        PieDataSet pieDataSet = new PieDataSet(profiles, "Sex");
        pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Sex Report");
        pieChart.animate();

        ActivityCompat.requestPermissions(PrintGenderActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        printGender.setOnClickListener(new View.OnClickListener() {
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
                "------------------------------------------------------- "+"Statistical Data" + " -------------------------------------------------------" + "\n" +
                "   " + "\n" +
                "   " + "Gender Report of Brgy. General Lim" + "   " + "\n" +
                "   " + "\n" +
                "     " + "    Female Residents" + "    " + "\n" +
                "     " + "      List of names:" + "    " + "\n" +
                "     " + "      1. Alonzo, Angeline Crisanto"  + "    " + "\n" +
                "     " + "      2. Aquino, Alliya Cruz" + "    " + "\n" +
                "     " + "      3. Basa, Camille Delgado" + "    " + "\n" +
                "     " + "      4. Cabrera, Mari Ystinelli Axsa" + "    " + "\n" +
                "     " + "      5. Frenilla, Nina Basa" + "    " + "\n" +
                "     " + "      6. Malit, Jenny Bartolome" + "    " + "\n" +
                "     " + "      7. Naquil, Kristine Den Hoson" + "    " + "\n" +
                "     " + "      8. Pangan, Micaella Abanilla" + "    " + "\n" +
                "     " + "      9. Paule, Susan Balbuena" + "    " + "\n" +
                "     " + "      10. Penaloza, Maria Salazar" + "    " + "\n" +
                "     " + "      11. Samoza, Celine Montecarlo" + "    " + "\n" +
                "     " + "      12. Sanchez, Maycie Dela Cruz" + "    " + "\n" +
                "     " + "      13. Sanggalang, Marry Ann Santos" + "    " + "\n" +
                "     " + "      14. Singca, Karen Kipte" + "    " + "\n" +
                "   " + "\n" +
                "     " + "    Male Residents" + "    " + "\n" +
                "     " + "      List of names:" + "    " + "\n" +
                "     " + "      1. Alonzo, Rhoden Fernando" + "    " + "\n" +
                "     " + "      2. De Jesus, Carlo John Bartolo" + "    " + "\n" +
                "     " + "      3. Francisco, Arnel De Leon" + "    " + "\n" +
                "     " + "      4. Hernandez, Gabriel Hanz Macabre" + "    " + "\n" +
                "     " + "      5. Jardino, Robert Samson" + "    " + "\n" +
                "     " + "      6. Santos, Andrew Rivera" + "    " + "\n" +
                "     " + "      7. Singca, Rolando Toreno" + "    " + "\n" +
                "     " + "      8. Sison, Gene Layug"+ "    " + "\n";


        int x = 10, y = 25;

        for (String line : myString.split("\n")) {
            myPage.getCanvas().drawText(line, x, y, myPaint);
            y += myPaint.descent() - myPaint.ascent();
        }

        myPdfDocument.finishPage(myPage);

        try {
            String myFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File myFile = new File(myFilePath, "Gender Report.pdf");

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