package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainLogin extends AppCompatActivity {
    SQLiteService helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        helper = new SQLiteService(this);

        Button btnInicioSesion = findViewById(R.id.btnInicioSesion);

        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textCorreo = findViewById(R.id.textCorreo);
                EditText textPassword = findViewById(R.id.textPassword);

                Cursor cursor = helper.ConsultarUsuPass(textCorreo.getText().toString(), textPassword.getText().toString());
                if(cursor.getCount()>0){
                    Intent intent = new Intent(MainLogin.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),  "Correo y/o Contraseña incorrectos", Toast.LENGTH_LONG).show();

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