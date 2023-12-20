package com.example.exchangerate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Exchange_Input extends AppCompatActivity {
    ;
    EditText ed;
    String cur;

    Double rate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
         cur = intent.getStringExtra("cur");
         rate = intent.getDoubleExtra("rate" , 0);

        assert cur != null;
        Log.i("ccc" , cur);
        setContentView(R.layout.activity_exchange_input);
        Button b  = findViewById(R.id.Exc);
        b.setText("CONVERT TO "+cur);
        ed = findViewById(R.id.money);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ed.getText().toString();
                Double bath = Double.parseDouble(text);
                Double sum = bath * rate;
                Intent intent = new Intent(Exchange_Input.this, Result.class);
                intent.putExtra("message",  String.format("%.1f %s = %.2f %s", bath , "THB" , sum , cur));
                startActivity(intent);
            }
        });
    }


}