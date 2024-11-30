package br.com.alysonrodrigo.apimoutstiorders.domain.repository;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private RepUserRepository userRepository;

    @Test
    public void testSaveAndFindUser() {
        // Criando uma categoria
        RepUser repUser = new RepUser();
        repUser.setId(1L); // O ID deve corresponder a um registro existente no banco
        repUser.setName("Alyson Rodrigo");
        repUser.setEmail("alyson@gmail.com");
        repUser.setDeleted(false);

        // Salvando a category
        repUser = userRepository.save(repUser);

        // Verificando se o produto foi salvo corretamente
        assertThat(repUser.getId()).isNotNull();

        // Buscando produto por ID
        Optional<RepUser> foundUser = userRepository.findById(repUser.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("Alyson Rodrigo");
        assertThat(foundUser.get().getEmail()).isEqualTo("alyson@gmail.com");
    }
}
