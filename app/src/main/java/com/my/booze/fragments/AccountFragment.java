package com.my.booze.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.$Gson$Preconditions;
import com.my.booze.R;
import com.my.booze.activities.CartActivity;
import com.my.booze.activities.LoginActivity;
import com.my.booze.activities.OrderDetailsActivity;
import com.my.booze.activities.SetNewPaswordActivity;
import com.my.booze.models.PlaceOrderResponseModel;
import com.my.booze.models.UserResponseModel;
import com.my.booze.network.GetDataService;
import com.my.booze.network.RetrofitClientInstance;
import com.my.booze.utilities.Utilities;
import com.wang.avi.AVLoadingIndicatorView;
import com.xgc1986.ripplebutton.widget.RippleButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    EditText name_EditText, email_EditText, password_EditText;
    RippleButton btn_ChangePassword;
    TextView btn_Logout;
    String user_Id;
    GetDataService service;
    public AVLoadingIndicatorView avi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        user_Id = Utilities.getString(getActivity(), "user_Id");
        avi = view.findViewById(R.id.avi);
        name_EditText = view.findViewById(R.id.name_EditText);
        email_EditText = view.findViewById(R.id.email_EditText);
        password_EditText = view.findViewById(R.id.password_EditText);
        btn_ChangePassword = view.findViewById(R.id.btn_ChangePassword);
        btn_Logout = view.findViewById(R.id.btn_Logout);
        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.clearSharedPref(getActivity());
                Toast.makeText(getActivity(), "Logout Successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        btn_ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetNewPaswordActivity.class);
                startActivity(intent);
            }
        });
        loadUser();
        return view;
    }

    public void loadUser() {
       name_EditText.setText(Utilities.getString(getActivity(), "user_Name"));
       email_EditText.setText(Utilities.getString(getActivity(), "user_Email"));
       password_EditText.setText(Utilities.getString(getActivity(), "user_Password"));
       stopAnim();
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