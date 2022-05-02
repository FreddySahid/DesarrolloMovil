package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class SQLiteService extends SQLiteOpenHelper {

    private static final String BDNAME = "hormiga.sqlite";
    private static final int BDVERSION = 1;
    private SQLiteDatabase BD;
    private static final String USUARIOSTABLA = "CREATE TABLE usuarios(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, correo TEXT, contrasena TEXT)";

    public SQLiteService(Context context){
        super(context, BDNAME,  null, BDVERSION);
        BD = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USUARIOSTABLA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertarUsuarios(String nombre, String correo, String contrasena){
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("correo", correo);
        cv.put("contrasena", contrasena);

        BD.insert("usuarios", null, cv);
    }
    public ArrayList<Usuario> ConsultaUsuario(){
        ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
        Cursor cursor = BD.rawQuery("SELECT id, nombre, correo, contrasena FROM usuarios", null);
        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String correo = cursor.getString(cursor.getColumnIndexOrThrow("correo"));
                String contrasena = cursor.getString(cursor.getColumnIndexOrThrow("contrasena"));

                Usuario canc = new Usuario(id, nombre, correo, contrasena);

                listaUsuarios.add(canc);
            }while (cursor.moveToNext());
        }


        return listaUsuarios;
    }

}
