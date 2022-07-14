package com.example.duan1_nhom1.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.duan1_nhom1.DetailFoodRestaurantActivity;
import com.example.duan1_nhom1.FoodManagerActivity;
import com.example.duan1_nhom1.R;
import com.example.duan1_nhom1.UpdateFoodActivity;
import com.example.duan1_nhom1.dao.FoodDAO;
import com.example.duan1_nhom1.modul.Food;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{
    private ArrayList<Food> list;
    private Context context;
    private FoodDAO foodDAO;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("list_food");

    public void getData(ArrayList<Food> list, Context context){
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        foodDAO = new FoodDAO(context);
        Food food = list.get(position);
        //Gan du lieu len item
        holder.tvName.setText(food.getTenMA());
        holder.tvDescribe.setText(food.getMoTa());
        holder.tvPrice.setText("Gia: "+ food.getGia() + "VND");
        Glide.with(this.context).load(food.getHinhAnh()).into(holder.imvFood);
        //Bat su kien
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(food.getMaMA()));
        viewBinderHelper.setOpenOnlyOne(true);

        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailFoodRestaurantActivity.class);
                intent.putExtra("food", food);
                context.startActivity(intent);
            }
        });

        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FoodManagerActivity)context).finish();
                Intent i = new Intent(context, UpdateFoodActivity.class);
                i.putExtra("food", food);
                context.startActivity(i);
                viewBinderHelper.closeLayout(String.valueOf(food.getMaMA()));
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDeleteFood(food);
                viewBinderHelper.closeLayout(String.valueOf(food.getMaMA()));

            }
        });
    }


    private void dialogDeleteFood(Food food) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning!");
        builder.setMessage("Do you want to delete food?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                foodDAO.delete(food.getMaMA());
                myRef.child(food.getMaMA()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(context, "Delete "+food.getTenMA()+" success", Toast.LENGTH_SHORT).show();
                    }
                });
                if(list.size()>0){
                    list.clear();
                }
                list = foodDAO.getFoodTheoMaNH(food.getMaNH());
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
    @Override
    public int getItemCount() {
        if(list.size()>0){
            return list.size();
        }
        return 0;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvDescribe, tvPrice, tvEdit, tvDelete;
        private ImageView imvFood;
        private SwipeRevealLayout swipeRevealLayout;
        private LinearLayout layout_item;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNameFoodItem);
            tvDescribe = itemView.findViewById(R.id.tvDescribeFoodItem);
            tvPrice = itemView.findViewById(R.id.tvPriceFoodItem);
            tvEdit = itemView.findViewById(R.id.tvEditFoodItem);
            tvDelete = itemView.findViewById(R.id.tvDeleteFoodItem);
            imvFood = itemView.findViewById(R.id.imvFoodItem);
            swipeRevealLayout = itemView.findViewById(R.id.swipe_layout);
            layout_item = itemView.findViewById(R.id.layout_itemFood);
        }
    }
}
