package com.example.tcgstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "categoria_carta")
public class CategoriaCarta {

    @Id
    private Integer id;

    private String rareza;
    private String estado;
    private Integer anho;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
