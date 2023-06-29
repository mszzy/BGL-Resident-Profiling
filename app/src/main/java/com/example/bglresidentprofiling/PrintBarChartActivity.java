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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class PrintBarChartActivity extends AppCompatActivity {

    BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> labelsNames;
    Button printPC;

    ArrayList<BarDataChart> barDataCharts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_bar_chart);
        barChart = findViewById(R.id.barChart);
        barEntryArrayList = new ArrayList<>();
        labelsNames = new ArrayList<>();
        printPC = findViewById(R.id.printPC);
        fillBarChart();


        for (int i = 0; i< barDataCharts.size(); i++){
            String address = barDataCharts.get(i).getAddress();
            int sales = barDataCharts.get(i).getSales();
            barEntryArrayList.add(new BarEntry(i,sales));
            labelsNames.add(address);
        }

        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Population Count");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description = new Description();
        description.setText("Address");
        barChart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));

        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelsNames.size());
        xAxis.setLabelRotationAngle(270);

        barChart.getDescription().setText("Resident's Profiles");
        barChart.animateY(2000);
        barChart.invalidate();

        ActivityCompat.requestPermissions(PrintBarChartActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        printPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPDF();
            }
        });
    }


    public void createPDF() {
        PdfDocument myPdfDocument = new PdfDocument();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(450, 800, 3).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);


        Paint myPaint = new Paint();

        String myString = "   " + "\n" +
                "------------------------------------------------------- "+"Statistical Data" + " -------------------------------------------------------" + "\n" +
                "   " + "\n" +
                "   "+"Address: Brgy. General Lim  |  Population Count" + "   " + "\n" +
                "     " +"             Purok 1:                                         3" + "    "+ "\n"+
                "     " +"                             List of names:" + "    "+ "\n"+
                "     " +"                                1. Pangan, Micaella Abanilla" + "    "+ "\n"+
                "     " +"                                2. Santos, Andrew Rivera" + "    "+ "\n"+
                "     " +"                                3. Singca, Rolando Toreno" + "    "+ "\n"+
                "   " + "\n" +
                "     " +"             Purok 2:                                         1" + "    "+ "\n"+
                "     " +"                             List of names:" + "    "+ "\n"+
                "     " +"                                1. Frenilla, Nina Basa" + "    "+ "\n"+
                "   " + "\n" +
                "     " +"             Purok 3:                                         3" + "    "+ "\n"+
                "     " +"                             List of names:" + "    "+ "\n"+
                "     " +"                                1. Malit, Jenny Bartolome" + "    "+ "\n"+
                "     " +"                                2. Naquil, Kristine Den Hoson" + "    "+ "\n"+
                "     " +"                                3. Paule, Susan Balbuena" + "    "+ "\n"+
                "   " + "\n" +
                "     " +"             Purok 4:                                         7" + "    "+ "\n"+
                "     " +"                             List of names:" + "    "+ "\n"+
                "     " +"                                1. Alonzo, Angeline Crisanto"+ "\n"+
                "     " +"                                2. Basa, Camille Delgado"  + "    "+ "\n"+
                "     " +"                                3. De Jesus, Carlo John Bartolo"  + "\n"+
                "     " +"                                4. Hernandez, Gabriel Hanz Macabre"+ "\n"+
                "     " +"                                5. Samoza, Celine Montecarlo"  + "    "+ "\n"+
                "     " +"                                6. Singca, Karen Kipte"  + "\n"+
                "     " +"                                7. Sison, Gene Layug"  + "\n"+
                "   " + "\n" +
                "     " +"             Purok 5:                                         3" + "    "+ "\n"+
                "     " +"                             List of names:" + "    "+ "\n"+
                "     " +"                                1. Jardino, Robert Samson"+ "\n"+
                "     " +"                                2. Penaloza, Maria Salazar"  + "    "+ "\n"+
                "     " +"                                3. Sanggalang, Marry Ann Santos"  + "\n"+
                "   " + "\n" +
                "     " +"             Purok 6:                                         2" + "    "+ "\n"+
                "     " +"                             List of names:" + "    "+ "\n"+
                "     " +"                                1. Alonzo, Rhoden Fernando"+ "\n"+
                "     " +"                                2. Cabrera, Mari Ystinelli Axsa"  + "    "+ "\n"+
                "   " + "\n" +
                "     " +"             Purok 7:                                         1" + "    "+ "\n"+
                "     " +"                             List of names:" + "    "+ "\n"+
                "     " +"                                1. Francisco, Arnel De Leon"+ "\n"+
                "   " + "\n" +
                "     " +"             Purok 8:                                         2" + "    "+ "\n"+
                "     " +"                             List of names:" + "    "+ "\n"+
                "     " +"                                1. Aquino, Alliya Cruz"+ "\n"+
                "     " +"                                2. Sanchez, Maycie Dela Cruz"  + "    "+ "\n"+
                "   " + "\n" +
                "   " +"Total Population Count:                            22" + "   "+ "\n";

        int x = 10, y = 25;

        for (String line : myString.split("\n")) {
            myPage.getCanvas().drawText(line, x, y, myPaint);
            y += myPaint.descent() - myPaint.ascent();
        }

        myPdfDocument.finishPage(myPage);

        try {
            String myFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File myFile = new File(myFilePath, "Population Count.pdf");

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


    public void fillBarChart(){
        barDataCharts.clear();
        barDataCharts.add(new BarDataChart("Purok 1 GL", 3));
        barDataCharts.add(new BarDataChart("Purok 2 GL", 1));
        barDataCharts.add(new BarDataChart("Purok 3 GL", 3));
        barDataCharts.add(new BarDataChart("Purok 4 GL", 7));
        barDataCharts.add(new BarDataChart("Purok 5 GL", 3));
        barDataCharts.add(new BarDataChart("Purok 6 GL", 2));
        barDataCharts.add(new BarDataChart("Purok 7 GL", 1));
        barDataCharts.add(new BarDataChart("Purok 8 GL", 2));
    }
}