package br.com.alysonrodrigo.apimoutstiorders.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users_id")
    @SequenceGenerator(name = "seq_users_id", sequenceName = "seq_users_id", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "idAuthentication", nullable = false, length = 255)
    private String idAuthentication;
}
