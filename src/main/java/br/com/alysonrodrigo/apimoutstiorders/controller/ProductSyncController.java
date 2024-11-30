package br.com.alysonrodrigo.apimoutstiorders.controller;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepProduct;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.RepProductService;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepCategoryDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.RepProductDTO;
import br.com.alysonrodrigo.apimoutstiorders.mapper.ProductMapper;
import br.com.alysonrodrigo.apimoutstiorders.producer.CategorySyncProducer;
import br.com.alysonrodrigo.apimoutstiorders.producer.ProductSyncProducer;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductSyncController {

    private final ProductSyncProducer productSyncProducer;

    private final RepProductService repProductService;

    private final ProductMapper productMapper;

    public ProductSyncController(ProductSyncProducer productSyncProducer,
                                 RepProductService repProductService,
                                 ProductMapper productMapper) {
        this.productSyncProducer = productSyncProducer;
        this.repProductService = repProductService;
        this.productMapper = productMapper;
    }

    /**
     * Endpoint para enviar um evento de sincronização de usuário.
     *
     * @param repProductDTO Dados do usuário a ser sincronizado.
     * @return Resposta indicando que o evento foi enviado.
     */
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/sync")
    public ResponseEntity<String> syncUser(@RequestBody RepProductDTO repProductDTO) {
        productSyncProducer.sendProductCreatedMessage(repProductDTO);
        return ResponseEntity.ok("Product sync message sent for product: " + repProductDTO.getName());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<List<RepProductDTO>> allProducts() {
        List<RepProduct> products = repProductService.getAllProducts(); // Método no serviço para buscar todos os produtos
        List<RepProductDTO> productDTOs = products.stream()
                .map(productMapper::toDTO) // Converter entidades para DTOs
                .toList();

        return ResponseEntity.ok(productDTOs);
    }
}