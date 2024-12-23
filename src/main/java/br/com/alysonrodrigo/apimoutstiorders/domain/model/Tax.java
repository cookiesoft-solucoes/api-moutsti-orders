package br.com.alysonrodrigo.apimoutstiorders.domain.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Tax extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tax_id")
    @SequenceGenerator(name = "seq_tax_id", sequenceName = "seq_tax_id", allocationSize = 1)
    private Long id;

    @Column(name = "tax_type", nullable = false, length = 100)
    private String taxType;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private RepCategory category;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal rate;

    @Column(columnDefinition = "TEXT")
    private String description;
}
