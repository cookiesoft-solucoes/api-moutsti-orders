package br.com.alysonrodrigo.apimoutstiorders.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "rep_user")
public class RepUser extends BaseEntity{

    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String email;
}
