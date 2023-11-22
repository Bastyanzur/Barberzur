package com.example.barberzur.Modelo;

public class Usuario {

    private String nombre;
    private String correo;
    private String edad;
    private String contrasena;

    public Usuario(String nombre, String correo, String edad, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", edad='" + edad + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}
