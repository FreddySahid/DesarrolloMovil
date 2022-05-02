package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityRegistro extends AppCompatActivity {
    private SQLiteService miDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        final EditText textNombreRegistro = findViewById(R.id.textNombreRegistro);
        final EditText textCorreoRegistro = findViewById(R.id.textCorreoRegistro);
        final EditText textPasswordRegistro = findViewById(R.id.textPasswordRegistro);
        final Button btnRegistrase = findViewById(R.id.btnRegistrase);
        miDB = new SQLiteService ( this);
        btnRegistrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = textNombreRegistro.getText().toString();
                String correo = textCorreoRegistro.getText().toString();
                String password = textPasswordRegistro.getText().toString();

                miDB.insertarUsuarios(nombre, correo, password);

                Toast.makeText(ActivityRegistro.this, "Se guardo Usuario", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(ActivityRegistro.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}