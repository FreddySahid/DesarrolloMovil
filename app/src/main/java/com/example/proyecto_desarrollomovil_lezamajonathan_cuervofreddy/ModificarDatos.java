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
import android.widget.Toast;

public class ModificarDatos extends AppCompatActivity {

    //Declarar variables
    EditText name;
    EditText email;
    EditText pass;
    EditText confPass;
    Button btnGuardarCambios;

    //Variables de la base de datos
    SQLiteService helper;

    //SharedPrefences para control de usuario en sesión
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_datos);

        //Base de datos
        helper = new SQLiteService(this);

        Toolbar tolbar = findViewById(R.id.toolbarmenu);
        setSupportActionBar(tolbar);

        //Recuperar el correo del usuario en sesión
        //String nombre = preferences.getString('sesion', "");
        preferences = this.getSharedPreferences("sesiones", Context.MODE_PRIVATE);
        editor = preferences.edit();
        String correo = preferences.getString("sesion", "");
        int idUser = helper.consultarUsuarioSesion(correo);

        /*try {
            Usuario user = helper.buscarUsuario(idUser);
        }catch (Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }*/


        //System.out.print(user.getNombre() + user.getEmail() + user.getPassword());

        //Declarar las variables que se ocupan en la activity
        name = (EditText) findViewById(R.id.textNombre);
        email = (EditText) findViewById(R.id.textEmail);
        pass = (EditText) findViewById(R.id.textPass);
        confPass = (EditText) findViewById(R.id.textConfPass);

        //Declarar variable de botón
        btnGuardarCambios = (Button) findViewById(R.id.btnGuardarCambios);
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameUser = name.getText().toString();
                String emailUser = email.getText().toString();
                String passwordNueva = pass.getText().toString();
                String confirmarPass = confPass.getText().toString();
                String passwordUser = "";

                if (!passwordNueva.equals(confirmarPass)){
                    Toast.makeText(ModificarDatos.this, "Error al confirmar contraseña", Toast.LENGTH_SHORT).show();
                }else{
                    passwordUser = passwordNueva;
                }
                if (passwordNueva.equals(confirmarPass)){
                    helper.modificarDatos(idUser, nameUser, emailUser, passwordUser);
                    Toast.makeText(ModificarDatos.this, "Datos modificados", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    email.setText("");
                    pass.setText("");
                    confPass.setText("");
                } else {
                    Toast.makeText(ModificarDatos.this, "Error al modificar datos", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(ModificarDatos.this, PresupuestoActivity.class);
            startActivity(intent);
        }else if(id == R.id.opcion2){
            Intent intent = new Intent(ModificarDatos.this, RegistrarGasto.class);
            startActivity(intent);
        }else if(id == R.id.opcion3){
            Intent intent = new Intent(ModificarDatos.this, HistorialPresupuestoActivity.class);
            startActivity(intent);
        }else if(id == R.id.opcion4){
            Intent intent = new Intent(ModificarDatos.this, MainActivity.class);
            startActivity(intent);
        }else if(id == R.id.opcion5){
            Toast.makeText(this, "Ya se encuentra ahí.", Toast.LENGTH_LONG).show();
        }else if (id == R.id.opcion6){
            Intent intent = new Intent(ModificarDatos.this, MainLogin.class);
            startActivity(intent);
        }
        return true;
    }
}