package com.example.bglresidentprofiling;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MapFragment extends Fragment {
    TextView btnBar, btnPie, btnRadar, btnProfile, btnGender, btnSolo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        btnBar = rootView.findViewById(R.id.btn_bar);
        btnPie = rootView.findViewById(R.id.btn_pie);
        btnRadar = rootView.findViewById(R.id.btn_radar);
        btnProfile = rootView.findViewById(R.id.btn_profile);
        btnGender = rootView.findViewById(R.id.btn_gender);
        btnSolo = rootView.findViewById(R.id.btn_solo);


        btnBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), BarChartActivity.class);
                startActivity(intent);
            }
        });
        //return rootView;

        btnPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), PieChartActivity.class);
                startActivity(intent);
            }
        });

        btnRadar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), RadarChartActivity.class);
                startActivity(intent);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), GeneratedProfileActivity.class);
                startActivity(intent);
            }
        });

        btnGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), GenderReportActivity.class);
                startActivity(intent);
            }
        });

        btnSolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), SoloParentActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}