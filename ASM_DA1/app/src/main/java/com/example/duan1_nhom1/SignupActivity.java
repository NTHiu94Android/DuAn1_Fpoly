package com.example.duan1_nhom1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.duan1_nhom1.dao.CustomerDAO;
import com.example.duan1_nhom1.dao.RestaurantOwnerDAO;
import com.example.duan1_nhom1.modul.Customer;
import com.example.duan1_nhom1.modul.RestaurantOwners;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private TextInputEditText edtUsernameNewSignup, edtPasswordNewSignup, edtRePasswordNewSignup, edtNameNewSignup;
    private Button btnSignup, btnCancelSignup;
    private Spinner spinner;
    private CustomerDAO customerDAO;
    private RestaurantOwnerDAO restaurantOwnerDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        anhXa();

        customerDAO = new CustomerDAO(getApplicationContext());
        restaurantOwnerDAO = new RestaurantOwnerDAO(getApplicationContext());

        addSpinner();
        listenner();
    }

    //Anh xa
    private void anhXa(){
        edtUsernameNewSignup = findViewById(R.id.edtUsernameNewSignup);
        edtPasswordNewSignup = findViewById(R.id.edtPasswordNewSignup);
        edtRePasswordNewSignup = findViewById(R.id.edtRePasswordNewSignup);
        edtNameNewSignup = findViewById(R.id.edtNameNewSignup);
        btnSignup = findViewById(R.id.btnSignup);
        btnCancelSignup = findViewById(R.id.btnCancelSignup);
        spinner = findViewById(R.id.spnVaiTro);
    }

    //Listener
    private void listenner(){
        btnCancelSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtUsernameNewSignup.setText("");
                edtPasswordNewSignup.setText("");
                edtRePasswordNewSignup.setText("");
                edtNameNewSignup.setText("");
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSignup();
            }
        });
    }
    //Kiem tra dang ky tai khoan
    private void checkSignup() {
        //Lay du lieu nhap vao
        String user = edtUsernameNewSignup.getText().toString().trim();
        String name = edtNameNewSignup.getText().toString().trim();
        String pass = edtPasswordNewSignup.getText().toString().trim();
        String repass = edtRePasswordNewSignup.getText().toString();

        //Lay vai tro trong spinner
        HashMap<String, String> hashMap = (HashMap<String, String>) spinner.getSelectedItem();
        String vaitro = hashMap.get("vaitro");

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

        //Kiem loi bo trong
        if(user.equals("")||pass.equals("")||repass.equals("")||name.equals(""))
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        else{
            if(pass.equals(repass)){//Neu repass dung
                //Kiem tra tai khoan da ton tai
                Boolean checkuserCustomer = customerDAO.checkusername(user);
                Boolean checkuserRestaurantOwner = restaurantOwnerDAO.checkusername(user);
                if(checkuserCustomer || checkuserRestaurantOwner){
                    Toast.makeText(getApplicationContext(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Dang ky theo vai tro
                if(vaitro.equals("Customer")){//Neu vai tro la Customer
                    if(!checkuserCustomer){
                        Customer customer = new Customer(user, name, null, vaitro, pass);
                        myRef.child("list_customer").child(user).setValue(customer);
                        customerDAO.insertCustomer(customer);
                        Toast.makeText(getApplicationContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finishAffinity();
                    } else{
                        Toast.makeText(getApplicationContext(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }else{//Neu vai tro la Restaurant Owners
                    if(!checkuserRestaurantOwner){
                        RestaurantOwners restaurantOwners = new RestaurantOwners(user, name, vaitro, pass);
                        myRef.child("list_restaurant_owner").child(user).setValue(restaurantOwners);
                        restaurantOwnerDAO.insertRestaurantOwner(restaurantOwners);
                        Toast.makeText(getApplicationContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finishAffinity();
                    } else{
                        Toast.makeText(getApplicationContext(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{//Neu repass sai
                Toast.makeText(getApplicationContext(), "Repass không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //Them du lieu vao spinner
    private void addSpinner() {
        ArrayList<String> listSp = new ArrayList<>();
        listSp.add("Restaurant Owners");
        listSp.add("Customer");
        ArrayList<HashMap<String, String>> listHM = new ArrayList<>();
        for (int i = 0; i < listSp.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("vaitro", listSp.get(i));
            listHM.add(hashMap);
        }
        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), listHM,
                R.layout.item_spn_vaitro, new String[]{"vaitro"}, new int[]{R.id.tvSpnVaitro});
        spinner.setAdapter(adapter);
    }
}