package com.example.barberzur.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.barberzur.R;

public class MenuActivity extends AppCompatActivity {
    Button btnMapa;
    Button btnAgendar;

    Button btnHoras;

    Button btnCancelar;

    Button btnFotos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnAgendar=findViewById(R.id.btnAgendar);
        btnAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, AgendarHora.class));
            }
        });
        btnMapa=findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, MapaUbi.class));
            }
        });
        btnHoras=findViewById(R.id.btnHoras);
        btnHoras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, HorasAgendadas.class));
            }
        });
        btnCancelar=findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, CancelarHoras.class));
            }
        });
        btnFotos=findViewById(R.id.btnFotos);
        btnFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, GaleriaFotos.class));
            }
        });

    }
}