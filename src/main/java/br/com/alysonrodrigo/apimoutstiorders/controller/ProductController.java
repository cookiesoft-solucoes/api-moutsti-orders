package br.com.alysonrodrigo.apimoutstiorders.controller;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.Category;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.Product;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.Tax;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.CategoryService;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.ProductService;
import br.com.alysonrodrigo.apimoutstiorders.dto.ProductDTO;
import br.com.alysonrodrigo.apimoutstiorders.dto.TaxDTO;
import br.com.alysonrodrigo.apimoutstiorders.mapper.ProductMapper;
import br.com.alysonrodrigo.apimoutstiorders.mapper.TaxMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, CategoryService categoryService, ProductMapper productMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO productDTO) {

        Category category = categoryService.findById(productDTO.getCategoryId());

        Product product = productMapper.toEntity(productDTO, category);

        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {

        Category category = categoryService.findById(productDTO.getCategoryId());

        Product product = productMapper.toEntity(productDTO, category);
        product.setId(id);
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}