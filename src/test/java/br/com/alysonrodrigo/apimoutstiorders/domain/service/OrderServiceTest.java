package br.com.alysonrodrigo.apimoutstiorders.domain.service;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.*;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.OrderRepository;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.RepProductRepository;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.RepUserRepository;
import br.com.alysonrodrigo.apimoutstiorders.dto.ItemOrderDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.OrderCreateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RepProductRepository repProductRepository;

    @Mock
    private RepUserRepository userRepository;

    @Mock
    private TaxService taxService;

    @InjectMocks
    private OrderService orderService;

    @Mock
    private RepUserService repUserService;

    @Mock
    private RepProductService repProductService;

    @Mock
    private ItemOrderService itemOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock da Categoria
        RepCategory category = new RepCategory();
        category.setId(1L);
        category.setName("Eletrônicos");
        category.setDescription("Produtos eletrônicos de teste");

        // Mock do usuário
        RepUser user = new RepUser();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("testuser@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(repUserService.findById(1L)).thenReturn(user); // Mockando o serviço

        // Mock do produto
        RepProduct product = new RepProduct();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(new BigDecimal("100.00"));
        product.setCategory(category); // Associando a categoria ao produto

        when(repProductRepository.findById(1L)).thenReturn(Optional.of(product));
        when(repProductService.getProductById(1L)).thenReturn(product);

        // Mock da taxa
        Tax tax = new Tax();
        tax.setId(1L);
        tax.setRate(new BigDecimal("10.00"));
        tax.setCategory(category); // Associando a categoria à taxa

        when(taxService.getFirstTaxByCategory(anyLong())).thenReturn(tax);

        // Mock do ItemOrderService.save
        ItemOrder itemOrder = new ItemOrder();
        itemOrder.setId(1L);
        itemOrder.setProduct(product);
        itemOrder.setQuantity(2);
        itemOrder.setPrice(product.getPrice().multiply(BigDecimal.valueOf(2)));
        itemOrder.setTax(tax);

        when(itemOrderService.save(any(ItemOrder.class))).thenReturn(itemOrder);

        // Mock do repositório de pedidos
        Order order = new Order();
        order.setCode("ORDER123");
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    void testCreateOrder() {
        // Criação do DTO
        OrderCreateDTO.ItemOrderDTO itemOrderDTO = new OrderCreateDTO.ItemOrderDTO();
        itemOrderDTO.setProductId(1L);
        itemOrderDTO.setPrice(BigDecimal.valueOf(1000));
        itemOrderDTO.setQuantity(2);

        OrderCreateDTO orderCreateDTO = new OrderCreateDTO();
        orderCreateDTO.setCode("ORDER123");
        orderCreateDTO.setClientId(1L);
        orderCreateDTO.setItems(List.of(itemOrderDTO));

        // Executa o método
        orderService.createOrder(orderCreateDTO);

        // Verifica chamadas nos mocks
        //verify(userRepository, times(1)).findById(1L);
        //verify(repProductRepository, times(1)).findById(1L);
        verify(taxService, times(1)).getFirstTaxByCategory(anyLong());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(itemOrderService, times(1)).save(any(ItemOrder.class));
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
