package br.com.alysonrodrigo.apimoutstiorders.domain.repository;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
