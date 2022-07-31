package com.example.duan1_nhom1.adapter;

import android.content.Context;
import android.content.DialogInterface;
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
import com.example.duan1_nhom1.R;
import com.example.duan1_nhom1.dao.CartDAO;
import com.example.duan1_nhom1.dao.FoodDAO;
import com.example.duan1_nhom1.modul.Cart;
import com.example.duan1_nhom1.modul.Food;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    private ArrayList<Cart> list;
    private Context context;

    public void getData(ArrayList<Cart> list, Context context){
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = list.get(position);
        //Lay mon an theo ma mon an
        FoodDAO foodDAO = new FoodDAO(context);
        Food food = foodDAO.getFoodTheoMaMA(cart.getMaMA());
        //Set view
        holder.tvName.setText(food.getTenMA());
        holder.tvDescribe.setText(food.getMoTa());
        holder.tvPrice.setText(String.valueOf(cart.getDonGia()));
        holder.tvNumber.setText(String.valueOf(cart.getSoLuong()));
        Glide.with(context).load(food.getHinhAnh()).into(holder.imv);
        //Bat su kien
        holder.layoutCartItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                deleteItem(cart);
                return false;
            }
        });
    }
    //Ham xoa mon an trong gio hang
    private void deleteItem(Cart cart) {
        new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        CartDAO cartDAO = new CartDAO(context);
                        cartDAO.delete(cart.getMaGH());
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("list_cart");
                        myRef.child(cart.getMaGH()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT).show();
                            }
                        });
                        loadData(cartDAO, cart.getMaKH());
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
    //Load danh sach
    private void loadData(CartDAO cartDAO, String maKH){
        if(list.size()>0){
            list.clear();
        }
        list = cartDAO.getAllCartTheoMaKH(maKH);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(list.size()>0){
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvPrice, tvDescribe, tvNumber;
        private ImageView imv;
        private LinearLayout layoutCartItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNameFoodCartItem);
            tvPrice = itemView.findViewById(R.id.tvListPriceFoodCartItem);
            tvDescribe = itemView.findViewById(R.id.tvDescribeFoodCartItem);
            tvNumber = itemView.findViewById(R.id.tvNumberFoodCartItem);
            imv = itemView.findViewById(R.id.imvCartItem);
            layoutCartItem = itemView.findViewById(R.id.layoutCartItem);
        }
    }
}
