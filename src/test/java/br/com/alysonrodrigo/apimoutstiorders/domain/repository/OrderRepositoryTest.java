package br.com.alysonrodrigo.apimoutstiorders.domain.repository;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RepUserRepository userRepository;

    @Autowired
    private RepProductRepository productRepository;

    @Autowired
    private TaxRepository taxRepository;

    @Test
    public void testSaveAndFindOrder() {
        // Criando um usuário
        RepUser user = new RepUser();
        user.setId(1L); // Supondo que o ID corresponda a um registro existente
        user.setName("João Teste");
        user.setEmail("joao@example.com");
        user = userRepository.save(user);

        // Criando um produto
        RepProduct product = new RepProduct();
        product.setId(1L); // Supondo que o ID corresponda a um registro existente
        product.setName("Produto Teste");
        product.setPrice(new BigDecimal("100.00"));
        product.setQuantity(10);
        product = productRepository.save(product);

        // Criando uma taxa
        Tax tax = new Tax();
        tax.setTaxType("ICMS");
        tax.setRate(new BigDecimal("18.00"));
        tax.setDescription("Taxa de ICMS");
        tax = taxRepository.save(tax);

        // Criando um pedido
        Order order = new Order();
        order.setCode("ORD123");
        order.setDate(LocalDateTime.now());
        order.setQuantity(1);
        order.setTotal(new BigDecimal("100.00"));
        order.setTotalTax(new BigDecimal("18.00"));
        order.setClient(user);
        order.setStatus("CREATED");

        // Criando item do pedido
        ItemOrder itemOrder = new ItemOrder();
        itemOrder.setProduct(product);
        itemOrder.setQuantity(1);
        itemOrder.setPrice(product.getPrice());
        itemOrder.setTax(tax);
        itemOrder.setOrder(order);

        order = orderRepository.save(order);

        // Verificar se o pedido foi salvo corretamente
        assertThat(order.getId()).isNotNull();
        assertThat(order.getCode()).isEqualTo("ORD123");
        assertThat(order.getClient()).isEqualTo(user);

        // Buscando pedido por ID
        Optional<Order> foundOrder = orderRepository.findById(order.getId());
        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getCode()).isEqualTo("ORD123");
    }
}
