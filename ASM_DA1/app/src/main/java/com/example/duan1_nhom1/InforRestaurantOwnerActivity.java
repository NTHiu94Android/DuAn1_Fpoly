package com.example.duan1_nhom1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_nhom1.dao.RestaurantDAO;
import com.example.duan1_nhom1.dao.RestaurantTypeDAO;
import com.example.duan1_nhom1.modul.Restaurant;
import com.example.duan1_nhom1.modul.RestaurantType;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class InforRestaurantOwnerActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("list_restaurant");
    private TextView tvUpdate, tvDelete, tvLogout;
    private RestaurantDAO restaurantDAO;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_restaurant_owner);
        tvUpdate = findViewById(R.id.tvUpdateInforRestaurant);
        tvDelete = findViewById(R.id.tvDeleteInforRestaurant);
        tvLogout = findViewById(R.id.tvLogoutInforRestaurant);

        restaurantDAO = new RestaurantDAO(getApplicationContext());

        //Lay restaurant tu DetailRestaurantActivity
        Intent intent = getIntent();
        Restaurant restaurant = (Restaurant) intent.getSerializableExtra("restaurant");

        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateRestaurant(restaurant);
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Delete", restaurant.getMaNH());
                restaurantDAO.delete(restaurant.getMaNH());
                myRef.child(restaurant.getMaNH()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getApplicationContext(), "Delete restaurant"+restaurant.getTenNH()+" success", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent i1 = new Intent(getApplicationContext(), RestaurantOwnersActivity.class);
                i1.putExtra("username", restaurant.getMaCNH());
                startActivity(i1);
                finishAffinity();
            }
        });
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finishAffinity();
            }
        });
    }

    private void UpdateRestaurant(Restaurant restaurant1) {
        //Show Alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_update_restaurant, null);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        //Anh xa
        Button btnClear = v.findViewById(R.id.btnCancelUpdateRestaurantDialog);
        Button btnUpdate = v.findViewById(R.id.btnUpdateRestaurantDialog);
        EditText edtName = v.findViewById(R.id.edtNameNewRestaurantDialog);
        EditText edtAddress = v.findViewById(R.id.edtAddressNewRestaurantDialog);
        EditText edtDescribe = v.findViewById(R.id.edtDescribeRestaurantNewDialog);
        spinner = v.findViewById(R.id.spnRestaurantTypeNewDialog);

        String tenNH = restaurant1.getTenNH();
        edtName.setText(tenNH);
        edtName.setEnabled(false);
        //Them ten loai nha hang vao spn
        addSpinner();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Kiem loi nhap du lieu
                if(edtName.getText().toString().isEmpty() || edtAddress.getText().toString().isEmpty() || edtDescribe.getText().toString().isEmpty()){
                    Toast.makeText(InforRestaurantOwnerActivity.this, "Please enter enough information", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Lay thong tin tu editext

                String diaChi = edtAddress.getText().toString();
                String moTa = edtDescribe.getText().toString();
                int hinhAnh = R.drawable.ic_restaurant256;
                //Lay ma loai nha hang trong spinner
                HashMap<String, String> hashMap = (HashMap<String, String>) spinner.getSelectedItem();
                String maLoaiNH = hashMap.get("IdRestaurantType");
                //Update restaurant
                Restaurant restaurant = new Restaurant(restaurant1.getMaNH(), tenNH, diaChi, moTa, hinhAnh, restaurant1.getMaCNH(), Integer.parseInt(maLoaiNH));
                myRef.child(restaurant1.getMaNH()).setValue(restaurant);
                restaurantDAO.update(restaurant);
                Toast.makeText(getApplicationContext(), "Update restaurant"+restaurant.getTenNH()+" success", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
                //Tra ma chu nha hang ve RestaurantOwnerActivity
                Intent i1 = new Intent(getApplicationContext(), RestaurantOwnersActivity.class);
                i1.putExtra("username", restaurant.getMaCNH());
                startActivity(i1);
                finishAffinity();
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
}