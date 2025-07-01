package com.example.tcgstore.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class CarritoProductoId implements Serializable {

    private Integer idCarrito;
    private Integer idProducto;
}
