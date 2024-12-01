package br.com.alysonrodrigo.apimoutstiorders.domain.service;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepCategory;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.RepProduct;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.RepProductRepository;
import br.com.alysonrodrigo.apimoutstiorders.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepProductService {

    private final RepProductRepository productRepository;

    public RepProductService(RepProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public RepProduct save(RepProduct product) {
        return productRepository.save(product);
    }

    public RepProduct updateProduct(Long id, RepProduct product) {
        RepProduct existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setCategory(product.getCategory());
        return productRepository.save(existingProduct);
    }

    public RepProduct getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Optional<RepProduct> findByIdOutException(Long id) {
        return productRepository.findById(id);
    }

    public List<RepProduct> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(Long id) {
        RepProduct product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.setDeleted(true);
        productRepository.save(product);
    }
}
