package br.com.alysonrodrigo.apimoutstiorders.domain.repository;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.Category;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.Product;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.Tax;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = "/sql/insert_categories.sql")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveAndFindProduct() {
        // Criando uma categoria
        Category category = new Category();
        category.setId(1L); // O ID deve corresponder a um registro existente no banco
        category.setName("Eletrônicos");
        category.setDescription("Produtos eletrônicos de teste");
        category.setDeleted(false);

        // Criando o produto
        Product product = new Product();
        product.setName("Smartphone");
        product.setPrice(new BigDecimal("1500.00"));
        product.setQuantity(10);
        product.setCategory(category);

        // Salvando o produto
        product = productRepository.save(product);

        // Verificando se o produto foi salvo corretamente
        assertThat(product.getId()).isNotNull();

        // Buscando produto por ID
        Optional<Product> foundProduct = productRepository.findById(product.getId());
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Smartphone");
        assertThat(foundProduct.get().getCategory().getName()).isEqualTo("Eletrônicos");
    }
}
