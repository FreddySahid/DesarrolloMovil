package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SQLiteService BD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}