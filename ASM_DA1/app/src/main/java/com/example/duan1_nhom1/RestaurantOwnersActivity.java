package com.example.duan1_nhom1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duan1_nhom1.adapter.RestaurantAdapter;
import com.example.duan1_nhom1.dao.RestaurantDAO;
import com.example.duan1_nhom1.dao.RestaurantTypeDAO;
import com.example.duan1_nhom1.modul.Restaurant;
import com.example.duan1_nhom1.modul.RestaurantType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantOwnersActivity extends AppCompatActivity {
    private long back;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("list_restaurant");
    private FloatingActionButton fab;
    private GridView gvRestaurantList;
    private RestaurantAdapter adapter;
    private RestaurantDAO restaurantDAO;
    private ArrayList<Restaurant> list;
    private String maCNH;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_owners);
        gvRestaurantList = findViewById(R.id.gvRestaurantList);
        fab = findViewById(R.id.fabAddRestaurant);
        //Lay maCNH tu loginActivity
        Intent intent = getIntent();
        maCNH = intent.getStringExtra("username");
        //Khai bao
        list = new ArrayList<>();
        restaurantDAO = new RestaurantDAO(getApplicationContext());
        //Load danh sach
        getData();
        //Bat su kien
        gvRestaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Restaurant restaurant = list.get(i);
                Intent intent1 = new Intent(RestaurantOwnersActivity.this, RestaurantDetailResActivity.class);
                intent1.putExtra("restaurant", restaurant);
                Toast.makeText(getApplicationContext(), ""+restaurant.getMaNH(), Toast.LENGTH_SHORT).show();
                startActivity(intent1);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRestaurant();
            }
        });

    }
    //Load danh sach
    public void getData(){
        if(list.size()>0){
            list.clear();
        }
        list = restaurantDAO.getRestaurantTheoMaCNH(maCNH);
        adapter = new RestaurantAdapter(list, this);
        gvRestaurantList.setAdapter(adapter);
    }
    //Them nha hang
    private void addRestaurant() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_add_restaurant, null);

        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        //Anh xa
        Button btnClear = v.findViewById(R.id.btnCancelRestaurantDialog);
        Button btnAdd = v.findViewById(R.id.btnAddRestaurantDialog);
        EditText edtName = v.findViewById(R.id.edtNameRestaurantDialog);
        EditText edtAddress = v.findViewById(R.id.edtAddressRestaurantDialog);
        EditText edtDescribe = v.findViewById(R.id.edtDescribeRestaurantDialog);
        spinner = v.findViewById(R.id.spnRestaurantTypeDialog);
        //Them ten loai nha hang vao spn
        addSpinner();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Kiem loi
                if(edtName.getText().toString().isEmpty() || edtAddress.getText().toString().isEmpty() || edtDescribe.getText().toString().isEmpty()){
                    Toast.makeText(RestaurantOwnersActivity.this, "Please enter enough information", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Gan du lieu
                String maNH = maCNH+ edtName.getText().toString();
                String tenNH = edtName.getText().toString();
                String diaChi = edtAddress.getText().toString();
                String moTa = edtDescribe.getText().toString();
                int hinhAnh = R.drawable.ic_restaurant256;
                //Lay ma loai nha hang trong spinner
                HashMap<String, String> hashMap = (HashMap<String, String>) spinner.getSelectedItem();
                String maLoaiNH = hashMap.get("IdRestaurantType");
                //Add database
                Restaurant restaurant = new Restaurant(maNH, tenNH, diaChi, moTa, hinhAnh, maCNH, Integer.parseInt(maLoaiNH));
                myRef.child(maNH).setValue(restaurant);
                restaurantDAO.insert(restaurant);
                Toast.makeText(RestaurantOwnersActivity.this, "Add restaurant succesfully!", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                getData();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
    //Them ten loai nha hang vao spn
    private void addSpinner() {
        RestaurantTypeDAO restaurantTypeDAO = new RestaurantTypeDAO(this);
        ArrayList<RestaurantType> list = restaurantTypeDAO.getAllRestaurantType();
        ArrayList<HashMap<String, String>> listHM = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("NameRestaurantType", list.get(i).getTenLoaiNH());
            hashMap.put("IdRestaurantType", String.valueOf(list.get(i).getMaLoaiNH()));
            listHM.add(hashMap);
        }
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), listHM,
                R.layout.item_spn_type_restaurant, new String[]{"NameRestaurantType"}, new int[]{R.id.tvSpnRestaurantType});
        spinner.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        if(back + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(RestaurantOwnersActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        back = System.currentTimeMillis();
    }
}