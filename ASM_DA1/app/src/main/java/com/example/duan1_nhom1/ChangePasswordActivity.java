package com.example.duan1_nhom1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.duan1_nhom1.dao.CustomerDAO;
import com.example.duan1_nhom1.dao.RestaurantOwnerDAO;
import com.example.duan1_nhom1.modul.Customer;
import com.example.duan1_nhom1.modul.RestaurantOwners;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePasswordActivity extends AppCompatActivity {
    private TextInputEditText edtOldPassChange, edtNewPassChange, edtReNewPassChange;
    private RestaurantOwnerDAO restaurantOwnerDAO;
    private CustomerDAO customerDAO;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        edtOldPassChange = findViewById(R.id.edtOldPassChange);
        edtNewPassChange = findViewById(R.id.edtNewPassChange);
        edtReNewPassChange = findViewById(R.id.edtReNewPassChange);
        Button btnSubmitChangPW = findViewById(R.id.btnSubmitChangPW);
        Button btnCancelChangePW = findViewById(R.id.btnCancelChangePW);

        restaurantOwnerDAO = new RestaurantOwnerDAO(getApplicationContext());
        customerDAO = new CustomerDAO(getApplicationContext());

        btnCancelChangePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtOldPassChange.setText("");
                edtNewPassChange.setText("");
                edtReNewPassChange.setText("");
            }
        });
        btnSubmitChangPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        //lay username tu LoginActivity
        Intent intent = getIntent();
        String usnameLogin = intent.getStringExtra("username");

        String old_pass = edtOldPassChange.getText().toString().trim();
        String new_pass = edtNewPassChange.getText().toString().trim();
        String renew_pass = edtReNewPassChange.getText().toString().trim();
        String old_pass_account = "";
        Object object;

        Customer customer = (Customer) intent.getSerializableExtra("object");
        RestaurantOwners restaurantOwners = (RestaurantOwners) intent.getSerializableExtra("object");

        //lay mat khau cu
        if(usnameLogin.equals(customer.getSdt())){
            object = customer;
            old_pass_account = customer.getMatKhau();
        }else {
            object = restaurantOwners;
            old_pass_account = restaurantOwners.getMatKhau();
        }
        //Kiem tra nhap thong tin
        if(old_pass.equals("")||new_pass.equals("")||renew_pass.equals("")){
            Toast.makeText(getApplicationContext(), "Dien day du thong tin", Toast.LENGTH_SHORT).show();
        }else {
            if(!old_pass_account.equals(old_pass)){
                Toast.makeText(getApplicationContext(), "Mat khau cu khong dung", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!new_pass.equals(renew_pass)){
                Toast.makeText(getApplicationContext(), "Nhap lai mat khau khong khop", Toast.LENGTH_SHORT).show();
                return;
            }
            if(new_pass.equals(old_pass_account)){
                Toast.makeText(getApplicationContext(), "Mat khau moi trung voi mat khau cu", Toast.LENGTH_SHORT).show();
                return;
            }
            if(old_pass_account.equals(old_pass) && new_pass.equals(renew_pass)){
                if(object == restaurantOwners){
                    restaurantOwners.setMatKhau(new_pass);
                    myRef.child("list_customer").child(customer.getSdt()).setValue(customer);
                }else {
                    customer.setMatKhau(new_pass);
                    myRef.child("list_restaurant_owner").child(customer.getSdt()).setValue(customer);
                }
                Toast.makeText(getApplicationContext(), "Doi mat khau thanh cong", Toast.LENGTH_SHORT).show();
            }
        }
    }
}