package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {


    private SQLiteService BD;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    Spinner tipoGasto;
    EditText fecha1;
    EditText fecha2;
    int idUser;
    ArrayList<Integer> listaUsuarioString2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar tolbar = findViewById(R.id.toolbarmenu);
        setSupportActionBar(tolbar);

        BD = new SQLiteService(this);
        preferences = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editor = preferences.edit();
        String correo = preferences.getString("sesion", "");
        idUser = BD.consultarUsuarioSesion(correo);

        tipoGasto = findViewById(R.id.spTipoDatoLista);
        fecha1 = findViewById(R.id.textHistorialInicioGasto);
        fecha2 = findViewById(R.id.textHistorialFinGasto);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);



        fecha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                        fecha2.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

        fecha1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                        fecha1.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

    }


    public void Buscar( View view){

        ArrayList<Gasto> listaUsuario = null;

        String gasto = tipoGasto.getSelectedItem().toString();
        String fechaInicio = fecha1.getText().toString();
        String fechafin = fecha2.getText().toString();
        if(gasto.equals("") || fechaInicio.equals("") || fechafin.equals("")){
            Toast.makeText(MainActivity.this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();

        }
        else if(gasto.equals("Tipo de gasto")){
            Toast.makeText(MainActivity.this, "Debe seleccionar un tipo de gasto", Toast.LENGTH_SHORT).show();

        }else if (gasto.equals("Todos")){
            try {

                listaUsuario = BD.ConsultarGasto2(idUser, fechaInicio, fechafin);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            ArrayList<String> listaUsuarioString = new ArrayList<String>(listaUsuario.size());
            listaUsuarioString2 = new ArrayList<>(listaUsuario.size());
            for(Gasto c: listaUsuario){
                listaUsuarioString.add("ID: "+ c.getIdGasto()+" Fecha: "+ c.getFecha() + "\nCategoría: "+c.getCategoria()+ "\nComentario: "+ c.getComentario() + "\nCosto: "+ c.getPrecio() + "\nTipo de gasto: " + c.getTipoGasto() );
                listaUsuarioString2.add(c.getIdGasto());
            }

            ArrayAdapter<String> adaptador =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaUsuarioString);



            ListView listaview = (ListView) findViewById(R.id.ListaUsuarios);
            listaview.setAdapter(adaptador);
            registerForContextMenu(listaview);

        }else{
            try {

                listaUsuario = BD.ConsultarGasto(idUser, gasto, fechaInicio, fechafin);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            ArrayList<String> listaUsuarioString = new ArrayList<String>(listaUsuario.size());
            listaUsuarioString2 = new ArrayList<>(listaUsuario.size());
            for(Gasto c: listaUsuario){
                listaUsuarioString.add("ID: "+ c.getIdGasto()+" Fecha: "+ c.getFecha() + "\nCategoría: "+c.getCategoria()+ "\nComentario: "+ c.getComentario() + "\nCosto: "+ c.getPrecio() );
                listaUsuarioString2.add(c.getIdGasto());
            }

            ArrayAdapter<String> adaptador =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaUsuarioString);



            ListView listaview = (ListView) findViewById(R.id.ListaUsuarios);
            listaview.setAdapter(adaptador);

            listaview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    registerForContextMenu(listaview);


                }
            });


        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int n = i.position;

        switch (item.getItemId()) {
            case R.id.context_delete:
                Toast.makeText(MainActivity.this, "item: "+listaUsuarioString2.get(n), Toast.LENGTH_SHORT).show();



        }


        return super.onContextItemSelected(item);
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
            Intent intent = new Intent(MainActivity.this, HistorialPresupuestoActivity.class);
            startActivity(intent);
        }else if(id == R.id.opcion4){
            Toast.makeText(this, "Ya se encuentra ahí.", Toast.LENGTH_LONG).show();
        }else if(id == R.id.opcion5){
            Intent intent = new Intent(MainActivity.this, ModificarDatos.class);
            startActivity(intent);
        }else if (id == R.id.opcion6){
            Intent intent = new Intent(MainActivity.this, MainLogin.class);
            startActivity(intent);
        }
        return true;
    }
}