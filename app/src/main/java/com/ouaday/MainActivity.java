package com.ouaday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ouaday.interfaces.employe.EmployeList;

public class MainActivity extends AppCompatActivity {
    private Button professeurBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        professeurBtn = findViewById(R.id.idEmployeBtn);
        professeurBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EmployeList.class);
                startActivity(intent);
            }
        });

    }
}