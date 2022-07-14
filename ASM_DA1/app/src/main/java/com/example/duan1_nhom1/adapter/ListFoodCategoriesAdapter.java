package com.example.duan1_nhom1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1_nhom1.DetailFoodActivity;
import com.example.duan1_nhom1.R;
import com.example.duan1_nhom1.modul.Food;

import java.util.ArrayList;

public class ListFoodCategoriesAdapter extends RecyclerView.Adapter<ListFoodCategoriesAdapter.ViewHolder>{
    private ArrayList<Food> list;
    private Context context;
    public void getData(ArrayList<Food> list, Context context){
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_categories, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = list.get(position);
        //Set data len item view
        holder.tvName.setText(food.getTenMA());
        holder.tvDescribe.setText(food.getMoTa());
        holder.tvPrice.setText("$: "+food.getGia()+" VND");
        Glide.with(context).load(food.getHinhAnh()).into(holder.imv);
        //Bat su kien
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailFoodActivity.class);
                intent.putExtra("food", food);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout;
        private ImageView imv;
        private TextView tvName, tvDescribe, tvPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layoutListFoodTypeItem);
            imv = itemView.findViewById(R.id.imvListFoodTypeItem);
            tvName = itemView.findViewById(R.id.tvListNameFoodTypeItem);
            tvDescribe = itemView.findViewById(R.id.tvListDescribeFoodTypeItem);
            tvPrice = itemView.findViewById(R.id.tvListPriceFoodTypeItem);
        }
    }
}
