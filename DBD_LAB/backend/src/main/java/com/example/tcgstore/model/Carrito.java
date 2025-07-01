package com.example.tcgstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "carrito")
public class Carrito {

    @Id
    private Integer idCarrito;

    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "id_boleta")
    private Boleta boleta;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
