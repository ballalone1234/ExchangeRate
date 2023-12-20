package com.example.exchangerate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        String me = intent.getStringExtra("message");
        setContentView(R.layout.activity_result);

        TextView t = findViewById(R.id.Result_mes);
        t.setText(me);
    }

}