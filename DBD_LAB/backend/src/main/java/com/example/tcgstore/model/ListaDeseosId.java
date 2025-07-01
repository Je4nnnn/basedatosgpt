package com.example.tcgstore.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class ListaDeseosId implements Serializable {

    private Integer usuarioId;
    private Integer productoId;
}
