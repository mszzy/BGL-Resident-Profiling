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

public class PrintPieChartActivity extends AppCompatActivity {

    Button printAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_pie_chart);
        printAG = findViewById(R.id.printAG);

        PieChart pieChart = findViewById(R.id.pieChart);

        ArrayList<PieEntry> profiles = new ArrayList<>();
        profiles.add(new PieEntry(1, "Children"));
        profiles.add(new PieEntry(2, "Teenager"));
        profiles.add(new PieEntry(15, "Adult"));
        profiles.add(new PieEntry(4, "Senior Citizen"));

        PieDataSet pieDataSet = new PieDataSet(profiles, "Age Group Count");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Residents Report");
        pieChart.animate();
        ActivityCompat.requestPermissions(PrintPieChartActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        printAG.setOnClickListener(new View.OnClickListener() {
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
                "   " + "Classification     |    Age range   |        Count" + "   " + "\n" +
                "     " + "      Children:                 0-12                      1" + "    " + "\n" +
                "     " + "      List of names:" + "    " + "\n" +
                "     " + "      1. Santos, Andrew Rivera" + "    " + "\n" +
                "   " + "\n" +
                "     " + "      Teenager:               13-19                    2" + "    " + "\n" +
                "     " + "      List of names:" + "    " + "\n" +
                "     " + "      1. De Jesus, Carlo John Bartolo" + "    " + "\n" +
                "     " + "      2. Frenilla, Nina Basa" + "    " + "\n" +
                "   " + "\n" +
                "     " + "      Adult:                      20-59                   15" + "    " + "\n" +
                "     " + "      List of names:" + "    " + "\n" +
                "     " + "      1. Alonzo, Angeline Crisanto" + "    " + "\n" +
                "     " + "      2. Alonzo, Rhoden Fernando" + "    " + "\n" +
                "     " + "      3. Aquino, Alliya Cruz" + "    " + "\n" +
                "     " + "      4. Basa, Camille Delgado" + "    " + "\n" +
                "     " + "      5. Cabrera, Mari Ystinelli Axsa" + "    " + "\n" +
                "     " + "      6. Francisco, Arnel De Leon" + "    " + "\n" +
                "     " + "      7. Hernandez, Gabriel Hanz Macabre" + "    " + "\n" +
                "     " + "      8. Malit, Jenny Bartolome" + "    " + "\n" +
                "     " + "      9. Naquil, Kristine Den Hoson" + "    " + "\n" +
                "     " + "      10. Pangan, Micaella Abanilla" + "    " + "\n" +
                "     " + "      11. Paule, Susan Balbuena" + "    " + "\n" +
                "     " + "      12. Samoza, Celine Montecarlo" + "    " + "\n" +
                "     " + "      13. Sanggalang, Marry Ann Santos" + "    " + "\n" +
                "     " + "      14. Singca, Karen Kipte" + "    " + "\n" +
                "     " + "      15. Sison, Gene Layug" + "    " + "\n" +
                "   " + "\n" +
                "     " + "      Senior Citizen:        60+                      4" + "    " + "\n" +
                "     " + "      List of names:" + "    " + "\n" +
                "     " + "      1. Jardino, Robert Samson" + "    " + "\n" +
                "     " + "      2. Penaloza, Maria Salazar" + "    " + "\n" +
                "     " + "      3. Sanchez, Maycie Dela Cruz" + "    " + "\n" +
                "     " + "      4. Singca, Rolando Toreno" + "    " + "\n" +
                "   " + "\n" +
                "  " + "Percentage of Senior citizen:   18%" + "    " + "\n";

        int x = 10, y = 25;

        for (String line : myString.split("\n")) {
            myPage.getCanvas().drawText(line, x, y, myPaint);
            y += myPaint.descent() - myPaint.ascent();
        }

        myPdfDocument.finishPage(myPage);

        try {
            String myFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File myFile = new File(myFilePath, "Age-Group Report.pdf");

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