package com.my.booze.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.my.booze.R;
import com.xgc1986.ripplebutton.widget.RippleButton;

public class QuestionActivity extends AppCompatActivity {

    RippleButton btn_Yes, btn_No;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        btn_No = findViewById(R.id.btn_No);
        btn_Yes = findViewById(R.id.btn_Yes);

        btn_Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}