package com.example.tcgstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "lista_deseos")
@IdClass(ListaDeseosId.class)
public class ListaDeseos {

    @Id
    private Integer usuarioId;

    @Id
    private Integer productoId;
}
