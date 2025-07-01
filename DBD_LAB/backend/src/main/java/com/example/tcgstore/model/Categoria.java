package com.example.tcgstore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "categoria")
public class Categoria {

    @Id
    private Integer idCategoria;

    private String genero;
}
