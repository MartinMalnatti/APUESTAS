
package com.apuestasamistosas.app.errors;


public class ErrorProveedores extends Exception{

    public ErrorProveedores(String message){
        super(message);
    }
    
    /* Constantes de utilidad para servicios */
    
    public static final String PROV_EXISTS = "Este proveedor ya se encuentra registrado.";
    public static final String DIGIT_TEL = "Solo pueden ser numeros (max. 13)";
    public static final String NO_TEL = "Debe ingresar un numero de teléfono.";
    public static final String NO_NOMBRE = "El nombre no puede estar vacio";
    public static final String NO_RESP = "Debe ingresar un responsable.";
    public static final String NO_PROV = "Este proveedor no existe";
}
