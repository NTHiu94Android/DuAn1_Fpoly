package com.example.duan1_nhom1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1_nhom1.R;
import com.example.duan1_nhom1.dao.FoodDAO;
import com.example.duan1_nhom1.dao.OrderDAO;
import com.example.duan1_nhom1.modul.Food;
import com.example.duan1_nhom1.modul.Order;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderResAdapter extends RecyclerView.Adapter<OrderResAdapter.ViewHolder>{
    private ArrayList<Order> list;
    private Context context;

    public void getData(ArrayList<Order> list, Context context){
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OrderResAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail_res, parent, false);
        return new OrderResAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderResAdapter.ViewHolder holder, int position) {
        Order order = list.get(position);
        OrderDAO orderDAO = new OrderDAO(context);
        FoodDAO foodDAO = new FoodDAO(context);
        Food food = foodDAO.getFoodTheoMaMA(order.getMaMA());
        //Set data
        holder.tvName.setText(food.getTenMA());
        holder.tvDescribe.setText(food.getMoTa());
        holder.tvDate.setText("Ngay dat hang: " + order.getNgayMua());
        holder.tvStt.setText(order.getTrangThai());
        holder.tvPrice.setText("Tong tien: " + order.getTongTien());
        Glide.with(context).load(food.getHinhAnh()).into(holder.imv);
        //Neu don hang da giao thi enabled button
        if(holder.tvStt.getText().equals("Don hang da duoc giao")){
            holder.btn.setEnabled(false);
            holder.tvStt.setTextColor(Color.parseColor("#64DD17"));
        }
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order.setTrangThai("Don hang da duoc giao");
                holder.tvStt.setText(order.getTrangThai());
                holder.tvStt.setTextColor(Color.parseColor("#64DD17"));
                holder.btn.setEnabled(false);
                //Cap nhat hoa don tren database
                orderDAO.update(order);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("list_order");
                myRef.child(order.getMaHD()).setValue(order, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(context, "Don hang da duoc xac nhan", Toast.LENGTH_SHORT).show();
                    }
                });
                //Load du lieu
                if(list.size()>0){
                    list.clear();
                }
                list = orderDAO.getOrderTheoMaNH(order.getMaNH(), "Cho xac nhan");
                notifyDataSetChanged();
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvDescribe, tvDate, tvStt, tvPrice;
        private ImageView imv;
        private Button btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imv = itemView.findViewById(R.id.imvOrderDetailResItem);
            tvName = itemView.findViewById(R.id.tvNameFoodOrderDetailResItem);
            tvDescribe = itemView.findViewById(R.id.tvDescribeFoodOrderDetailResItem);
            tvDate = itemView.findViewById(R.id.tvDateOrderDetailResItem);
            tvStt = itemView.findViewById(R.id.tvSttOrderDetailResItem);
            tvPrice = itemView.findViewById(R.id.tvPriceFoodOrderDetailResItem);
            btn = itemView.findViewById(R.id.btnXacNhanDonHang);
        }
    }
}
