package com.my.booze.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.internal.$Gson$Preconditions;
import com.my.booze.R;
import com.my.booze.models.UserResponseModel;
import com.my.booze.network.GetDataService;
import com.my.booze.network.RetrofitClientInstance;
import com.wang.avi.AVLoadingIndicatorView;
import com.xgc1986.ripplebutton.widget.RippleButton;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationCodeActivity extends AppCompatActivity {

    ImageView backsign;
    TextView verification_For;
    OtpTextView otp_view;
    RippleButton btn_Send, btn_ReSend;
    String verificationFor, otpText = "", user_Email;
    GetDataService service;
    AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        verificationFor = getIntent().getStringExtra("VERIFICATION_CODE");
        user_Email = getIntent().getStringExtra("USER_EMAIL");
        avi = findViewById(R.id.avi);
        backsign = findViewById(R.id.backsign);
        verification_For = findViewById(R.id.verification_For);
        otp_view = findViewById(R.id.otp_view);
        btn_Send = findViewById(R.id.btn_Send);
        btn_ReSend = findViewById(R.id.btn_ReSend);

        backsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(verificationFor.equals("signup")) {
            verification_For.setText("Sign up");
        } else {
            verification_For.setText("Reset Password");
        }

        otp_view.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // fired when user types something in the Otpbox
            }
            @Override
            public void onOTPComplete(String otp) {
                // fired when user has entered the OTP fully.
                otpText = otp;
            }
        });

        btn_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otpText.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(v, "Please enter your verification code!", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(ContextCompat.getColor(VerificationCodeActivity.this, R.color.red));
                    snackbar.show();
                } else {
                    submitVerificationCode(v);
                }
            }
        });

        btn_ReSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationCode(v);
            }
        });
    }

    public void submitVerificationCode(View v) {
        startAnim();
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<UserResponseModel> call = service.matchVerificationCode(user_Email, otpText);
        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
//                Log.i("checking_Data11", ""+response.body().getMsg());
//                Log.i("checking_Data11", ""+response.body().getStatus());
//                Log.i("checking_Data11", ""+status);
                if (status == 200){
                    stopAnim();
//                    progressDialog.dismiss();
                    Toast.makeText(VerificationCodeActivity.this, "Your account is registered successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VerificationCodeActivity.this, LoginActivity.class);
//                    intent.putExtra("CODE_FOR", "signup");
                    startActivity(intent);
                }
                else if (status == 400){
                    stopAnim();
//                    progressDialog.dismiss();
                    Snackbar snackbar = Snackbar.make(v, ""+response.body().getMsg(), Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(ContextCompat.getColor(VerificationCodeActivity.this, R.color.red));
                    snackbar.show();
//                    Toast.makeText(VerificationCodeActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
//                Utilities.stopAnim();
                stopAnim();
                Log.i("checkingResponse", t.getMessage());
                Toast.makeText(VerificationCodeActivity.this, t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void resendVerificationCode(View v) {
        startAnim();
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<UserResponseModel> call = service.resendVerificationCode(user_Email);
        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {
                assert response.body() != null;
                int status  = response.body().getStatus();
//                Log.i("checking_Data11", ""+response.body().getMsg());
//                Log.i("checking_Data11", ""+response.body().getStatus());
//                Log.i("checking_Data11", ""+status);
                if (status == 200){
                    stopAnim();
                    Snackbar snackbar = Snackbar.make(v, ""+response.body().getMsg(), Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(ContextCompat.getColor(VerificationCodeActivity.this, R.color.colorGreen));
                    snackbar.show();
                }
                else if (status == 400){
//                    progressDialog.dismiss();
                    stopAnim();
                    Snackbar snackbar = Snackbar.make(v, ""+response.body().getMsg(), Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(ContextCompat.getColor(VerificationCodeActivity.this, R.color.red));
                    snackbar.show();
//                    Toast.makeText(VerificationCodeActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserResponseModel> call, Throwable t) {
//                progressDialog.dismiss();
//                Utilities.stopAnim();
                stopAnim();
                Log.i("checkingResponse", t.getMessage());
                Toast.makeText(VerificationCodeActivity.this, t.getMessage() + " Not Called", Toast.LENGTH_LONG).show();
            }
        });
    }

    void startAnim(){
        avi.show();
        // or avi.smoothToShow();
    }

    void stopAnim(){
        avi.hide();
        // or avi.smoothToHide();
    }

}