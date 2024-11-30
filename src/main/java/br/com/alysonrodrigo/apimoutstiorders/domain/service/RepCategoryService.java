package br.com.alysonrodrigo.apimoutstiorders.domain.service;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepUser;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.RepCategoryRepository;
import br.com.alysonrodrigo.apimoutstiorders.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepCategoryService {


    private final RepCategoryRepository categoryRepository;

    public RepCategoryService(RepCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retorna todas as categorias.
     *
     * @return Lista de categorias.
     */
    public List<RepCategory> findAll() {
        return categoryRepository.findAll();
    }

    public RepCategory findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + id));
    }

    public Optional<RepCategory> findByIdOutException(Long id) {
        return categoryRepository.findById(id);
    }

    public RepCategory save(RepCategory category) {
        return categoryRepository.save(category);
    }


    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

}
