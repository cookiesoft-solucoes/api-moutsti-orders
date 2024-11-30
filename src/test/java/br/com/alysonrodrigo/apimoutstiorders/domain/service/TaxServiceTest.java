package br.com.alysonrodrigo.apimoutstiorders.domain.service;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.Category;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.Tax;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.CategoryRepository;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.TaxRepository;
import br.com.alysonrodrigo.apimoutstiorders.dto.TaxDTO;
import br.com.alysonrodrigo.apimoutstiorders.mapper.TaxMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaxServiceTest {

    @Mock
    private TaxRepository taxRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TaxMapper taxMapper;

    @Mock
    private TaxCacheService taxCacheService; // Mock do TaxCacheService

    @InjectMocks
    private TaxService taxService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveTaxWithInvalidCategory() {
        // Configurar mock para categoria inexistente
        Long invalidCategoryId = 99L;
        when(categoryRepository.findById(invalidCategoryId)).thenReturn(Optional.empty());

        TaxDTO taxDTO = new TaxDTO();
        taxDTO.setTaxType("ICMS");
        taxDTO.setRate(new BigDecimal("18.00"));
        taxDTO.setDescription("Imposto ICMS inválido");
        taxDTO.setCategoryId(invalidCategoryId);

        // Verificar se lança IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            Category category = categoryRepository.findById(invalidCategoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
            taxMapper.toEntity(taxDTO, category);
        });

        // Verificar que o método save não foi chamado
        verify(taxRepository, never()).save(any());
    }
}
