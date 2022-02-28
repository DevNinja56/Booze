package com.my.booze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.my.booze.R;
import com.my.booze.models.OrderResponseModel;
import com.my.booze.models.UserResponseModel;
import com.my.booze.network.GetDataService;
import com.my.booze.network.RetrofitClientInstance;
import com.my.booze.utilities.Utilities;
import com.xgc1986.ripplebutton.widget.RippleButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    RelativeLayout name_Layout, email_Layout, contact_Layout, password_Layout;
    private EditText name_EditText, email_EditText, contact_EditText, password_EditText;
    private String name, email, contact, password;
    private RippleButton btn_signup;
    private TextView btn_signin;
    ProgressDialog progressDialog;
    GetDataService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name_Layout = findViewById(R.id.name_Layout);
        email_Layout = findViewById(R.id.email_Layout);
        contact_Layout = findViewById(R.id.contact_Layout);
        password_Layout = findViewById(R.id.password_Layout);
        name_EditText = findViewById(R.id.name_EditText);
        email_EditText = findViewById(R.id.email_EditText);
        contact_EditText = findViewById(R.id.contact_EditText);
        password_EditText = findViewById(R.id.password_EditText);
        btn_signin = findViewById(R.id.btn_signin);
        btn_signup = findViewById(R.id.btn_signup);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = name_EditText.getText().toString();
                email = email_EditText.getText().toString();
                contact = contact_EditText.getText().toString();
                password = password_EditText.getText().toString();

                if (name.isEmpty()){
                    name_EditText.setError("*Name");
                    name_EditText.requestFocus();
                    return;
                } else if (email.isEmpty()){
                    email_EditText.setError("*Email");
                    email_EditText.requestFocus();
                    return;
                } else if (contact.isEmpty()){
                    contact_EditText.setError("*Contact");
                    contact_EditText.requestFocus();
                    return;
                } else if (password.isEmpty()){
                    password_EditText.setError("*Password");
                    password_EditText.requestFocus();
                    return;
                }  else if(password.length() <= 6) {
                    Snackbar snackbar = Snackbar.make(v, "Password length should be greater than 6", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(ContextCompat.getColor(SignupActivity.this, R.color.red));
                    snackbar.show();
                    return;
                }
                else {
                    userSignup(v, name, email, contact, password);
                }
            }
        });
        name_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_EditText.requestFocus();
            }
        });
        email_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_EditText.requestFocus();
            }
        });
        contact_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact_EditText.requestFocus();
            }
        });
        password_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password_EditText.requestFocus();
            }
        });
    }

    private void userSignup(View view, String name, String email, String contact, String password){
        progressDialog  = new ProgressDialog(SignupActivity.this);
        progressDialog.setTitle("Signing up");
        progressDialog.show();
        progressDialog.setCancelable(false);
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<UserResponseModel> call = service.SignUp(name, email, contact, password);
        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
//                Log.i("checking_Data11", ""+response.body().getMsg());
//                Log.i("checking_Data11", ""+response.body().getStatus());
//                Log.i("checking_Data11", ""+status);
                if (status == 200){
                    progressDialog.dismiss();
                    Toast.makeText(SignupActivity.this, "Your account is successfully created!", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(SignupActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(SignupActivity.this, VerificationCodeActivity.class);
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    intent.putExtra("CODE_FOR", "signup");
                    intent.putExtra("CODE_FOR", email);
                    startActivity(intent);
                }
                else if (status == 400){
                    progressDialog.dismiss();
                    Toast.makeText(SignupActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
//                Utilities.stopAnim();
                Log.i("checkingResponse", t.getMessage());
                Toast.makeText(SignupActivity.this, t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }
}