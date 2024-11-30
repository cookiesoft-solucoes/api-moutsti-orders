package br.com.alysonrodrigo.apimoutstiorders.controller;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.Category;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.Tax;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.TaxRepository;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.CategoryService;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.TaxService;
import br.com.alysonrodrigo.apimoutstiorders.dto.TaxDTO;
import br.com.alysonrodrigo.apimoutstiorders.mapper.TaxMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;


@WebMvcTest(TaxController.class)
@ActiveProfiles("test")
public class TaxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaxService taxService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private TaxMapper taxMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public TaxControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTax() throws Exception {
        // Mock do TaxDTO
        TaxDTO taxDTO = new TaxDTO();
        taxDTO.setTaxType("ICMS");
        taxDTO.setCategoryId(1L);
        taxDTO.setRate(new BigDecimal("18.00"));
        taxDTO.setDescription("Imposto ICMS para teste");

        // Mock do retorno do método saveTax
        Tax tax = new Tax(); // Simule o retorno esperado
        tax.setTaxType("ICMS");
        tax.setCategory(new Category(1L, "Category Test", "Description Category Test")); // Exemplo de categoria mockada
        tax.setRate(new BigDecimal("18.00"));
        tax.setDescription("Imposto ICMS para teste");

        // Simulação do comportamento
        when(taxService.saveTax(any(Tax.class))).thenReturn(tax);

        // Testando o endpoint
        mockMvc.perform(post("/taxes")
                        .with(httpBasic("manager", "manager123")) // Credenciais
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taxDTO)))
                .andExpect(status().isForbidden());

    }
}
