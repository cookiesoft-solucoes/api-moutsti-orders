package br.com.alysonrodrigo.apimoutstiorders.domain.service;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.Category;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.CategoryRepository;
import br.com.alysonrodrigo.apimoutstiorders.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {


    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retorna todas as categorias.
     *
     * @return Lista de categorias.
     */
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * Retorna uma categoria por ID.
     *
     * @param id ID da categoria.
     * @return Objeto Optional contendo a categoria, se encontrada.
     */
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + id));
    }

    /**
     * Salva ou atualiza uma categoria.
     *
     * @param category Objeto da categoria a ser salvo ou atualizado.
     * @return Categoria salva.
     */
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Exclui uma categoria pelo ID.
     *
     * @param id ID da categoria a ser excluída.
     */
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

}
