package br.com.alysonrodrigo.apimoutstiorders.domain.repository;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CategoryRepositoryTest {

    @Autowired
    private RepCategoryRepository productRepository;

    @Test
    public void testSaveAndFindProduct() {
        // Criando uma categoria
        RepCategory category = new RepCategory();
        category.setId(1L); // O ID deve corresponder a um registro existente no banco
        category.setName("Eletrônicos");
        category.setDescription("Produtos eletrônicos de teste");
        category.setDeleted(false);

        // Salvando a category
        category = productRepository.save(category);

        // Verificando se o produto foi salvo corretamente
        assertThat(category.getId()).isNotNull();

        // Buscando produto por ID
        Optional<RepCategory> foundCategory = productRepository.findById(category.getId());
        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().getName()).isEqualTo("Eletrônicos");
        assertThat(foundCategory.get().getDescription()).isEqualTo("Produtos eletrônicos de teste");
    }
}
