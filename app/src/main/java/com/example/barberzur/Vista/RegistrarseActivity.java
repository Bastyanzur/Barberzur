package com.example.barberzur.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barberzur.R;
import com.example.barberzur.controlador.ConexionHelper;
import com.example.barberzur.controlador.Utility;

    public class RegistrarseActivity extends AppCompatActivity {
    private EditText nameEditText, edadEditText, emailEditText, passwordEditText;
    private Button btnConsultar, btnUpdate, btnDelete;
    private ConexionHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
        conn = new ConexionHelper(getApplicationContext(), "Barberzur65.db", null, 1);

        nameEditText = findViewById(R.id.nameEditText);
        edadEditText = findViewById(R.id.edadEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        Button registerButton = findViewById(R.id.registerButton);
        btnConsultar = findViewById(R.id.consultarButton);
        btnUpdate = findViewById(R.id.updateButton);
        btnDelete = findViewById(R.id.deleteButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString().trim();
                String edad = edadEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (validateForm(name, edad, email, password)) {
                    registrarUsuarioEnBD(name, edad, email, password);

                    Intent intent = new Intent(RegistrarseActivity.this, InciarSesionActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultar();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarUsuario();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarUsuario();
            }
        });
    }

        private boolean validateForm(String name, String edad, String email, String password) {
            boolean isValid = true;

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(edad) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                // Manejar el caso en el que algún campo esté vacío
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return false;
            }

            // Validar formato de correo electrónico
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.setError("Correo electrónico no válido");
                isValid = false;
            } else {
                emailEditText.setError(null);
            }

            // Validar contraseña
            if (password.length() < 6) { // Cambia el tamaño según tus requisitos
                passwordEditText.setError("La contraseña debe tener al menos 6 caracteres");
                isValid = false;
            } else {
                passwordEditText.setError(null);
            }

            return isValid;
        }

    private void registrarUsuarioEnBD(String name, String edad, String email, String password) {
        // Validar el formulario antes de registrar
        if (!validateForm(name, edad, email, password)) {
            return;
        }
        ConexionHelper conn = new ConexionHelper(this, "Barberzur65.db", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Utility.CAMPO_NOMBRE, name);
        contentValues.put(Utility.CAMPO_EDAD, edad);
        contentValues.put(Utility.CAMPO_CORREO, email);
        contentValues.put(Utility.CAMPO_CONTRASENA, password);

        long idResultado = db.insert(Utility.TABLA_USUARIO, null, contentValues);
        Toast.makeText(getApplicationContext(), "ATENCION, ID Registrado: " + idResultado, Toast.LENGTH_SHORT).show();

        // Cerrar la conexión con la base de datos
        db.close();
        conn.close();
    }

    private void consultar() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {nameEditText.getText().toString()};

        try {
            // Utilizar el método query para evitar la inyección SQL
            Cursor cursor = db.query(
                    Utility.TABLA_USUARIO,
                    new String[]{Utility.CAMPO_CORREO, Utility.CAMPO_EDAD},
                    Utility.CAMPO_NOMBRE + "=?",
                    parametros,
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();

            if (cursor.getCount() > 0) {
                emailEditText.setText(cursor.getString(0));
                edadEditText.setText(cursor.getString(1));
            } else {
                Toast.makeText(getApplicationContext(), "ATENCION, usuario no existe", Toast.LENGTH_LONG).show();
                limpiar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actualizarUsuario() {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {nameEditText.getText().toString()};

        ContentValues values = new ContentValues();
        values.put(Utility.CAMPO_NOMBRE, nameEditText.getText().toString());
        values.put(Utility.CAMPO_CORREO, emailEditText.getText().toString());
        values.put(Utility.CAMPO_EDAD, edadEditText.getText().toString());
        values.put(Utility.CAMPO_CONTRASENA, passwordEditText.getText().toString());

        // Utilizar el método update para evitar la inyección SQL
        db.update(Utility.TABLA_USUARIO, values, Utility.CAMPO_NOMBRE + "=?", parametros);
        Toast.makeText(getApplicationContext(), "ATENCION, se actualizó el usuario", Toast.LENGTH_LONG).show();
        limpiar();
        db.close();
    }

    private void eliminarUsuario() {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {nameEditText.getText().toString()};

        // Utilizar el método delete para evitar la inyección SQL
        db.delete(Utility.TABLA_USUARIO, Utility.CAMPO_NOMBRE + "=?", parametros);
        Toast.makeText(getApplicationContext(), "ATENCION, se eliminó el usuario", Toast.LENGTH_LONG).show();
        limpiar();
        db.close();
    }

    private void limpiar() {
        emailEditText.setText("");
        edadEditText.setText("");
    }

}