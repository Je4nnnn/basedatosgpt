package com.example.tcgstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuario")
public class Usuario {

    @Id
    private Integer id;

    private String nombre;
    private String email;
    private String ubicacion;
    private String rol;

    @ManyToOne
    @JoinColumn(name = "tienda_id")
    private Tienda tienda;

    @ManyToOne
    @JoinColumn(name = "boleta_id")
    private Boleta boleta;
}
