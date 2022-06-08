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

import java.util.Calendar;

public class EditarPresupuesto extends AppCompatActivity {

    SQLiteService helper;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Spinner tipoPresupuesto;
    EditText saldo;
    EditText inicioPresupuesto;
    EditText finPresupuesto;
    EditText meta;

    Button btnModificarPre;

    String tipoPresupuestoOriginal;
    float saldoOriginal;
    String inicioPresupuestoOriginal;
    String finPresupuestoOriginal;
    float metaOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_presupuesto);

        //Base de datos
        helper = new SQLiteService(this);

        Toolbar tolbar = findViewById(R.id.toolbarmenu);
        setSupportActionBar(tolbar);

        //Recuperar el correo del usuario en sesión
        preferences = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editor = preferences.edit();
        String correo = preferences.getString("sesion", "");
        int idUser = helper.consultarUsuarioSesion(correo);

        tipoPresupuesto = findViewById(R.id.spinnerPresupuesto);
        saldo = findViewById(R.id.txtSaldo);
        inicioPresupuesto = findViewById(R.id.dateInicioPresupuesto);
        finPresupuesto = findViewById(R.id.dateFinPresupuesto);
        meta = findViewById(R.id.txtMeta);

        int idPresupuesto = (int) getIntent().getExtras().getSerializable("idPresupuesto");

        Presupuesto presupuesto = helper.buscarPresupuesto(idPresupuesto);
        tipoPresupuestoOriginal = presupuesto.getTipoPresupuesto();
        saldoOriginal = presupuesto.getSaldo();
        inicioPresupuestoOriginal = presupuesto.getInicioPresupuesto();
        finPresupuestoOriginal = presupuesto.getFinPresupuesto();
        metaOriginal = presupuesto.getMeta();

        int position = 0;
        for (int i = 0; i < 3; i++){
            if (tipoPresupuesto.getItemAtPosition(i).toString().equals(tipoPresupuestoOriginal)){
                position = i;
            }
        }
        tipoPresupuesto.setSelection(position);
        saldo.setText(Float.toString(saldoOriginal));
        inicioPresupuesto.setText(inicioPresupuestoOriginal);
        finPresupuesto.setText(finPresupuestoOriginal);
        meta.setText(Float.toString(metaOriginal));

        String[] partsInicio = inicioPresupuestoOriginal.split("/");
        String[] partsFin = finPresupuestoOriginal.split("/");

        Calendar calendar = Calendar.getInstance();
        final int year = Integer.parseInt(partsInicio[0]);
        final int month = Integer.parseInt(partsInicio[1]);
        final int day = Integer.parseInt(partsInicio[2]);

        inicioPresupuesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditarPresupuesto.this, new DatePickerDialog.OnDateSetListener() {
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

        Calendar calendar2 = Calendar.getInstance();
        final int year2 = Integer.parseInt(partsFin[0]);
        final int month2 = Integer.parseInt(partsFin[1]);
        final int day2 = Integer.parseInt(partsFin[2]);
        finPresupuesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditarPresupuesto.this, new DatePickerDialog.OnDateSetListener() {
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
                }, year2, month2, day2);
                datePickerDialog.show();

            }
        });

        btnModificarPre = (Button) findViewById(R.id.btnRegistrarPresupuesto);
        btnModificarPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texttipoPresupuesto = tipoPresupuesto.getSelectedItem().toString();
                String textSaldo = saldo.getText().toString();
                String textFechaInicio = inicioPresupuesto.getText().toString();
                String textFechaFin = finPresupuesto.getText().toString();
                String textMeta = meta.getText().toString();
                boolean band = false;

                if (tipoPresupuestoOriginal.equals(texttipoPresupuesto) && textSaldo.equals(Float.toString(saldoOriginal)) && inicioPresupuestoOriginal.equals(textFechaInicio) && finPresupuestoOriginal.equals(textFechaFin) && textMeta.equals(Float.toString(metaOriginal))){
                    Toast.makeText(EditarPresupuesto.this, "Presupuesto no editado", Toast.LENGTH_SHORT).show();
                } else {
                    if (texttipoPresupuesto.isEmpty()){
                        texttipoPresupuesto = tipoPresupuestoOriginal;
                    }
                    if (textSaldo.isEmpty()){
                        textSaldo = Float.toString(saldoOriginal);
                    }
                    if (textFechaInicio.isEmpty()){
                        textFechaInicio = inicioPresupuestoOriginal;
                    }
                    if (textFechaFin.isEmpty()){
                        textFechaFin = finPresupuestoOriginal;
                    }
                    if (textMeta.isEmpty()){
                        textMeta = Float.toString(metaOriginal);
                    }
                    if (!texttipoPresupuesto.isEmpty() && !textSaldo.isEmpty() && !textFechaInicio.isEmpty() && !textFechaFin.isEmpty() && !textMeta.isEmpty()) {
                        float numMeta = Float.parseFloat(textMeta);
                        float numSaldo = Float.parseFloat(textSaldo);
                        band = helper.modificarPresupuesto(idPresupuesto, texttipoPresupuesto, numSaldo, textFechaInicio, textFechaFin, numMeta);
                        if (band) {
                            Toast.makeText(EditarPresupuesto.this, "Presupuesto modificado", Toast.LENGTH_SHORT).show();
                            Presupuesto presupuesto = helper.buscarPresupuesto(idPresupuesto);
                            int position = 0;
                            for (int i = 0; i < 3; i++) {
                                if (tipoPresupuesto.getItemAtPosition(i).toString().equals(presupuesto.getTipoPresupuesto())) {
                                    position = i;
                                }
                            }
                            tipoPresupuesto.setSelection(position);
                            saldo.setText(Float.toString(presupuesto.getSaldo()));
                            inicioPresupuesto.setText(presupuesto.getInicioPresupuesto());
                            finPresupuesto.setText(presupuesto.getFinPresupuesto());
                            meta.setText(Float.toString(presupuesto.getMeta()));
                        } else {
                            Toast.makeText(EditarPresupuesto.this, "Error al editar presupuesto", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
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
            Intent intent = new Intent(EditarPresupuesto.this, PresupuestoActivity.class);
            startActivity(intent);
        }else if(id == R.id.opcion3){
            Intent intent = new Intent(EditarPresupuesto.this, HistorialPresupuestoActivity.class);
            startActivity(intent);
        }else if(id == R.id.opcion4){
            Intent intent = new Intent(EditarPresupuesto.this, MainActivity.class);
            startActivity(intent);
        }else if(id == R.id.opcion5){
            Intent intent = new Intent(EditarPresupuesto.this, ModificarDatos.class);
            startActivity(intent);
        }else if (id == R.id.opcion6){
            Intent intent = new Intent(EditarPresupuesto.this, MainLogin.class);
            startActivity(intent);
        }
        return true;
    }
}