package com.example.duan1_nhom1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom1.ListCategoriesActivity;
import com.example.duan1_nhom1.R;
import com.example.duan1_nhom1.modul.TypeOfFood;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>{
    private ArrayList<TypeOfFood> list;
    private Context context;

    public void getData(ArrayList<TypeOfFood> list, Context context){
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        TypeOfFood typeOfFood = list.get(position);

        holder.imv.setImageResource(typeOfFood.getHinhAnh());
        holder.tv.setText(typeOfFood.getTenLoaiMA());
        holder.layoutview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListCategoriesActivity.class);
                intent.putExtra("FoodType", typeOfFood);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list.size()>0){
            return list.size();
        }
        return 0;
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder{
        private ImageView imv;
        private TextView tv;
        private CardView layoutview;
        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            imv = itemView.findViewById(R.id.imvItemCategories);
            tv = itemView.findViewById(R.id.tvItemCategories);
            layoutview = itemView.findViewById(R.id.categoriesLayout);
        }
    }
}
