package br.com.alysonrodrigo.apimoutstiorders.mapper;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.ItemOrder;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.Order;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.dto.ItemOrderDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.OrderDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepCategoryDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    /**
     * Converte um objeto Tax para TaxDTO.
     *
     * @param order Objeto Order a ser convertido.
     * @return Objeto OrderDTO correspondente.
     */
    public OrderDTO toDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCode(order.getCode());
        orderDTO.setQuantity(order.getQuantity());
        orderDTO.setDate(order.getDate());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setTotalTax(order.getTotalTax());
        orderDTO.setClientName(order.getClient().getName());
        orderDTO.setStatus(order.getStatus());

        orderDTO.setItems(order.getItems().stream().map(this::toItemOrderDTO).collect(Collectors.toList()));
        return orderDTO;
    }

    private ItemOrderDTO toItemOrderDTO(ItemOrder itemOrder) {
        ItemOrderDTO itemOrderDTO = new ItemOrderDTO();
        itemOrderDTO.setProductName(itemOrder.getProduct().getName());
        itemOrderDTO.setQuantity(itemOrder.getQuantity());
        itemOrderDTO.setPrice(itemOrder.getPrice());
        itemOrderDTO.setTaxType(itemOrder.getTax().getTaxType());
        return itemOrderDTO;
    }


}
