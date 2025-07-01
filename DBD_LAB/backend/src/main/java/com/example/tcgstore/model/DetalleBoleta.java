package com.example.tcgstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "detalle_boleta")
public class DetalleBoleta {

    @Id
    private Integer id;

    private Integer cantidad;
    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "boleta_id")
    private Boleta boleta;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
