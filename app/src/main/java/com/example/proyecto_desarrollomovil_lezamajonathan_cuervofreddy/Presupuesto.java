package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import java.util.Date;

public class Presupuesto {
    private int id;
    private String tipoPresupuesto;
    private float saldo;
    private String inicioPresupuesto;
    private String finPresupuesto;
    private float meta;
    private int idUsuario;

    public Presupuesto(int id, String tipoPresupuesto, float saldo, String inicioPresupuesto, String finPresupuesto, float meta, int idUsuario) {
        this.id = id;
        this.tipoPresupuesto = tipoPresupuesto;
        this.saldo = saldo;
        this.inicioPresupuesto = inicioPresupuesto;
        this.finPresupuesto = finPresupuesto;
        this.meta = meta;
        this.idUsuario = idUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoPresupuesto() {
        return tipoPresupuesto;
    }

    public void setTipoPresupuesto(String tipoPresupuesto) {
        this.tipoPresupuesto = tipoPresupuesto;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public String getInicioPresupuesto() {
        return inicioPresupuesto;
    }

    public void setInicioPresupuesto(String inicioPresupuesto) {
        this.inicioPresupuesto = inicioPresupuesto;
    }

    public String getFinPresupuesto() {
        return finPresupuesto;
    }

    public void setFinPresupuesto(String finPresupuesto) {
        this.finPresupuesto = finPresupuesto;
    }

    public float getMeta() {
        return meta;
    }

    public void setMeta(float meta) {
        this.meta = meta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
