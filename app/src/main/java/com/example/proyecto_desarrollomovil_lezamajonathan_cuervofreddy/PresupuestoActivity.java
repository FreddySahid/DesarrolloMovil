package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);



        inicioPresupuesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PresupuestoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
                        month = month+1;
                        String date;
                        if(month<10 ){
                            if(dayofMonth<10){
                                date = year+"/0"+month+"/0"+dayofMonth;
                            }else{
                                date = year+"/0"+month+"/"+dayofMonth;
                            }

                        }   else {
                            if(dayofMonth<10){
                                date = year+"/"+month+"/0"+dayofMonth;
                            }else{
                                date = year+"/"+month+"/"+dayofMonth;
                            }
                        }

                        inicioPresupuesto.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });
        finPresupuesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PresupuestoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayofMonth) {
                        month = month+1;
                        String date;
                        if(month<10 ){
                            if(dayofMonth<10){
                                date = year+"/0"+month+"/0"+dayofMonth;
                            }else{
                                date = year+"/0"+month+"/"+dayofMonth;
                            }

                        }   else {
                            if(dayofMonth<10){
                                date = year+"/"+month+"/0"+dayofMonth;
                            }else{
                                date = year+"/"+month+"/"+dayofMonth;
                            }
                        }

                        finPresupuesto.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });


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
        }else if (id == R.id.opcion6){
            Intent intent = new Intent(PresupuestoActivity.this, MainLogin.class);
            startActivity(intent);
        }
        return true;
    }
}