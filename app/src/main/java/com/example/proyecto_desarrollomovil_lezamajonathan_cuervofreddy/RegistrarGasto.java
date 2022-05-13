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

public class RegistrarGasto extends AppCompatActivity {
    SQLiteService helper;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Spinner tipoGasto;
    Spinner categoria;
    EditText comentario;
    EditText fecha;
    EditText precio;

    int dia, mes, anio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_gasto);

        //Base de datos
        helper = new SQLiteService(this);

        Toolbar tolbar = findViewById(R.id.toolbarmenu);
        setSupportActionBar(tolbar);

        //Recuperar el correo del usuario en sesión
        //String nombre = preferences.getString('sesion', "");
        preferences = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editor = preferences.edit();
        String correo = preferences.getString("sesion", "");

        //Datos para guardar gasto

        Button btnEnviar = (Button) findViewById(R.id.btnRegistrarGasto);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //Inicializar variables
                    tipoGasto = (Spinner) findViewById(R.id.spTipoDato);
                    categoria = (Spinner) findViewById(R.id.spCategoria);
                    comentario = (EditText) findViewById(R.id.textComentario);
                    fecha = (EditText) findViewById(R.id.textFecha);
                    precio = (EditText) findViewById(R.id.textPrecio);
                    int idUser = helper.consultarUsuarioSesion(correo);
                    int idPresupuesto = 0;

                    if (comentario.getText().toString() == "" || fecha.getText().toString() == "" || precio.getText().toString() == ""){
                        Toast.makeText(RegistrarGasto.this, "No puede dejar los campos vacios", Toast.LENGTH_SHORT).show();
                    }else {
                        if (tipoGasto.getSelectedItem().toString() == "Tipo de gasto"){
                            Toast.makeText(RegistrarGasto.this, "Tiene que escoger un gasto distinto", Toast.LENGTH_SHORT).show();

                        }
                        if (categoria.getSelectedItem().toString() == "Categoría"){
                            Toast.makeText(RegistrarGasto.this, "Tiene que escoger una categoría distinta", Toast.LENGTH_SHORT).show();

                        }
                        if (comentario.getText().toString() == "Comentario corto"){
                            Toast.makeText(RegistrarGasto.this, "Tiene que agrega un comentario", Toast.LENGTH_SHORT).show();

                        }
                        if (fecha.getText().toString() == "DD/MM/AAAA"){
                            Toast.makeText(RegistrarGasto.this, "Agrega la fecha en el formato DD/MM/AAAA", Toast.LENGTH_SHORT).show();

                        }
                        if (precio.getText().toString() == "Cantidad total"){
                            Toast.makeText(RegistrarGasto.this, "Agrega el precio total", Toast.LENGTH_SHORT).show();

                        }
                        if (tipoGasto.getSelectedItem().toString() != "" && tipoGasto.getSelectedItem().toString() != "Tipo de gasto" && categoria.getSelectedItem().toString() != "" && categoria.getSelectedItem().toString() != "Categoría" && comentario.getText().toString() != "" && comentario.getText().toString() != "Comentario corto" && fecha.getText().toString() != "" && fecha.getText().toString() != "DD/MM/AAAA" && precio.getText().toString() != "" && precio.getText().toString() != "Cantidad total") {
                            String textTipoGasto = tipoGasto.getSelectedItem().toString();
                            String textCategoria = categoria.getSelectedItem().toString();
                            String textComentario = comentario.getText().toString();
                            String textFecha = fecha.getText().toString();
                            String textPrecio = precio.getText().toString();
                            float numPrecio = Float.parseFloat(textPrecio); //Float.parseFloat(textPrecio);

                            helper.insertarGasto(textTipoGasto, textCategoria, textComentario, textFecha, numPrecio, idUser, idPresupuesto);
                            Toast.makeText(RegistrarGasto.this, "Se han registrado el gasto", Toast.LENGTH_SHORT).show();
                            comentario.setText("");
                            fecha.setText("");
                            precio.setText("");
                        }else {
                            Toast.makeText(RegistrarGasto.this, "Favor de verificar la información", Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(RegistrarGasto.this, "Favor de verificar la información", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(RegistrarGasto.this, PresupuestoActivity.class);
            startActivity(intent);
        }else if(id == R.id.opcion2){
            Toast.makeText(this, "Ya se encuentra ahí.", Toast.LENGTH_LONG).show();
        }else if(id == R.id.opcion3){
            Toast.makeText(this, "Opción 3", Toast.LENGTH_LONG).show();
        }else if(id == R.id.opcion4){
            Toast.makeText(this, "Opción 4", Toast.LENGTH_LONG).show();
        }else if(id == R.id.opcion5){
            Intent intent = new Intent(RegistrarGasto.this, MainActivity.class);
            startActivity(intent);
        }else if (id == R.id.opcion6){
            Intent intent = new Intent(RegistrarGasto.this, MainLogin.class);
            startActivity(intent);
        }
        return true;
    }
}