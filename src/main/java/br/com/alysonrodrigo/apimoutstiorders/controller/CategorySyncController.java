package br.com.alysonrodrigo.apimoutstiorders.controller;


import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepProduct;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.RepCategoryService;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepCategoryDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepUserDTO;
import br.com.alysonrodrigo.apimoutstiorders.mapper.CategoryMapper;
import br.com.alysonrodrigo.apimoutstiorders.producer.CategorySyncProducer;
import br.com.alysonrodrigo.apimoutstiorders.producer.UserSyncProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategorySyncController {

    private final CategorySyncProducer categorySyncProducer;

    private final RepCategoryService categoryService;

    private final CategoryMapper categoryMapper;

    public CategorySyncController(CategorySyncProducer categorySyncProducer,
                                  RepCategoryService categoryService,
                                  CategoryMapper categoryMapper) {
        this.categorySyncProducer = categorySyncProducer;
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Endpoint para enviar um evento de sincronização de usuário.
     *
     * @param repCategoryDTO Dados do usuário a ser sincronizado.
     * @return Resposta indicando que o evento foi enviado.
     */
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/sync")
    public ResponseEntity<String> syncUser(@RequestBody RepCategoryDTO repCategoryDTO) {
        categorySyncProducer.sendCategoryCreatedMessage(repCategoryDTO);
        return ResponseEntity.ok("Category sync message sent for category: " + repCategoryDTO.getName());
    }

    @Operation(summary = "Listar todas categorias", description = "Listar todas categorias.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido processado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RepCategoryDTO.class)))
    })
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<List<RepCategoryDTO>> allCategories() {
        List<RepCategory> categories = categoryService.findAll(); // Método no serviço para buscar todos os produtos
        List<RepCategoryDTO> productDTOs = categories.stream()
                .map(categoryMapper::toDTO) // Converter entidades para DTOs
                .toList();

        return ResponseEntity.ok(productDTOs);
    }
}
