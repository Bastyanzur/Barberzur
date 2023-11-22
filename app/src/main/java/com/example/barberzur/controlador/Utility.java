package com.example.barberzur.controlador;

public class Utility {


        public static final String TABLA_USUARIO = "usuario";
        public static final String CAMPO_NOMBRE = "nombre";
        public static final String CAMPO_EDAD = "edad";
        public static final String CAMPO_CORREO = "correo";
        public static final String CAMPO_CONTRASENA = "contrasena";

        public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " +
                TABLA_USUARIO + " (" +
                CAMPO_NOMBRE + " TEXT, " +
                CAMPO_EDAD + " INTEGER, " +
                CAMPO_CORREO + " TEXT, " +
                CAMPO_CONTRASENA + " TEXT);";
        }

