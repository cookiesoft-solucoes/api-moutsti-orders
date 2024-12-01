package br.com.alysonrodrigo.apimoutstiorders.controller;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepProduct;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.RepCategoryService;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.RepProductService;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepCategoryDTO;
import br.com.alysonrodrigo.apimoutstiorders.mapper.CategoryMapper;
import br.com.alysonrodrigo.apimoutstiorders.mapper.ProductMapper;
import br.com.alysonrodrigo.apimoutstiorders.producer.CategorySyncProducer;
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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategorySyncController.class)
@ActiveProfiles("test")
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategorySyncProducer productSyncProducer;

    @MockBean
    private RepCategoryService categoryService;

    @MockBean
    private CategoryMapper productMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public CategoryControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() throws Exception {

        RepCategory category = new RepCategory();
        category.setId(1L);
        category.setName("Test Category");
        category.setDescription("Description Test Category");

        RepCategoryDTO categoryDTO = new RepCategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Test Category");
        categoryDTO.setDescription("Description Test Category");

        when(categoryService.save(any(RepCategory.class))).thenReturn(category);

        mockMvc.perform(post("/categories/sync")
                        .with(httpBasic("manager", "manager123")) // Credenciais
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void testGetAllProducts() throws Exception {
        RepProduct product = new RepProduct();
        product.setName("Test Product");

        mockMvc.perform(get("/categories")
                        .with(httpBasic("manager", "manager123")) // Credenciais
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }
}