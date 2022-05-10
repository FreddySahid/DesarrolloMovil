package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private SQLiteService BD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar tolbar = findViewById(R.id.toolbarmenu);
        setSupportActionBar(tolbar);

        BD = new SQLiteService(this);

        ArrayList<Usuario> listaUsuario = BD.ConsultaUsuario();

        ArrayList<String> listaUsuarioString = new ArrayList<String>(listaUsuario.size());
        for(Usuario c: listaUsuario){
            listaUsuarioString.add(c.getNombre() +"-"+ c.getEmail()+ " - "+ c.getPassword());
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
            Toast.makeText(this, "Opción 3", Toast.LENGTH_LONG).show();
        }else if(id == R.id.opcion4){
            Toast.makeText(this, "Opción 4", Toast.LENGTH_LONG).show();
        }else if(id == R.id.opcion5){
            Toast.makeText(this, "Ya se encuentra ahí.", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}