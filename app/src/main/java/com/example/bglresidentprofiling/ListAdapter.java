package com.example.bglresidentprofiling;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter {

    private Activity mContext;
    List<DatClass> datClasses;

    public ListAdapter(Activity mContext, List<DatClass> datClasses){
        super(mContext,R.layout.list_item,datClasses);
        this.mContext = mContext;
        this.datClasses = datClasses;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_item, null, true);

        TextView tvFirstname = listItemView.findViewById(R.id.tvFirstname);
        TextView tvMiddlename = listItemView.findViewById(R.id.tvMiddlename);
        TextView tvLastname = listItemView.findViewById(R.id.tvLastname);

        DatClass datClass = datClasses.get(position);

        tvFirstname.setText(datClass.getFirstname());
        tvMiddlename.setText(datClass.getMiddle());
        tvLastname.setText(datClass.getLastname());

        return listItemView;
    }
}
