package com.chemapeva.saludyvida.Modelo;

/**
 * Created by crist on 31/01/2018.
 */

public class FacturaDetalle {

    private int codigo;
    private int cantidad;
    private double subtotal;
    private Menu menu;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "FacturaDetalle{" +
                "codigo=" + codigo +
                ", cantidad=" + cantidad +
                ", subtotal=" + subtotal +
                ", menu=" + menu +
                '}';
    }
}
