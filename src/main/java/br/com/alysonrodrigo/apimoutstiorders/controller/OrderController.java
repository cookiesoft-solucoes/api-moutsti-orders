package br.com.alysonrodrigo.apimoutstiorders.controller;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.Order;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.OrderService;
import br.com.alysonrodrigo.apimoutstiorders.dto.OrderCreateDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.OrderDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.OrderProcessingResponseDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepCategoryDTO;
import br.com.alysonrodrigo.apimoutstiorders.mapper.OrderMapper;
import br.com.alysonrodrigo.apimoutstiorders.producer.CategorySyncProducer;
import br.com.alysonrodrigo.apimoutstiorders.producer.OrderProducer;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderProducer orderProducer;

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    public OrderController(OrderProducer orderProducer,
                           OrderService orderService,
                           OrderMapper orderMapper) {
        this.orderProducer = orderProducer;
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    /**
     * Endpoint para enviar um evento de sincronização de usuário.
     *
     * @param orderCreateDTO Dados do usuário a ser sincronizado.
     * @return Resposta indicando que o evento foi enviado.
     */
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<OrderProcessingResponseDTO> processOrder(@RequestBody OrderCreateDTO orderCreateDTO) {
        LocalDateTime processingStart = LocalDateTime.now();

        // Verificar se o pedido com o código já existe
        boolean orderExists = orderService.existsByCode(orderCreateDTO.getCode());
        if (orderExists) {
            OrderProcessingResponseDTO existingOrderResponse = new OrderProcessingResponseDTO();
            existingOrderResponse.setProcessingStart(processingStart);
            existingOrderResponse.setStatus("JÁ PROCESSADO");
            existingOrderResponse.setMessage("Já existe um pedido com o código fornecido. Verifique os detalhes na área de pedidos.");
            return ResponseEntity.badRequest().body(existingOrderResponse);
        }

        orderProducer.sendOrderToProcess(orderCreateDTO);

        OrderProcessingResponseDTO responseDTO = new OrderProcessingResponseDTO();
        responseDTO.setProcessingStart(processingStart);
        responseDTO.setStatus("PROCESSANDO");
        responseDTO.setMessage("Você receberá detalhes do pedido por email ou poderá consultar o status na área de pedidos.");


        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Endpoint para consultar um pedido pelo código.
     *
     * @param code Código do pedido.
     * @return Detalhes do pedido.
     */
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/{code}")
    public ResponseEntity<OrderDTO> getOrderByCode(@PathVariable String code) {
        Order order = orderService.findByCode(code);
        OrderDTO orderDTO = orderMapper.toDTO(order);
        return ResponseEntity.ok(orderDTO);
    }
}
