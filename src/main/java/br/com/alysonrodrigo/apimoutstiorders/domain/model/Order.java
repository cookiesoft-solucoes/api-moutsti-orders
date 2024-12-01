package br.com.alysonrodrigo.apimoutstiorders.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "orders")
@Where(clause = "deleted = false")
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_order_id")
    @SequenceGenerator(name = "seq_order_id", sequenceName = "seq_order_id", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 50)
    private String code;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "total_tax", nullable = false, precision = 10, scale = 2, columnDefinition = "NUMERIC(10, 2) DEFAULT 0")
    private BigDecimal totalTax = BigDecimal.ZERO;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private RepUser client;

    @Column(nullable = false, length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'CREATED'")
    private String status = "CREATED";

    @OneToMany(mappedBy = "order")
    private List<ItemOrder> items;
}
