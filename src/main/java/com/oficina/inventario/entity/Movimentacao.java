package com.oficina.inventario.entity;

import com.oficina.inventario.enums.TipoMovimentacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoMovimentacao tipoMovimentacao;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private Integer quantidadeAnterior;

    @Column(nullable = false)
    private Integer quantidadeNova;

    @Column(length = 500)
    private String observacao;

    @Column(nullable = false, length = 100)
    private String responsavel;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataMovimentacao;
}
