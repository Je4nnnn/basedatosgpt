package com.example.tcgstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "boleta")
public class Boleta {

    @Id
    private Integer idBoleta;
    private String metodoPago;
    private Integer total;
    private java.sql.Date fecha;
}
