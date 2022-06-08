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
        Cursor cursor = BD.rawQuery("SELECT idGasto, tipogasto, categoria, comentario, fecha, precio, idusuario, idpresupuesto FROM gasto where idusuario = '"+idUsuario+"' AND tipogasto = '"+ gasto+ "' AND fecha BETWEEN '"+fecha1+"' AND '"+ fecha2+"'  ORDER by fecha DESC", null);
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

    public ArrayList<Gasto> ConsultarGasto2( int idUsuario, String fecha1, String fecha2 ) throws ParseException {
        ArrayList<Gasto> listaGastoUsuario = new ArrayList<Gasto>();
        Cursor cursor = BD.rawQuery("SELECT idGasto, tipogasto, categoria, comentario, fecha, precio, idusuario, idpresupuesto FROM gasto where idusuario = '"+idUsuario+"'AND fecha BETWEEN '"+fecha1+"' AND '"+ fecha2+"'  ORDER by fecha DESC", null);
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

    // @Start Modificar datos personales

    //Buscar usuario
    public String buscarNombreUsuario(int id){
        String nombre = "";
        Cursor cursor = BD.rawQuery("SELECT nombre FROM usuarios WHERE id = '" + id + "'", null);
        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));

            } while (cursor.moveToNext());
        }
        return nombre;
    }

    public String buscarEmailUsuario(int id){
        String email = "";
        Cursor cursor = BD.rawQuery("SELECT correo FROM usuarios WHERE id = '" + id + "'", null);
        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                email = cursor.getString(cursor.getColumnIndexOrThrow("correo"));

            } while (cursor.moveToNext());
        }
        return email;
    }

    //Modificar datos
    public boolean modificarDatos(int idUser, String nameUser, String emailUser, String passwordUser){
        String id = Integer.toString(idUser);//Revisar
        try{
            if (!nameUser.isEmpty() && !emailUser.isEmpty() && !passwordUser.isEmpty()){ //Si necesita modificar todos los datos
                ContentValues cv = new ContentValues();
                cv.put("nombre", nameUser);
                cv.put("correo", emailUser);
                cv.put("contrasena", passwordUser);

                BD.update("usuarios", cv, "id = ?", new String[]{id});
                return true;
            }
            if (nameUser.isEmpty() && !emailUser.isEmpty() && !passwordUser.isEmpty()){ //Si no necesita modificar nombre
                ContentValues cv = new ContentValues();
                cv.put("correo", emailUser);
                cv.put("contrasena", passwordUser);

                BD.update("usuarios", cv, "id = ?", new String[]{id});
                return true;
            }
            if (!nameUser.isEmpty() && emailUser.isEmpty() && !passwordUser.isEmpty()){ //Si no necesita modificar email
                ContentValues cv = new ContentValues();
                cv.put("nombre", nameUser);
                cv.put("contrasena", passwordUser);

                BD.update("usuarios", cv, "id = ?", new String[]{id});
                return true;
            }
            if (!nameUser.isEmpty() && !emailUser.isEmpty() && passwordUser.isEmpty()){ //Si no necesita modificar la contraseña
                ContentValues cv = new ContentValues();
                cv.put("nombre", nameUser);
                cv.put("correo", emailUser);

                BD.update("usuarios", cv, "id = ?", new String[]{id});
                return true;
            }
            if (!nameUser.isEmpty() && emailUser.isEmpty() && passwordUser.isEmpty()){ //Si solo necesita modificar nombre
                ContentValues cv = new ContentValues();
                cv.put("nombre", nameUser);
                BD.update("usuarios", cv, "id = ?", new String[]{id});
                return true;
            }
            if (nameUser.isEmpty() && !emailUser.isEmpty() && passwordUser.isEmpty()){ //Si solo necesita modificar email
                ContentValues cv = new ContentValues();
                cv.put("correo", emailUser);

                BD.update("usuarios", cv, "id = ?", new String[]{id});
                return true;
            }
            if (nameUser.isEmpty() && emailUser.isEmpty() && !passwordUser.isEmpty()){ //Si solo necesita modificar password
                ContentValues cv = new ContentValues();
                cv.put("contrasena", passwordUser);

                BD.update("usuarios", cv, "id = ?", new String[]{id});
                return true;
            }
            return false;
        } catch (Exception e){
            return false;
        }
    }

    //Consultar historial de presupuestos



    public ArrayList<Presupuesto> ConsultarPresupuesto( int idUsuario, String tipo ) throws ParseException {
        ArrayList<Presupuesto> listapresupuesto = new ArrayList<Presupuesto>();
        Cursor cursor = BD.rawQuery("SELECT idpresupuesto, tipopresupuesto, saldo, iniciopresupuesto, finpresupuesto, meta, idusuario FROM presupuesto where idusuario = '"+idUsuario+"' AND tipopresupuesto = '"+ tipo + "' ORDER by iniciopresupuesto DESC ", null);
        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{

                int id = cursor.getInt(cursor.getColumnIndexOrThrow("idpresupuesto"));
                String tipopresupuesto = cursor.getString(cursor.getColumnIndexOrThrow("tipopresupuesto"));
                float saldo = cursor.getFloat(cursor.getColumnIndexOrThrow("saldo"));
                String iniciopresupuesto = cursor.getString(cursor.getColumnIndexOrThrow("iniciopresupuesto"));
                String finpresupuesto = cursor.getString(cursor.getColumnIndexOrThrow("finpresupuesto"));
                float meta = cursor.getFloat(cursor.getColumnIndexOrThrow("meta"));
                int idusuario = cursor.getInt(cursor.getColumnIndexOrThrow("idusuario"));

                Presupuesto presupuesto = new Presupuesto(id, tipopresupuesto,saldo,iniciopresupuesto, finpresupuesto, meta, idusuario);

                listapresupuesto.add(presupuesto);
            }while (cursor.moveToNext());
        }


        return listapresupuesto;
    }
    public ArrayList<Presupuesto> ConsultarPresupuesto2( int idUsuario) throws ParseException {
        ArrayList<Presupuesto> listapresupuesto = new ArrayList<Presupuesto>();
        Cursor cursor = BD.rawQuery("SELECT idpresupuesto, tipopresupuesto, saldo, iniciopresupuesto, finpresupuesto, meta, idusuario FROM presupuesto where idusuario = '"+idUsuario+"' ORDER by iniciopresupuesto DESC ", null);
        if(cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{

                int id = cursor.getInt(cursor.getColumnIndexOrThrow("idpresupuesto"));
                String tipopresupuesto = cursor.getString(cursor.getColumnIndexOrThrow("tipopresupuesto"));
                float saldo = cursor.getFloat(cursor.getColumnIndexOrThrow("saldo"));
                String iniciopresupuesto = cursor.getString(cursor.getColumnIndexOrThrow("iniciopresupuesto"));
                String finpresupuesto = cursor.getString(cursor.getColumnIndexOrThrow("finpresupuesto"));
                float meta = cursor.getFloat(cursor.getColumnIndexOrThrow("meta"));
                int idusuario = cursor.getInt(cursor.getColumnIndexOrThrow("idusuario"));

                Presupuesto presupuesto = new Presupuesto(id, tipopresupuesto,saldo,iniciopresupuesto, finpresupuesto, meta, idusuario);

                listapresupuesto.add(presupuesto);
            }while (cursor.moveToNext());
        }


        return listapresupuesto;
    }

    // Modificar presupuesto
    // Buscar presupuesto
    public Presupuesto buscarPresupuesto (int idPresupuesto){
        Presupuesto presupuesto = new Presupuesto();
        Cursor cursor = BD.rawQuery("SELECT idpresupuesto, tipopresupuesto, saldo, iniciopresupuesto, finpresupuesto, meta, idusuario FROM presupuesto where idpresupuesto = '"+idPresupuesto+"'", null);
        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do {

                String tipoPresupuesto = cursor.getString(cursor.getColumnIndexOrThrow("tipopresupuesto"));
                float saldo = cursor.getFloat(cursor.getColumnIndexOrThrow("saldo"));
                String iniciopresupuesto = cursor.getString(cursor.getColumnIndexOrThrow("iniciopresupuesto"));
                String finpresupuesto = cursor.getString(cursor.getColumnIndexOrThrow("finpresupuesto"));
                float meta = cursor.getFloat(cursor.getColumnIndexOrThrow("meta"));
                int idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("idusuario"));

                presupuesto.setId(idPresupuesto);
                presupuesto.setTipoPresupuesto(tipoPresupuesto);
                presupuesto.setSaldo(saldo);
                presupuesto.setInicioPresupuesto(iniciopresupuesto);
                presupuesto.setFinPresupuesto(finpresupuesto);
                presupuesto.setMeta(meta);
                presupuesto.setIdUsuario(idUsuario);

            }while (cursor.moveToNext());
        }
        return presupuesto;
    }

    public boolean modificarPresupuesto(int idPresupuesto, String tipopresupuesto, float saldo, String iniciopresupuesto, String finpresupuesto, float meta){
        String id = Integer.toString(idPresupuesto);//Revisar
        try {
            if (!tipopresupuesto.isEmpty() && saldo!=0 && !iniciopresupuesto.isEmpty() && !finpresupuesto.isEmpty() && meta!=0){ //Modificar todos los datos
                ContentValues cv = new ContentValues();
                cv.put("tipopresupuesto", tipopresupuesto);
                cv.put("saldo", saldo);
                cv.put("iniciopresupuesto", iniciopresupuesto);
                cv.put("finpresupuesto", finpresupuesto);
                cv.put("meta", meta);

                BD.update("presupuesto", cv, "idpresupuesto = ?", new String[]{id});
                return true;
            }
            return false;
        } catch (Exception e){
            return false;
        }
    }

    public boolean restarGasto(float precio, int idPresupuesto){
        // Primero obtener el saldo
        float saldoNeto = 0;
        Cursor cursor = BD.rawQuery("SELECT saldo FROM presupuesto where idpresupuesto = '"+idPresupuesto+"'", null);
        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                saldoNeto = cursor.getFloat(cursor.getColumnIndexOrThrow("saldo"));

            }while (cursor.moveToNext());
        }

        // Segundo, restar el precio del gasto entrante
        String id = Integer.toString(idPresupuesto);
        try{
            float nuevoSaldo = saldoNeto - precio;
            if (nuevoSaldo <= 0){
                return false;
            }
            if (nuevoSaldo > 0){
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

}
