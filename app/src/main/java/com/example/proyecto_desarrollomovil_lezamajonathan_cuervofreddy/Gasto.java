package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

public class Gasto {
    private int idGasto;
    private String tipoGasto;
    private String categoria;
    private String comentario;
    private String fecha;
    private float precio;
    private int idUsuario;
    private int idPresupuesto;

    public Gasto(int idGasto, String tipoGasto, String categoria, String comentario, String fecha, float precio, int idUsuario, int idPresupuesto) {
        this.idGasto = idGasto;
        this.tipoGasto = tipoGasto;
        this.categoria = categoria;
        this.comentario = comentario;
        this.fecha = fecha;
        this.precio = precio;
        this.idUsuario = idUsuario;
        this.idPresupuesto = idPresupuesto;
    }

    public int getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(int idGasto) {
        this.idGasto = idGasto;
    }

    public String getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(String tipoGasto) {
        this.tipoGasto = tipoGasto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(int idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }
}
