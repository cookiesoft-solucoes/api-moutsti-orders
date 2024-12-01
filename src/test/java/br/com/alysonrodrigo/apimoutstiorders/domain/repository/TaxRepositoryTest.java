package br.com.alysonrodrigo.apimoutstiorders.domain.repository;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.Tax;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = "/sql/insert_categories.sql")
public class TaxRepositoryTest {

    @Autowired
    private TaxRepository taxRepository;

    @Test
    public void testSaveAndFindTax() {
        // Criação do imposto
        Tax tax = new Tax();
        tax.setTaxType("ICMS");
        tax.setRate(new BigDecimal("18.00"));
        tax.setDescription("Imposto ICMS para teste");
        tax.setCategory(new RepCategory(1L, "Eletrônicos", "Descrição de Eletrônicos")); // Categoria existente

        // Salvando imposto
        tax = taxRepository.save(tax);

        // Verificando se foi salvo corretamente
        assertThat(tax.getId()).isNotNull();

        // Buscando impostos por categoria
        List<Tax> taxes = taxRepository.findByCategoryId(1L);
        assertThat(taxes).isNotEmpty();
    }
}
