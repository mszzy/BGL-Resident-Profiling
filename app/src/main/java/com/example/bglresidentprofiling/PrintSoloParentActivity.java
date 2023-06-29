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

public class PrintSoloParentActivity extends AppCompatActivity {

    Button printSoloParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_solo_parent);
        printSoloParent = findViewById(R.id.printSoloParent);

        PieChart pieChart = findViewById(R.id.parentChart);

        ArrayList<PieEntry> profiles = new ArrayList<>();
        profiles.add(new PieEntry(3, "Yes"));
        profiles.add(new PieEntry(3, "No"));
        profiles.add(new PieEntry(16, "Not Applicable"));

        PieDataSet pieDataSet = new PieDataSet(profiles, "Solo Parent");
        pieDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Solo Parent Report");
        pieChart.animate();
        ActivityCompat.requestPermissions(PrintSoloParentActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        printSoloParent.setOnClickListener(new View.OnClickListener() {
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
                "   " + "Solo Parent Report of Brgy. General Lim" + "   " + "\n" +
                "     " + "                      Residents          |          Count" + "    " + "\n" +
                "     " + "             Responded 'Yes'                                3" + "    " + "\n" +
                "     " + "      List of names:" + "    " + "\n" +
                "     " + "      1. Pangan, Micaella Abanilla" + "    " + "\n" +
                "     " + "      2. Sanchez, Maycie Dela Cruz" + "    " + "\n" +
                "     " + "      3. Singca, Rolando Toreno" + "    " + "\n" +
                "   " + "\n" +
                "     " + "             Responded 'No'                                  3" + "    " + "\n" +
                "     " + "      List of names:" + "    " + "\n" +
                "     " + "      1. Francisco, Arnel De Leon" + "    " + "\n" +
                "     " + "      2. Malit, Jenny Bartolome" + "    " + "\n" +
                "     " + "      3. Paule, Susan Balbuena" + "    " + "\n" +
                "   " + "\n" +
                "     " + "             Responded 'N/A'                               16" + "    " + "\n" +
                "     " + "      List of names:" + "    " + "\n" +
                "     " + "      1. Alonzo, Angeline Crisanto" + "    " + "\n" +
                "     " + "      2. Alonzo, Rhoden Fernando" + "    " + "\n" +
                "     " + "      3. Aquino, Alliya Cruz" + "    " + "\n" +
                "     " + "      4. Basa, Camille Delgado" + "    " + "\n" +
                "     " + "      5. Cabrera, Mari Ystinelli Axsa" + "    " + "\n" +
                "     " + "      6. De Jesus, Carlo John Bartolo" + "    " + "\n" +
                "     " + "      7. Frenilla, Nina Basa" + "    " + "\n" +
                "     " + "      8. Hernandez, Gabriel Hanz Macabre" + "    " + "\n" +
                "     " + "      9. Jardino, Robert Samson" + "    " + "\n" +
                "     " + "      10. Naquil, Kristine Den Hoson" + "    " + "\n" +
                "     " + "      11. Penaloza, Maria Salazar" + "    " + "\n" +
                "     " + "      12. Samoza, Celine Montecarlo" + "    " + "\n" +
                "     " + "      13. Sanggalang, Marry Ann Santos" + "    " + "\n" +
                "     " + "      14. Santos, Andrew Rivera" + "    " + "\n" +
                "     " + "      15. Singca, Karen Kipte" + "    " + "\n" +
                "     " + "      16. Sison, Gene Layug" + "    " + "\n" +
                "   " + "\n" +
                "  " + "Percentage of Solo Parent:   13%" + "    " + "\n";

        int x = 10, y = 25;

        for (String line : myString.split("\n")) {
            myPage.getCanvas().drawText(line, x, y, myPaint);
            y += myPaint.descent() - myPaint.ascent();
        }

        myPdfDocument.finishPage(myPage);

        try {
            String myFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File myFile = new File(myFilePath, "Solo Parent Report.pdf");

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
