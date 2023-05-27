package com.example.bglresidentprofiling;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<DatClass> datList;
    public MyAdapter(Context context, List<DatClass> datList) {
        this.context = context;
        this.datList = datList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(datList.get(position).getDataImage()).into(holder.recImage);
        holder.recTitle.setText(datList.get(position).getFirstname());
        holder.recDesc.setText(datList.get(position).getMiddle());
        holder.recLang.setText(datList.get(position).getLastname());
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Image", datList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Middlename", datList.get(holder.getAdapterPosition()).getMiddle());
                intent.putExtra("Firstname", datList.get(holder.getAdapterPosition()).getFirstname());
                intent.putExtra("Age",datList.get(holder.getAdapterPosition()).getAge());
                intent.putExtra("Sex",datList.get(holder.getAdapterPosition()).getSex());
                intent.putExtra("Birthday",datList.get(holder.getAdapterPosition()).getBirthday());
                intent.putExtra("Marital",datList.get(holder.getAdapterPosition()).getMarital());
                intent.putExtra("Contact No.",datList.get(holder.getAdapterPosition()).getNumber());
                intent.putExtra("Address",datList.get(holder.getAdapterPosition()).getAddress());
                intent.putExtra("Occupation",datList.get(holder.getAdapterPosition()).getOccupation());
                intent.putExtra("Religion",datList.get(holder.getAdapterPosition()).getReligion());
                intent.putExtra("House No.",datList.get(holder.getAdapterPosition()).getHouseNo());
                intent.putExtra("Level of Education",datList.get(holder.getAdapterPosition()).getLevelOfEduc());
                intent.putExtra("Solo Parent",datList.get(holder.getAdapterPosition()).getParentSolo());
                intent.putExtra("Senior Citizen",datList.get(holder.getAdapterPosition()).getSeniorCitiz());
                intent.putExtra("Registered Voter",datList.get(holder.getAdapterPosition()).getRegisVoter());
                intent.putExtra("Key",datList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("Lastname", datList.get(holder.getAdapterPosition()).getLastname());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return datList.size();
    }
    public void searchDatList(ArrayList<DatClass> searchList){
        datList = searchList;
        notifyDataSetChanged();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    ImageView recImage;
    TextView recTitle, recDesc, recLang;
    CardView recCard;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recDesc = itemView.findViewById(R.id.recDesc);
        recLang = itemView.findViewById(R.id.recLang);
        recTitle = itemView.findViewById(R.id.recTitle);
    }
}