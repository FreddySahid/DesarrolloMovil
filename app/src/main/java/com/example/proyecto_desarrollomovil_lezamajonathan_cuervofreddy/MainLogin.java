package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainLogin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

    }
    public void Registrarse(View view){

            Intent intent = new Intent(MainLogin.this, ActivityRegistro.class);
            startActivity(intent);



    }
}