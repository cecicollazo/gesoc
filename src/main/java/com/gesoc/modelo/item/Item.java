package com.gesoc.modelo.item;

import javax.persistence.*;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Producto producto;
    private String nombre;
    private Integer precioTotal;
    private Integer cantidad;

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecioTotal(Integer precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    //para test
    // TODO: validaciones de null
    public Item(final String nombre, final Integer precioTotal) {
        this.precioTotal = precioTotal;
        this.nombre = nombre;
    }

    public Item(final String nombre, final Producto producto, final Integer precioTotal, final Integer cantidad) {
        this.precioTotal = precioTotal;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public Integer getPrecioTotal() {
        return precioTotal * cantidad;
    }

    @Override
    public String toString() {
        return "Item{"
                + "producto=" + producto
                + ", nombre='" + nombre + '\''
                + ", precio=" + precioTotal
                + ", cantidad=" + cantidad
                + '}';
    }
    
    public Integer getPrecio() {
        return precioTotal;
    }

}
