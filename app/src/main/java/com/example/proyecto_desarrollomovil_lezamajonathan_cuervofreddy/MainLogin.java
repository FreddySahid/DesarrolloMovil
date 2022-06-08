package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainLogin extends AppCompatActivity {
    SQLiteService helper;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        helper = new SQLiteService(this);

        preferences = this.getSharedPreferences("sesiones",Context.MODE_PRIVATE);
        editor = preferences.edit();

        Button btnInicioSesion = findViewById(R.id.btnInicioSesion);

        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textCorreo = findViewById(R.id.textCorreo);
                EditText textPassword = findViewById(R.id.textPassword);
                String correo = textCorreo.getText().toString();
                String password = textPassword.getText().toString();

                Cursor cursor = helper.ConsultarUsuPass(textCorreo.getText().toString(), textPassword.getText().toString());
                if (correo.equals("") || textPassword.equals("")){
                    Toast.makeText(getApplicationContext(),  "Debes llenar todos los campos", Toast.LENGTH_LONG).show();

                }else {
                    if(cursor.getCount()>0){
                        editor.putString("sesion",correo );
                        editor.apply();

                        Intent intent = new Intent(MainLogin.this, HistorialPresupuestoActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),  "Correo y/o Contraseña incorrectos", Toast.LENGTH_LONG).show();

                    }
                }
                textCorreo.setText("");
                textPassword.setText("");
                textCorreo.findFocus();

            }
        });



    }
    public void Registrarse(View view){

            Intent intent = new Intent(MainLogin.this, ActivityRegistro.class);
            startActivity(intent);

    }
}