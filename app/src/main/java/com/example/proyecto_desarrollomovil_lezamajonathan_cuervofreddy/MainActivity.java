package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {


    private SQLiteService BD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar tolbar = findViewById(R.id.toolbarmenu);
        setSupportActionBar(tolbar);

        BD = new SQLiteService(this);


        ArrayList<Gasto> listaUsuario = null;

        try {
            listaUsuario = BD.ConsultarGasto();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        ArrayList<String> listaUsuarioString = new ArrayList<String>(listaUsuario.size());
        for(Gasto c: listaUsuario){
            listaUsuarioString.add("Categoria: "+c.getCategoria()+ ", Gasto: "+c.getComentario()+", Precio: "+ c.getPrecio() + ", fecha: "+ c.getFecha()+ "Usuario: "+ c.getIdUsuario() );
        }

        ArrayAdapter<String> adaptador =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaUsuarioString);

        ListView listaview = (ListView) findViewById(R.id.ListaUsuarios);
        listaview.setAdapter(adaptador);


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
            Intent intent = new Intent(MainActivity.this, PresupuestoActivity.class);
            startActivity(intent);
        }else if(id == R.id.opcion2){
            Intent intent = new Intent(MainActivity.this, RegistrarGasto.class);
            startActivity(intent);
        }else if(id == R.id.opcion3){
            Intent intent = new Intent(MainActivity.this, ModificarDatos.class);
            startActivity(intent);
        }else if(id == R.id.opcion4){
            Toast.makeText(this, "Opción 4", Toast.LENGTH_LONG).show();
        }else if(id == R.id.opcion5){
            Toast.makeText(this, "Ya se encuentra ahí.", Toast.LENGTH_LONG).show();
        }else if (id == R.id.opcion6){
            Intent intent = new Intent(MainActivity.this, MainLogin.class);
            startActivity(intent);
        }
        return true;
    }
}