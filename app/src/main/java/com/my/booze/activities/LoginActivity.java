package com.my.booze.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.my.booze.R;
import com.my.booze.models.SigninResponseModel;
import com.my.booze.models.UserDataModel;
import com.my.booze.models.UserResponseModel;
import com.my.booze.network.GetDataService;
import com.my.booze.network.RetrofitClientInstance;
import com.my.booze.utilities.Utilities;
import com.xgc1986.ripplebutton.widget.RippleButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private RelativeLayout email_Layout, password_Layout;
    private EditText email_EditText, password_EditText;
    String email, password;
    private RippleButton btn_signin;
    private TextView btn_signup;
    GetDataService service;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_Layout = findViewById(R.id.email_Layout);
        password_Layout = findViewById(R.id.password_Layout);
        email_EditText = findViewById(R.id.email_EditText);
        password_EditText = findViewById(R.id.password_EditText);
        btn_signin = findViewById(R.id.btn_signin);
        btn_signup = findViewById(R.id.btn_signup);
        email_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_EditText.requestFocus();
            }
        });
        password_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password_Layout.requestFocus();
            }
        });
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_EditText.getText().toString();
                password = password_EditText.getText().toString();

                if (email.isEmpty()){
                    email_EditText.setError("*Email");
                    email_EditText.requestFocus();
                } else if (password.isEmpty()){
                    password_EditText.setError("*Password");
                    password_EditText.requestFocus();
                } else {
//                    Toast.makeText(LoginActivity.this, "111", Toast.LENGTH_SHORT).show();
                    userLogin(email, password);
                }
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    public void userLogin(String email, String password) {
        progressDialog  = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Logging up");
        progressDialog.show();
        progressDialog.setCancelable(false);
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SigninResponseModel> call = service.SignIn(email.toString(), password.toString());
        call.enqueue(new Callback<SigninResponseModel>() {
            @Override
            public void onResponse(Call<SigninResponseModel> call, Response<SigninResponseModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
                Log.i("checkingResponse", ""+response.body());
                Log.i("checkingResponse", ""+response.body().getMsg());
                Log.i("checkingResponse", ""+response.body().getStatus());
               if (status == 200) {
                   Log.i("checkingResponse", ""+response.body().getData());
                   UserDataModel user = response.body().getData();
                    Utilities.saveString(LoginActivity.this, "login_Status", "yes");
                    Utilities.saveString(LoginActivity.this, "user_Id", ""+user.getUser_Id());
                    Utilities.saveString(LoginActivity.this, "user_Name", ""+user.getUser_Name());
                    Utilities.saveString(LoginActivity.this, "user_Email", ""+user.getUser_Email());
                    Utilities.saveString(LoginActivity.this, "user_Contact", ""+user.getUser_Contact());
                    Utilities.saveString(LoginActivity.this, "user_Password", ""+user.getUserPassword());
                    Utilities.saveString(LoginActivity.this, "user_RecentAddress", ""+user.getUser_RecentAddress());
                    Utilities.saveString(LoginActivity.this, "user_CartItemsCount", ""+response.body().getCart_ItemsCount());
                    Toast.makeText(LoginActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    if(Utilities.getString(LoginActivity.this, "User_RecentAddress").isEmpty()) {
                        finish();
                        Intent intent = new Intent(LoginActivity.this, DeliveryAddressActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
                else if (status == 400){
                   progressDialog.dismiss();
                   Toast.makeText(LoginActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SigninResponseModel> call, Throwable t) {
                progressDialog.dismiss();
//                Utilities.stopAnim();
                Log.i("checkingResponse", t.getMessage());
                Toast.makeText(LoginActivity.this, t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }

}