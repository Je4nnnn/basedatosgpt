package com.example.tcgstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "producto_x_categoria")
@IdClass(ProductoCategoriaId.class)
public class ProductoCategoria {

    @Id
    private Integer idProducto;

    @Id
    private Integer idCategoria;
}
