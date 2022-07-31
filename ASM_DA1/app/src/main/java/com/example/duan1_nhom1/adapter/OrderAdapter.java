package com.example.duan1_nhom1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1_nhom1.R;
import com.example.duan1_nhom1.dao.FoodDAO;
import com.example.duan1_nhom1.modul.Food;
import com.example.duan1_nhom1.modul.Order;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private ArrayList<Order> list;
    private Context context;

    public void getData(ArrayList<Order> list, Context context){
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = list.get(position);
        FoodDAO foodDAO = new FoodDAO(context);
        Food food = foodDAO.getFoodTheoMaMA(order.getMaMA());
        //Set data
        holder.tvName.setText(food.getTenMA());
        holder.tvDescribe.setText(food.getMoTa());
        holder.tvDate.setText("Ngay dat hang: " + order.getNgayMua());
        holder.tvStt.setText(order.getTrangThai());
        if(holder.tvStt.getText().equals("Don hang da duoc giao")){
            holder.tvStt.setTextColor(Color.parseColor("#64DD17"));
        }
        holder.tvPrice.setText("Tong tien: " + order.getTongTien());
        Glide.with(context).load(food.getHinhAnh()).into(holder.imv);
    }

    @Override
    public int getItemCount() {
        if(list.size()>0){
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvDescribe, tvDate, tvStt, tvPrice;
        private ImageView imv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imv = itemView.findViewById(R.id.imvOrderDetailItem);
            tvName = itemView.findViewById(R.id.tvNameFoodOrderDetailItem);
            tvDescribe = itemView.findViewById(R.id.tvDescribeFoodOrderDetailItem);
            tvDate = itemView.findViewById(R.id.tvDateOrderDetailItem);
            tvStt = itemView.findViewById(R.id.tvSttOrderDetailItem);
            tvPrice = itemView.findViewById(R.id.tvPriceFoodOrderDetailItem);
        }
    }
}
