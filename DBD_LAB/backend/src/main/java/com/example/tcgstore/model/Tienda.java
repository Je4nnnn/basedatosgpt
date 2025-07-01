package com.example.tcgstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tienda")
public class Tienda {

    @Id
    private Integer idTienda;

    private String nombreTienda;
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "id_jefe")
    private Usuario jefe;
}
