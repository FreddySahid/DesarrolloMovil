package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class PresupuestoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presupuesto);

        Toolbar tolbar = findViewById(R.id.toolbarmenu);
        setSupportActionBar(tolbar);
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