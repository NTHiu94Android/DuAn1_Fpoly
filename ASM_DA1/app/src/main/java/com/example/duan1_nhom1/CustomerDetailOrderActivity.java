package com.example.duan1_nhom1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.duan1_nhom1.adapter.OrderAdapter;
import com.example.duan1_nhom1.dao.OrderDAO;
import com.example.duan1_nhom1.modul.Order;

import java.util.ArrayList;

public class CustomerDetailOrderActivity extends AppCompatActivity {
    private OrderDAO orderDAO;
    private OrderAdapter adapter;
    private ArrayList<Order> list;
    private String maKH = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        RecyclerView rcv = findViewById(R.id.rcvOrderDetail);
        //Lay thong tin maKH
        Intent intent = getIntent();
        maKH = intent.getStringExtra("maKH");
        orderDAO = new OrderDAO(CustomerDetailOrderActivity.this);
        list = orderDAO.getOrderTheoMaKH(maKH);
        adapter = new OrderAdapter();
        adapter.getData(list, CustomerDetailOrderActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);

        rcv.setAdapter(adapter);
        rcv.setLayoutManager(manager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, CustomerActivity.class);
        intent.putExtra("maKH", maKH);
        startActivity(intent);
        finish();
    }
}