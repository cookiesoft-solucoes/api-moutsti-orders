package br.com.alysonrodrigo.apimoutstiorders.domain.service;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepProduct;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.RepCategoryRepository;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.RepProductRepository;
import br.com.alysonrodrigo.apimoutstiorders.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @InjectMocks
    private RepCategoryService categoryService;

    @Mock
    private RepCategoryRepository categoryRepository;

    public CategoryServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory() {
        RepCategory category = new RepCategory();
        category.setId(1L);
        category.setName("Informática");
        category.setDescription("Informática");

        when(categoryRepository.save(any(RepCategory.class))).thenReturn(category);

        RepCategory result = categoryRepository.save(category);

        assertNotNull(result);
        assertEquals("Informática", result.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testGetCategoryById() {
        RepCategory category = new RepCategory();
        category.setId(1L);
        category.setName("Test Product");
        category.setDescription("Informática");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        RepCategory result = categoryService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetProductByIdNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.findById(1L));
        verify(categoryRepository, times(1)).findById(1L);
    }
}