package br.com.alysonrodrigo.apimoutstiorders.controller;

import br.com.alysonrodrigo.apimoutstiorders.domain.service.OrderService;
import br.com.alysonrodrigo.apimoutstiorders.dto.OrderCreateDTO;
import br.com.alysonrodrigo.apimoutstiorders.mapper.OrderMapper;
import br.com.alysonrodrigo.apimoutstiorders.producer.OrderProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderProducer orderProducer;

    @MockBean
    private OrderMapper orderMapper;

    @Test
    public void testProcessOrder() throws Exception {
        OrderCreateDTO orderCreateDTO = new OrderCreateDTO();
        orderCreateDTO.setCode("ORD123");
        orderCreateDTO.setClientId(1L);
        orderCreateDTO.setItems(Collections.emptyList());

        when(orderService.createOrder(orderCreateDTO)).thenReturn(null);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderCreateDTO)))
                .andExpect(status().isForbidden());
    }
}
