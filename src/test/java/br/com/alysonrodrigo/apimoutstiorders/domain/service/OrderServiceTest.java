package br.com.alysonrodrigo.apimoutstiorders.domain.service;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.ItemOrder;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.Order;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepProduct;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.Tax;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.OrderRepository;
import br.com.alysonrodrigo.apimoutstiorders.dto.OrderCreateDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    public OrderServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {
        OrderCreateDTO orderDTO = new OrderCreateDTO();
        orderDTO.setCode("ORD123");
        orderDTO.setClientId(1L);

        Order order = new Order();
        order.setId(1L);
        order.setCode("ORD123");
        order.setTotal(BigDecimal.valueOf(100.00));
        order.setTotalTax(BigDecimal.valueOf(18.00));
        order.setStatus("CREATED");

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrder(orderDTO);

        assertThat(createdOrder).isNotNull();
        assertThat(createdOrder.getCode()).isEqualTo(orderDTO.getCode());

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testCalculateOrderTotal() {
        // Mockando produtos
        RepProduct product1 = new RepProduct();
        product1.setPrice(new BigDecimal("100.00")); // Preço do produto 1

        RepProduct product2 = new RepProduct();
        product2.setPrice(new BigDecimal("200.00")); // Preço do produto 2

        // Criando itens do pedido com quantidade e preços
        ItemOrder item1 = new ItemOrder();
        item1.setProduct(product1);
        item1.setQuantity(2); // Quantidade 2
        item1.setPrice(product1.getPrice().multiply(BigDecimal.valueOf(item1.getQuantity()))); // 100 * 2 = 200

        ItemOrder item2 = new ItemOrder();
        item2.setProduct(product2);
        item2.setQuantity(1); // Quantidade 1
        item2.setPrice(product2.getPrice().multiply(BigDecimal.valueOf(item2.getQuantity()))); // 200 * 1 = 200

        // Criando a lista de itens
        List<ItemOrder> items = List.of(item1, item2);

        // Calculando o total do pedido
        BigDecimal totalAmount = orderService.calculateOrderTotal(items);

        // Verificando o total esperado
        assertThat(totalAmount).isEqualTo(new BigDecimal("400.00")); // Total esperado: 200 + 200
    }

    @Test
    void testCalculateOrderTax() {
        // Mockando produtos e impostos
        RepProduct product1 = new RepProduct();
        product1.setPrice(new BigDecimal("100.00"));

        RepProduct product2 = new RepProduct();
        product2.setPrice(new BigDecimal("200.00"));

        Tax tax1 = new Tax();
        tax1.setRate(new BigDecimal("10.00")); // Taxa de 10%

        Tax tax2 = new Tax();
        tax2.setRate(new BigDecimal("15.00")); // Taxa de 15%

        // Criando itens do pedido
        ItemOrder item1 = new ItemOrder();
        item1.setProduct(product1);
        item1.setQuantity(2); // Quantidade 2
        item1.setPrice(product1.getPrice().multiply(BigDecimal.valueOf(item1.getQuantity()))); // 100 * 2 = 200
        item1.setTax(tax1);

        ItemOrder item2 = new ItemOrder();
        item2.setProduct(product2);
        item2.setQuantity(1); // Quantidade 1
        item2.setPrice(product2.getPrice().multiply(BigDecimal.valueOf(item2.getQuantity()))); // 200 * 1 = 200
        item2.setTax(tax2);

        // Lista de itens do pedido
        List<ItemOrder> items = List.of(item1, item2);

        // Calculando o total do imposto
        BigDecimal expectedTax = item1.getPrice().multiply(tax1.getRate()).divide(BigDecimal.valueOf(100))
                .add(item2.getPrice().multiply(tax2.getRate()).divide(BigDecimal.valueOf(100)));

        BigDecimal actualTax = orderService.calculateOrderTax(items);

        // Verificando o imposto calculado
        assertThat(actualTax).isEqualByComparingTo(expectedTax);
    }
}
