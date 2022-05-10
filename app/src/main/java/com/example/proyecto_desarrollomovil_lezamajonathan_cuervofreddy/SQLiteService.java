package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteService extends SQLiteOpenHelper {
    private static final String BDNAME = "hormiga.sqlite";
    private static final int BDVERSION = 1;
    private SQLiteDatabase BD;
    private static final String USUARIOSTABLA = "CREATE TABLE usuarios(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, correo TEXT, contrasena TEXT)";
    private static final String PRESUPUESTOTABLA = "CREATE TABLE presupuesto(idpresupuesto INTEGER PRIMARY KEY AUTOINCREMENT, tipopresupuesto TEXT, saldo REAL, iniciopresupuesto TEXT, finpresupuesto TEXT, meta TEXT, idusuario INTEGER, FOREIGN KEY(idusuario) REFERENCES usuarios(id))";
    private static final String GASTOTABLA = "CREATE TABLE gasto(idGasto INTEGER PRIMARY KEY AUTOINCREMENT, tipogasto TEXT, categoria TEXT, comentario TEXT, fecha TEXT, precio REAL, idusuario INTEGER, idpresupuesto INTEGER, FOREIGN KEY(idusuario) REFERENCES usuarios(id), FOREIGN KEY(idpresupuesto) REFERENCES presupuesto(idpresupuesto))";

    public SQLiteService(Context context){
        super(context, BDNAME,  null, BDVERSION);
        BD = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USUARIOSTABLA);
        db.execSQL(PRESUPUESTOTABLA);
        db.execSQL(GASTOTABLA);
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
    public Cursor ConsultarUsuPass( String correo, String pass) throws SQLException {
        Cursor mcursos = null;
        mcursos =this.getReadableDatabase().query("usuarios",
                new String[]{"id", "nombre", "correo", "contrasena"}, "correo like '"+
                correo +"'"+ "and contrasena like '"+
                pass +"'", null, null, null, null );
        return mcursos;
    }

    //TABLA GASTO
    //INGRESAR GASTO
    public void insertarGasto(String tipoGasto, String categoria, String comentario, String fecha, float precio, int idUsuario, int idPresupuesto){
        ContentValues cv = new ContentValues();
        cv.put("tipoGasto", tipoGasto);
        cv.put("categoria", categoria);
        cv.put("comentario", comentario);
        cv.put("fecha", fecha);
        cv.put("precio", precio);
        cv.put("idusuario", idUsuario);
        cv.put("idpresupuesto", idPresupuesto);

        BD.insert("gasto", null, cv);
    }

    public int consultarUsuarioSesion(String correo){
        int idUsuario = 0;
        Cursor cursor = BD.rawQuery("SELECT id FROM usuarios WHERE correo = '" + correo + "'", null);
        if(cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            } while (cursor.moveToNext());
        }
        return idUsuario;
    }
}
