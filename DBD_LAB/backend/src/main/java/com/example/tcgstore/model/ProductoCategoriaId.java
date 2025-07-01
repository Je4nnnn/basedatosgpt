package com.example.tcgstore.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class ProductoCategoriaId implements Serializable {

    private Integer idProducto;
    private Integer idCategoria;
}
