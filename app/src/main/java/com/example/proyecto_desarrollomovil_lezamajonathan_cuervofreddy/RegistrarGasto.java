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

public class RegistrarGasto extends AppCompatActivity {
    SQLiteService helper;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Spinner tipoGasto;
    Spinner categoria;
    EditText comentario;
    EditText fecha;
    EditText precio;

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

        //Inicializar variables
        tipoGasto = findViewById(R.id.spTipoDato);
        categoria = findViewById(R.id.spCategoria);
        comentario = findViewById(R.id.textComentario);
        fecha = findViewById(R.id.textFecha);
        precio = findViewById(R.id.textPrecio);

        //Datos para guardar gasto


        Button btnEnviar = findViewById(R.id.btnRegistrarGasto);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textTipoGasto = tipoGasto.getSelectedItem().toString();
                String textCategoria = categoria.getSelectedItem().toString();
                String textComentario = comentario.getText().toString();
                String textFecha = fecha.getText().toString();
                String textPrecio = precio.getText().toString();
                float numPrecio = Float.parseFloat(textPrecio); //Float.parseFloat(textPrecio);
                int idUser = helper.consultarUsuarioSesion(correo);
                int idPresupuesto = 0;
                if (textTipoGasto.equals("") || textCategoria.equals("") || textComentario.equals("") || textFecha.equals("") || textPrecio.equals("")){
                    Toast.makeText(RegistrarGasto.this, "No puede dejar los campos vacios", Toast.LENGTH_SHORT).show();
                }else {
                    if (textTipoGasto.equals("Tipo de gasto")){
                        Toast.makeText(RegistrarGasto.this, "Tiene que escoger un gasto distinto", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (textCategoria.equals("Categoría")){
                        Toast.makeText(RegistrarGasto.this, "Tiene que escoger una categoría distinta", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (textComentario.equals("Comentario corto")){
                        Toast.makeText(RegistrarGasto.this, "Tiene que agrega un comentario", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (textFecha.equals("DD/MM/AAAA")){
                        Toast.makeText(RegistrarGasto.this, "Agrega la fecha en el formato DD/MM/AAAA", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (textPrecio.equals("Cantidad total")){
                        Toast.makeText(RegistrarGasto.this, "Agrega el precio total", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (textTipoGasto != "" && textCategoria != "" && textComentario != "" && textFecha != "" && textPrecio != "") {
                        helper.insertarGasto(textTipoGasto, textCategoria, textComentario, textFecha, numPrecio, idUser, idPresupuesto);

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
        }
        return true;
    }
}