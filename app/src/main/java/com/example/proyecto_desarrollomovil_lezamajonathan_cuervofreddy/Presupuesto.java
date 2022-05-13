package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

import java.util.Date;

public class Presupuesto {
    private int id;
    private String tipoPresupuesto;
    private float saldo;
    private Date inicioPresupuesto;
    private Date finPresupuesto;
    private float meta;
    private int idUsuario;

    public Presupuesto(int id, String tipoPresupuesto, float saldo, Date inicioPresupuesto, Date finPresupuesto, float meta, int idUsuario) {
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

    public Date getInicioPresupuesto() {
        return inicioPresupuesto;
    }

    public void setInicioPresupuesto(Date inicioPresupuesto) {
        this.inicioPresupuesto = inicioPresupuesto;
    }

    public Date getFinPresupuesto() {
        return finPresupuesto;
    }

    public void setFinPresupuesto(Date finPresupuesto) {
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
