package com.example.barberzur.Vista;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barberzur.R;
import com.example.barberzur.controlador.ConexionHelper;
import com.example.barberzur.controlador.Utility;
import com.google.android.material.textfield.TextInputLayout;

public class InciarSesionActivity extends AppCompatActivity {

    TextView emailTextInputLayout, passwordTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inciar_sesion);

        Button loginButton = findViewById(R.id.InciarSesion);
        Button registerButton = findViewById(R.id.registerButton);

        // Obtén las referencias de TextInputLayout
        emailTextInputLayout = findViewById(R.id.emailEditText);
        passwordTextInputLayout = findViewById(R.id.passwordEditText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Valida el correo electrónico y la contraseña
                if (validateForm()) {
                    String email = emailTextInputLayout.getText().toString().trim();
                    String password = passwordTextInputLayout.getText().toString().trim();

                    // Aquí debes implementar la lógica para verificar las credenciales
                    if (isValidCredentials(email, password)) {
                        // Iniciar sesión exitosamente
                        startActivity(new Intent(InciarSesionActivity.this, MenuActivity.class));
                        // Muestra un mensaje de confirmación
                        Toast.makeText(InciarSesionActivity.this, "¡Iniciaste sesión correctamente!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Muestra un mensaje de error si las credenciales son incorrectas
                        Toast.makeText(InciarSesionActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inicia la actividad de registro cuando se presiona el botón "Registrarse"
                startActivity(new Intent(InciarSesionActivity.this, RegistrarseActivity.class));
            }
        });
    }

    // Función para validar los campos del formulario de inicio de sesión
    private boolean validateForm() {
        boolean isValid = true;

        String email = emailTextInputLayout.getText().toString().trim();
        String password = passwordTextInputLayout.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailTextInputLayout.setError("Este campo es requerido");
            isValid = false;
        } else {
            emailTextInputLayout.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordTextInputLayout.setError("Este campo es requerido");
            isValid = false;
        } else {
            passwordTextInputLayout.setError(null);
        }

        return isValid;
    }

    private boolean isValidCredentials(String email, String password) {
        ConexionHelper conn = new ConexionHelper(this, "Barberzur65.db", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        try {
            String[] columns = {Utility.CAMPO_CONTRASENA};
            String selection = Utility.CAMPO_CORREO + "=?";
            String[] selectionArgs = {email};

            Cursor cursor = db.query(Utility.TABLA_USUARIO, columns, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                String storedPassword = cursor.getString(cursor.getColumnIndex(Utility.CAMPO_CONTRASENA));
                cursor.close();
                return password.equals(storedPassword);
            } else {
                cursor.close();
                return false;
            }
        } finally {
            db.close();
        }
    }
}