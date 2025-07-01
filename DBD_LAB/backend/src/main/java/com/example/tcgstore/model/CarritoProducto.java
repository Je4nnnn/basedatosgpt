package com.example.tcgstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "carrito_x_producto")
@IdClass(CarritoProductoId.class)
public class CarritoProducto {

    @Id
    private Integer idCarrito;

    @Id
    private Integer idProducto;
}
