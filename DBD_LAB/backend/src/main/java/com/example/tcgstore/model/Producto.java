package com.example.tcgstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "producto")
public class Producto {

    @Id
    private Integer id;

    private String nombre;
    private Double precio;
    private Integer stock;
    private String tipo;
    private String urlImagen;

    @ManyToOne
    @JoinColumn(name = "tienda_id")
    private Tienda tienda;
}
