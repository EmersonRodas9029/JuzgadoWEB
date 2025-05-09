package com.juzgado.gestor_expedientes.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "expedientes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Expediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    private String descripcion;

    @Column(nullable = false)
    private LocalDate fecha;

    private String ubicacionFisica;

    @ManyToOne
    @JoinColumn(name = "usuario_creador_id")
    private Usuario usuarioCreador;
}
