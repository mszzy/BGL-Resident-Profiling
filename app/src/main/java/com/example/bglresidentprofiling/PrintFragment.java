package com.example.bglresidentprofiling;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PrintFragment extends Fragment {

    TextView btn_pc, btn_ag, btn_rv, btn_rl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_print, container, false);
        btn_pc = rootView.findViewById(R.id.btn_pc);
        btn_ag = rootView.findViewById(R.id.btn_ag);
        btn_rv = rootView.findViewById(R.id.btn_rv);
        btn_rl = rootView.findViewById(R.id.btn_rl);

        btn_pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), PrintBarChartActivity.class);
                startActivity(intent);
            }
        });
        //return rootView;

        btn_ag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), PrintPieChartActivity.class);
                startActivity(intent);
            }
        });

        btn_rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), PrintRadarChartActivity.class);
                startActivity(intent);
            }
        });

        btn_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), PrintGeneratedProfileActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}