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

public class PrintRadarChartActivity extends AppCompatActivity {

    Button printRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_radar_chart);
        printRV = findViewById(R.id.printRV);

        PieChart pieChart = findViewById(R.id.pieChart);

        ArrayList<PieEntry> profiles = new ArrayList<>();
        profiles.add(new PieEntry(20, "Registered Voter"));
        profiles.add(new PieEntry(2, "Not Registered Voter"));

        PieDataSet pieDataSet = new PieDataSet(profiles, "Registered Voters");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Residents Report");
        pieChart.animate();

        ActivityCompat.requestPermissions(PrintRadarChartActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        printRV.setOnClickListener(new View.OnClickListener() {
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
                "   " + "Registered Voters of Brgy. General Lim" + "   " + "\n" +
                "     " + "                      Residents          |          Count" + "    " + "\n" +
                "     " + "             Responded 'Yes'                       20" + "    " + "\n" +
                "     " + "      List of names:" + "    " + "\n" +
                "     " + "      1. Alonzo, Angeline Crisanto" + "    " + "\n" +
                "     " + "      2. Alonzo, Rhoden Fernando" + "    " + "\n" +
                "     " + "      3. Aquino, Alliya Cruz" + "    " + "\n" +
                "     " + "      4. Basa, Camille Delgado" + "    " + "\n" +
                "     " + "      5. Cabrera, Mari Ystinelli Axsa" + "    " + "\n" +
                "     " + "      6. De Jesus, Carlo John Bartolo" + "    " + "\n" +
                "     " + "      7. Francisco, Arnel De Leon" + "    " + "\n" +
                "     " + "      8. Hernandez, Gabriel Hanz Macabre" + "    " + "\n" +
                "     " + "      9. Jardino, Robert Samson" + "    " + "\n" +
                "     " + "      10. Malit, Jenny Bartolome" + "    " + "\n" +
                "     " + "      11. Naquil, Kristine Den Hoson" + "    " + "\n" +
                "     " + "      12. Pangan, Micaella Abanilla" + "    " + "\n" +
                "     " + "      13. Paule, Susan Balbuena" + "    " + "\n" +
                "     " + "      14. Penaloza, Maria Salazar" + "    " + "\n" +
                "     " + "      15. Samoza, Celine Montecarlo" + "    " + "\n" +
                "     " + "      16. Sanchez, Maycie Dela Cruz" + "    " + "\n" +
                "     " + "      17. Sanggalang, Marry Ann Santos" + "    " + "\n" +
                "     " + "      18. Singca, Karen Kipte" + "    " + "\n" +
                "     " + "      19. Singca, Rolando Toreno" + "    " + "\n" +
                "     " + "      20. Sison, Gene Layug" + "    " + "\n" +
                "   " + "\n" +
                "     " + "             Responded 'No'                         2" + "    " + "\n" +
                "     " + "      1. Frenilla, Nina Basa" + "    " + "\n" +
                "     " + "      2. Santos, Andrew Rivera" + "    " + "\n" +
                "   " + "\n" +
                "  " + "Percentage of Registered Voter:   90%" + "    " + "\n";

        int x = 10, y = 25;

        for (String line : myString.split("\n")) {
            myPage.getCanvas().drawText(line, x, y, myPaint);
            y += myPaint.descent() - myPaint.ascent();
        }

        myPdfDocument.finishPage(myPage);

        try {
            String myFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File myFile = new File(myFilePath, "Registered Voters.pdf");

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