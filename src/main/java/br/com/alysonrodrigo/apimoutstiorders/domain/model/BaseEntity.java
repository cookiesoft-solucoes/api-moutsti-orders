package br.com.alysonrodrigo.apimoutstiorders.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.Where;

@Data
@MappedSuperclass
@Where(clause = "deleted = false")
public class BaseEntity {

    @Column(nullable = false)
    private Boolean deleted = false;
}
