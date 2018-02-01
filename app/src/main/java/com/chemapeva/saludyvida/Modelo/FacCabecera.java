package com.chemapeva.saludyvida.Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by crist on 31/01/2018.
 */

public class FacCabecera {
   private int codigo;
   private int numero_factura;
   private Date fechaEmision;
   private double subtotal;
   private double iva;
   private double total;
   private Cliente cliente;
   private List<FacturaDetalle> detalles;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getNumero_factura() {
        return numero_factura;
    }

    public void setNumero_factura(int numero_factura) {
        this.numero_factura = numero_factura;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<FacturaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<FacturaDetalle> detalles) {
        this.detalles = detalles;
    }
    public void addDetalle(FacturaDetalle detalle) {
        if(detalles==null)
            detalles=new ArrayList<>();
        detalles.add(detalle);
    }
    @Override
    public String toString() {
        return "FacCabecera{" +
                "codigo=" + codigo +
                ", numero_factura=" + numero_factura +
                ", fechaEmision=" + fechaEmision +
                ", subtotal=" + subtotal +
                ", iva=" + iva +
                ", total=" + total +
                ", cliente=" + cliente +
                ", detalles=" + detalles +
                '}';
    }
}
