package com.my.booze.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.my.booze.R;
import com.my.booze.utilities.Utilities;

public class DeliveryAddressActivity extends AppCompatActivity {

    RelativeLayout btn_AddNewLocationLayout, recent_AddressLayout;
    ImageView backsign;
    TextView recent_Address;
    String stringAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);

        stringAddress = Utilities.getString(DeliveryAddressActivity.this, "User_RecentAddress");
        recent_AddressLayout = findViewById(R.id.recent_AddressLayout);
        recent_Address = findViewById(R.id.recent_Address);
        btn_AddNewLocationLayout = findViewById(R.id.btn_AddNewLocationLayout);
        backsign = findViewById(R.id.backsign);
        backsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(stringAddress.isEmpty()) {
            recent_AddressLayout.setVisibility(View.GONE);
        } else {
           recent_Address.setText(stringAddress);
        }
        btn_AddNewLocationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryAddressActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        recent_AddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryAddressActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}