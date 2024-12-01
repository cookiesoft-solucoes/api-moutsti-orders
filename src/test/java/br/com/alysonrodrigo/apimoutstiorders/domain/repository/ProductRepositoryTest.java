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
@Sql(scripts = "/sql/insert_categories.sql")
public class ProductRepositoryTest {

    @Autowired
    private RepProductRepository productRepository;

    @Test
    public void testSaveAndFindProduct() {
        // Criando uma categoria
        RepCategory category = new RepCategory();
        category.setId(1L); // O ID deve corresponder a um registro existente no banco
        category.setName("Eletronicos");
        category.setDescription("Produtos eletronicos de teste");
        category.setDeleted(false);

        // Criando o produto
        RepProduct product = new RepProduct();
        product.setId(1L);
        product.setName("Smartphone");
        product.setPrice(new BigDecimal("1500.00"));
        product.setQuantity(10);
        product.setCategory(category);

        // Salvando o produto
        product = productRepository.save(product);

        // Verificando se o produto foi salvo corretamente
        assertThat(product.getId()).isNotNull();

        // Buscando produto por ID
        Optional<RepProduct> foundProduct = productRepository.findById(product.getId());
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Smartphone");
    }
}
