package br.com.alysonrodrigo.apimoutstiorders.controller;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepProduct;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.RepCategoryService;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.RepProductService;
import br.com.alysonrodrigo.apimoutstiorders.mapper.ProductMapper;
import br.com.alysonrodrigo.apimoutstiorders.producer.ProductSyncProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductSyncController.class)
@ActiveProfiles("test")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepCategoryService categoryService;

    @MockBean
    private ProductSyncProducer productSyncProducer;

    @MockBean
    private RepProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public ProductControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() throws Exception {
        RepProduct product = new RepProduct();
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(100));
        product.setQuantity(10);

        when(productService.save(any(RepProduct.class))).thenReturn(product);

        mockMvc.perform(post("/products/sync")
                        .with(httpBasic("manager", "manager123")) // Credenciais
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void testGetAllProducts() throws Exception {
        RepProduct product = new RepProduct();
        product.setName("Test Product");

        mockMvc.perform(get("/products")
                        .with(httpBasic("manager", "manager123")) // Credenciais
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }
}