package com.example.proyecto_desarrollomovil_lezamajonathan_cuervofreddy;

public class Usuario {

    public int id;
    public String nombre = "";
    public String email ="";
    public String password = "";


    public Usuario( int id, String nombre, String email, String password){
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public Usuario() {
        this.id = 0;
        this.nombre = "";
        this.email = "";
        this.password = "";
    }

    public Usuario(Usuario user){
        this.id = user.getId();
        this.nombre = user.getNombre();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
