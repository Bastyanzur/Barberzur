package com.example.barberzur.Vista;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.example.barberzur.R;



import android.widget.EditText;


import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class InciarSesionActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button btnLogin, btnIngresarRegistro, btnLogout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inciar_sesion);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogout = findViewById(R.id.btnLogout);

        mAuth = FirebaseAuth.getInstance();

        btnIngresarRegistro = findViewById(R.id.btnregresarRegistro);

        btnIngresarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí agregas la lógica para ir al activity_register
                irAActivityRegister();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });
    }

    private void irAActivityRegister() {
        Intent intent = new Intent(this, RegistrarseActivity.class);
        startActivity(intent);
    }

    private void iniciarSesion() {
        // Obtener los valores ingresados por el usuario
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Validar que los campos no estén vacíos
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(InciarSesionActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

// Iniciar sesión utilizando Firebase Authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(InciarSesionActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Inicio de sesión exitoso
                            Toast.makeText(InciarSesionActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                            // Redirigir al usuario a ManuActivity en lugar de AgendarHora
                            Intent intent = new Intent(InciarSesionActivity.this, MenuActivity.class);
                            startActivity(intent);
                            finish(); // Cerrar la actividad actual para que el usuario no pueda volver atrás

                        } else {
                            // Inicio de sesión fallido
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(InciarSesionActivity.this, "Error al iniciar sesión: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void cerrarSesion() {
        // Cerrar sesión en Firebase
        if (mAuth.getCurrentUser() != null) {
            mAuth.signOut();
            Toast.makeText(InciarSesionActivity.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(InciarSesionActivity.this, "No hay sesión activa", Toast.LENGTH_SHORT).show();
        }
    }
}