package com.chemapeva.saludyvida.Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by crist on 11/01/2018.
 */

public class Cliente {

    private Integer codigo;
    private String cedula;
    private String nombres;
    private String usuario;
    private String contrasena;
    private String email;
    private String fechaNacimiento;
    private ArrayList<Ubicacion> ubicaciones;
    private ArrayList<MenuTemp> menus;

    public ArrayList<Ubicacion> getUbicacion() {
        return ubicaciones;
    }

    public void setUbicacion(ArrayList<Ubicacion> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public ArrayList<Ubicacion> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(ArrayList<Ubicacion> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public ArrayList<MenuTemp> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<MenuTemp> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "codigo=" + codigo +
                ", cedula='" + cedula + '\'' +
                ", nombres='" + nombres + '\'' +
                ", usuario='" + usuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", email='" + email + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", ubicaciones=" + ubicaciones +
                ", menus=" + menus +
                '}';
    }
}
