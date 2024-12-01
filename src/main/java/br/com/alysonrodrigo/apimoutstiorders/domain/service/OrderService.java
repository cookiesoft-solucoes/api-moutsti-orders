package br.com.alysonrodrigo.apimoutstiorders.domain.service;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.*;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.*;
import br.com.alysonrodrigo.apimoutstiorders.dto.OrderCreateDTO;
import br.com.alysonrodrigo.apimoutstiorders.exception.ResourceNotFoundException;
import br.com.alysonrodrigo.apimoutstiorders.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemOrderService itemOrderService;
    private final RepProductService repProductService;
    private final TaxService taxService;
    private final RepUserService userService;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository,
                        ItemOrderService itemOrderService,
                        RepProductService repProductService,
                        TaxService taxService,
                        RepUserService userService,
                        OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.itemOrderService = itemOrderService;
        this.repProductService = repProductService;
        this.taxService = taxService;
        this.userService = userService;
        this.orderMapper = orderMapper;
    }

    /**
     * Retorna todas as categorias.
     *
     * @return Lista de categorias.
     */
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrada com o ID: " + id));
    }

    public Order findOrderByCode(String code) {
        return orderRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com o código: " + code));
    }

    public List<Order> findOrdersByClientId(Long clientId) {
        return orderRepository.findByClientId(clientId);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }


    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public Order createOrder(OrderCreateDTO orderCreateDTO) {
        // Validar se o cliente existe
        RepUser user = this.userService.findById(orderCreateDTO.getClientId());

        // Montar Itens do pedido
        List<ItemOrder> itemOrders = this.createItemOrders(orderCreateDTO.getItems());

        // Calcular o total do pedido e a taxa total
        BigDecimal total = calculateOrderTotal(itemOrders);

        BigDecimal totalTax = calculateOrderTax(itemOrders);

        // Criar o objeto Order
        Order order = new Order();
        order.setCode(orderCreateDTO.getCode()); // Gerar um código único para o pedido
        order.setDate(LocalDateTime.now());
        order.setQuantity(itemOrders.size());
        order.setTotal(total);
        order.setTotalTax(totalTax);
        order.setClient(user);
        order.setStatus("CREATED");

        // Persistir a ordem (lógica de persistência pode ser adicionada aqui)
        this.orderRepository.save(order);

        // Vincular os itens ao pedido e persistir
        itemOrders.forEach(item -> {
            item.setOrder(order);
            this.itemOrderService.save(item);
        });

        return order;

    }

    private List<ItemOrder> createItemOrders(List<OrderCreateDTO.ItemOrderDTO> items) {

        List<ItemOrder> itemOrders = items.stream().map(itemDTO -> {
            // Validar o produto
            RepProduct product = this.repProductService.getProductById(itemDTO.getProductId());

            // Criar o objeto ItemOrder
            ItemOrder itemOrder = new ItemOrder();
            itemOrder.setProduct(product);
            itemOrder.setQuantity(itemDTO.getQuantity());
            itemOrder.setPrice(product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));

            // Adicionar lógica para impostos (se necessário)
            Tax tax = this.taxService.getFirstTaxByCategory(product.getCategory().getId());
            itemOrder.setTax(tax);

            return itemOrder;
        }).toList();

        return itemOrders;
    }

    public boolean existsByCode(String code) {
        return orderRepository.existsByCode(code);
    }

    public Order findByCode(String code) {
        return orderRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o código: " + code));
    }

    public BigDecimal calculateOrderTax(List<ItemOrder> itemOrders) {
        return itemOrders.stream()
                .map(item -> {
                    BigDecimal taxRate = item.getTax().getRate(); // Obtém a taxa de imposto
                    BigDecimal itemPrice = item.getPrice(); // Obtém o preço total do item
                    return itemPrice.multiply(taxRate).divide(BigDecimal.valueOf(100)); // Calcula o valor do imposto
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Soma todos os impostos
    }

    public BigDecimal calculateOrderTotal(List<ItemOrder> itemOrders) {
        return itemOrders.stream()
                .map(ItemOrder::getPrice) // Obtém o preço total de cada item
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Soma os preços para calcular o total do pedido
    }

}
