package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SQLiteService extends SQLiteOpenHelper {
    private static final String BDNAME = "hormiga2";
    private static final int BDVERSION = 1;
    private SQLiteDatabase BD;
    private static final String USUARIOSTABLA = "CREATE TABLE usuarios(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, correo TEXT, contrasena TEXT)";
    private static final String PRESUPUESTOTABLA = "CREATE TABLE presupuesto(idpresupuesto INTEGER PRIMARY KEY AUTOINCREMENT, tipopresupuesto TEXT, saldo REAL, iniciopresupuesto DATE, finpresupuesto DATE, meta Real, idusuario INTEGER, FOREIGN KEY(idusuario) REFERENCES usuarios(id))";
    private static final String GASTOTABLA = "CREATE TABLE gasto(idGasto INTEGER PRIMARY KEY AUTOINCREMENT, tipogasto TEXT, categoria TEXT, comentario TEXT, fecha DATE, precio REAL, idusuario INTEGER, idpresupuesto INTEGER, FOREIGN KEY(idusuario) REFERENCES usuarios(id), FOREIGN KEY(idpresupuesto) REFERENCES presupuesto(idpresupuesto))";

    public SQLiteService(Context context){
        super(context, BDNAME,  null, BDVERSION);
        BD = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS presupuesto");
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
        cv.put("fecha", String.valueOf(fecha));
        cv.put("precio", precio);
        cv.put("idusuario", idUsuario);
        cv.put("idpresupuesto", idPresupuesto);

        BD.insert("gasto", null, cv);
    }


    public void insertarPresupuesto(String tipopresupuesto, float saldo,
                                    String iniciopresupuesto, String finpresupuesto, float meta, int idusuario){
        ContentValues cv = new ContentValues();
        cv.put("tipopresupuesto", tipopresupuesto);
        cv.put("saldo", saldo);
        cv.put("iniciopresupuesto", String.valueOf(iniciopresupuesto));
        cv.put("finpresupuesto", String.valueOf(finpresupuesto));
        cv.put("meta", meta);
        cv.put("idusuario", idusuario);

        BD.insert("presupuesto", null, cv);
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

    public ArrayList<Gasto> ConsultarGasto( int idUsuario, String gasto, String fecha1, String fecha2 ) throws ParseException {
        ArrayList<Gasto> listaGastoUsuario = new ArrayList<Gasto>();
        Cursor cursor = BD.rawQuery("SELECT idGasto, tipogasto, categoria, comentario, fecha, precio, idusuario, idpresupuesto FROM gasto where idusuario = '"+idUsuario+"' AND tipogasto = '"+ gasto+ "' AND fecha BETWEEN '"+fecha1+"' AND '"+ fecha2+"'", null);
        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{

                int id = cursor.getInt(cursor.getColumnIndexOrThrow("idGasto"));
                String tipogasto = cursor.getString(cursor.getColumnIndexOrThrow("tipogasto"));
                String categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"));
                String comentario = cursor.getString(cursor.getColumnIndexOrThrow("comentario"));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
                float precio = cursor.getFloat(cursor.getColumnIndexOrThrow("precio"));
                int idusuario = cursor.getInt(cursor.getColumnIndexOrThrow("idusuario"));
                int idpresupuesto = cursor.getInt(cursor.getColumnIndexOrThrow("idpresupuesto"));


                Gasto canc = new Gasto(id, tipogasto, categoria, comentario,fecha, precio,idusuario,idpresupuesto );

                listaGastoUsuario.add(canc);
            }while (cursor.moveToNext());
        }


        return listaGastoUsuario;
    }

}
