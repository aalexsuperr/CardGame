package com.example.cardgame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etPlayer1 = findViewById(R.id.et_player1);
        EditText etPlayer2 = findViewById(R.id.et_player2);
        Button btnStart = findViewById(R.id.btn_start);

        btnStart.setOnClickListener(v -> {
            String name1 = etPlayer1.getText().toString().trim();
            String name2 = etPlayer2.getText().toString().trim();
            if (name1.isEmpty()) name1 = "Игрок 1";
            if (name2.isEmpty()) name2 = "Игрок 2";
            if (name1.equals(name2)) name2 = name2 + " 2";

            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("player1_name", name1);
            intent.putExtra("player2_name", name2);
            startActivity(intent);
        });
    }
}
