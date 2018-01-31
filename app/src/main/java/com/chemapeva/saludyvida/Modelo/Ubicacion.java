package com.chemapeva.saludyvida.Modelo;

/**
 * Created by crist on 11/01/2018.
 */

public class Ubicacion {
    private String codigo;
    private String direccion;
    private String sector;
    private double vectorx;
    private double vectory;
    private Cliente cliente;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public double getVectorx() {
        return vectorx;
    }

    public void setVectorx(double vectorx) {
        this.vectorx = vectorx;
    }

    public double getVectory() {
        return vectory;
    }

    public void setVectory(double vectory) {
        this.vectory = vectory;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Ubicacion{" +
                "codigo='" + codigo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", sector='" + sector + '\'' +
                ", vectorx=" + vectorx +
                ", vectory=" + vectory +
                ", cliente=" + cliente +
                '}';
    }
}
