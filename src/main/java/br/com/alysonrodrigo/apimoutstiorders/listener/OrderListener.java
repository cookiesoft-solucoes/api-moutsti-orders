package br.com.alysonrodrigo.apimoutstiorders.listener;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.OrderService;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.RepCategoryService;
import br.com.alysonrodrigo.apimoutstiorders.dto.OrderCreateDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepCategoryDTO;
import br.com.alysonrodrigo.apimoutstiorders.util.ConstantsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class OrderListener {

    private final OrderService orderService;

    public OrderListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = ConstantsUtil.ORDER_SYNC_QUEUE)
    public void handleProcessOrder(OrderCreateDTO orderCreateDTO) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Obtendo os dados necessários
        String orderCode = orderCreateDTO.getCode();
        String startedProcessingTime = LocalDateTime.now().format(formatter);

        System.out.println("Order processing success! Code: " + orderCode + ", started at: " + startedProcessingTime);

        this.orderService.createOrder(orderCreateDTO);

        String endedProcessingTime = LocalDateTime.now().format(formatter);

        System.out.println("Order processed with success! Code: " + orderCode + ", completed at: " + endedProcessingTime);



    }
}
