package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class PresupuestoActivity extends AppCompatActivity {

    SQLiteService helper;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Spinner tipoPresupuesto;
    EditText saldo;
    EditText inicioPresupuesto;
    EditText finPresupuesto;
    EditText meta;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presupuesto);
        helper = new SQLiteService(this);

        Toolbar tolbar = findViewById(R.id.toolbarmenu);
        setSupportActionBar(tolbar);

        preferences = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editor = preferences.edit();
        String correo = preferences.getString("sesion", "");




        tipoPresupuesto = findViewById(R.id.spinnerPresupuesto);
        saldo = findViewById(R.id.textSaldo);

        inicioPresupuesto = findViewById(R.id.dateInicioPresupuesto);
        finPresupuesto = findViewById(R.id.dateFinPresupuesto);
        meta = findViewById(R.id.textMeta);


        Button btnGuardar = findViewById(R.id.btnRegistrarPresupuesto);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texttipoPresupuesto = tipoPresupuesto.getSelectedItem().toString();
                String textSaldo = saldo.getText().toString();

                String textFechaInicio = inicioPresupuesto.getText().toString();
                String textFechaFin = finPresupuesto.getText().toString();
                String textMeta = meta.getText().toString();
                int idUser = helper.consultarUsuarioSesion(correo);


                if(texttipoPresupuesto.equals("") || textSaldo.equals("") || textFechaInicio.equals("") || textFechaFin.equals("") || textMeta.equals("") ){
                    Toast.makeText(PresupuestoActivity.this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();


                }else if(texttipoPresupuesto.equals("Presupuesto")){
                    Toast.makeText(PresupuestoActivity.this, "Debe que elegir un tipo de presupuesto", Toast.LENGTH_SHORT).show();

                }else{
                    float numMeta = Float.parseFloat(textMeta);
                    float numSaldo = Float.parseFloat(textSaldo); //Float.parseFloat(textPrecio);


                    helper.insertarPresupuesto(texttipoPresupuesto, numSaldo, textFechaInicio, textFechaFin, numMeta, idUser);
                    Toast.makeText(PresupuestoActivity.this, "Se han guardado los datos", Toast.LENGTH_SHORT).show();
                    saldo.setText("");
                    inicioPresupuesto.setText("");
                    finPresupuesto.setText("");
                    meta.setText("");

                }

            }});

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.opcion1){
            Toast.makeText(this, "Ya se encuentra ahí.", Toast.LENGTH_LONG).show();
        }else if(id == R.id.opcion2){
            Intent intent = new Intent(PresupuestoActivity.this, RegistrarGasto.class);
            startActivity(intent);
        }else if(id == R.id.opcion3){
            Toast.makeText(this, "Opción 3", Toast.LENGTH_LONG).show();
        }else if(id == R.id.opcion4){
            Toast.makeText(this, "Opción 4", Toast.LENGTH_LONG).show();
        }else if(id == R.id.opcion5){
            Intent intent = new Intent(PresupuestoActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return true;
    }
}