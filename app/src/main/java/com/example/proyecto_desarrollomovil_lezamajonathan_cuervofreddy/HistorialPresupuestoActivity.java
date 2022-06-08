package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class HistorialPresupuestoActivity extends AppCompatActivity {

    private SQLiteService BD;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    Spinner tipoPresupuesto;
    int idUser;
    ArrayList<Integer> listaPresupuestoInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_presupuesto);

        Toolbar tolbar = findViewById(R.id.toolbarmenu);
        setSupportActionBar(tolbar);

        BD = new SQLiteService(this);
        preferences = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editor = preferences.edit();
        String correo = preferences.getString("sesion", "");
        idUser = BD.consultarUsuarioSesion(correo);

        tipoPresupuesto = findViewById(R.id.spTipoDatoPresupuesto);

    }

    public void BuscarPresupuesto( View view){

        ArrayList<Presupuesto> listaPresupuesto = null;

        String gasto = tipoPresupuesto.getSelectedItem().toString();

        if(gasto.equals("Presupuesto")){
            Toast.makeText(HistorialPresupuestoActivity.this, "Debe seleccionar un tipo de presupuesto", Toast.LENGTH_SHORT).show();

        }else if(gasto.equals("Todos")){
            try {

                listaPresupuesto = BD.ConsultarPresupuesto2(idUser);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            ArrayList<String> listaPresupuestoString = new ArrayList<String>(listaPresupuesto.size());
            listaPresupuestoInt = new ArrayList<>(listaPresupuesto.size());
            for(Presupuesto c: listaPresupuesto){
                listaPresupuestoString.add("Fecha inicial: "+ c.getInicioPresupuesto() + "\nFecha final: "+c.getFinPresupuesto()+ "\nSaldo: "+ c.getSaldo() + "\nMeta: "+ c.getMeta()+ "\nTipo de presupuesto: "+ c.getTipoPresupuesto() );
                listaPresupuestoInt.add(c.getId());
            }

            ArrayAdapter<String> adaptador =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaPresupuestoString);



            ListView listaview = (ListView) findViewById(R.id.ListaPresupuestos);
            listaview.setAdapter(adaptador);
            registerForContextMenu(listaview);


        }else{
            try {

                listaPresupuesto = BD.ConsultarPresupuesto(idUser, gasto);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            ArrayList<String> listaUsuarioString = new ArrayList<String>(listaPresupuesto.size());
            listaPresupuestoInt = new ArrayList<>(listaPresupuesto.size());
            for(Presupuesto c: listaPresupuesto){
                listaUsuarioString.add("Fecha inicial: "+ c.getInicioPresupuesto() + "\nFecha final: "+c.getFinPresupuesto()+ "\nSaldo: "+ c.getSaldo() + "\nMeta: "+ c.getMeta() );
                listaPresupuestoInt.add(c.getId());
            }

            ArrayAdapter<String> adaptador =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaUsuarioString);



            ListView listaview = (ListView) findViewById(R.id.ListaPresupuestos);
            listaview.setAdapter(adaptador);
            registerForContextMenu(listaview);

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
            case R.id.context_editar:
                Intent intent = new Intent(HistorialPresupuestoActivity.this, EditarPresupuesto.class);
                intent.putExtra("idPresupuesto", listaPresupuestoInt.get(n));
                startActivity(intent);
                return true;
            case R.id.context_delete:
                BD.borrarPresupuesto(listaPresupuestoInt.get(n));
                Toast.makeText(HistorialPresupuestoActivity.this, "Presupuesto eliminado ", Toast.LENGTH_SHORT).show();
                BuscarPresupuesto(null);
                return true;
            case R.id.context_nuevoGasto:
                Intent intent1 = new Intent(HistorialPresupuestoActivity.this, RegistrarGasto.class);
                intent1.putExtra("idPresupuesto", listaPresupuestoInt.get(n));
                startActivity(intent1);
                return true;
            default:
                return super.onContextItemSelected(item);

        }


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
            Intent intent = new Intent(HistorialPresupuestoActivity.this, PresupuestoActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.opcion3){
            Toast.makeText(this, "Ya se encuentra ahí.", Toast.LENGTH_LONG).show();
        }else if(id == R.id.opcion4){
            Intent intent = new Intent(HistorialPresupuestoActivity.this, MainActivity.class);
            startActivity(intent);
        }else if(id == R.id.opcion5){
            Intent intent = new Intent(HistorialPresupuestoActivity.this, ModificarDatos.class);
            startActivity(intent);
        }else if (id == R.id.opcion6){
            Intent intent = new Intent(HistorialPresupuestoActivity.this, MainLogin.class);
            startActivity(intent);
        }
        return true;
    }
}