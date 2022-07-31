package com.example.duan1_nhom1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom1.CustomerDetailOrderActivity;
import com.example.duan1_nhom1.R;
import com.example.duan1_nhom1.adapter.CartAdapter;
import com.example.duan1_nhom1.dao.CartDAO;
import com.example.duan1_nhom1.dao.OrderDAO;
import com.example.duan1_nhom1.dao.RestaurantDAO;
import com.example.duan1_nhom1.modul.Cart;
import com.example.duan1_nhom1.modul.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class CartFargment extends Fragment {
    private RecyclerView rcv;
    private TextView tvPay;
    private CartDAO cartDAO;
    private CartAdapter adapter;
    private ArrayList<Cart> list;
    private String maKH = "";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("list_order");
    private DatabaseReference myRef_cart = database.getReference("list_cart");

    public static CartFargment newInstance() {
        CartFargment fragment = new CartFargment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        rcv = view.findViewById(R.id.rcvCart);
        tvPay = view.findViewById(R.id.tvPayFM);
        //Lay maKH
        maKH = getActivity().getIntent().getStringExtra("maKH");
        //Khai bao lop DAO, set rcv
        loadData();
        //Bat su kien
        tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrder();
            }
        });
        return view;
    }
    //Ham them Hoa don
    private void addOrder(){
        OrderDAO orderDAO = new OrderDAO(getActivity());
        RestaurantDAO restaurantDAO = new RestaurantDAO(getActivity());
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        String ngayMua = c.get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + c.get(Calendar.YEAR);
        for (int i = 0; i < list.size(); i++) {
            Order order = new Order();
            order.setMaHD(maKH+i+list.get(i).getMaGH());
            order.setNgayMua(ngayMua);
            order.setTongTien(list.get(i).getDonGia());
            order.setTrangThai("Cho xac nhan");
            order.setMaMA(list.get(i).getMaMA());
            order.setMaNH(restaurantDAO.getMaNHTheoMaMA(list.get(i).getMaMA()));
            order.setMaKH(maKH);
            //Dat hang (Them don hang)
            orderDAO.insert(order);
            myRef.child(order.getMaHD()).setValue(order);
            //Xoa gio hang
            cartDAO.delete(list.get(i).getMaGH());
            myRef_cart.child(list.get(i).getMaGH()).removeValue();
            Toast.makeText(getActivity(), "Dat hang thanh cong!", Toast.LENGTH_SHORT).show();
        }
        //Put maKH sang DetailOrderActivity
        Intent intent = new Intent(getActivity(), CustomerDetailOrderActivity.class);
        intent.putExtra("maKH", maKH);
        startActivity(intent);
        getActivity().finish();
    }
    //Load du lieu khi resume fragment
    @Override
    public void onResume() {
        super.onResume();
        maKH = getActivity().getIntent().getStringExtra("maKH");
        loadData();
    }
    //Load du lieu
    private void loadData() {
        cartDAO = new CartDAO(getActivity());
        list = cartDAO.getAllCartTheoMaKH(maKH);

        adapter = new CartAdapter();
        adapter.getData(list, getActivity());

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);

        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);
    }
}