package com.example.bglresidentprofiling;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarChartActivity extends AppCompatActivity {

    BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> labelsNames;

    ArrayList<BarDataChart> barDataCharts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        BarChart barChart = findViewById(R.id.barChart);
        barEntryArrayList = new ArrayList<>();
        labelsNames = new ArrayList<>();
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


    }

    public void fillBarChart(){
        barDataCharts.clear();
        barDataCharts.add(new BarDataChart("Purok 1 GL", 3));
        barDataCharts.add(new BarDataChart("Purok 2 GL", 1));
        barDataCharts.add(new BarDataChart("Purok 3 GL", 2));
        barDataCharts.add(new BarDataChart("Purok 4 GL", 0));
        barDataCharts.add(new BarDataChart("Purok 5 GL", 2));
        barDataCharts.add(new BarDataChart("Purok 6 GL", 1));
        barDataCharts.add(new BarDataChart("Purok 7 GL", 1));
        barDataCharts.add(new BarDataChart("Purok 8 GL", 1));


    }
}