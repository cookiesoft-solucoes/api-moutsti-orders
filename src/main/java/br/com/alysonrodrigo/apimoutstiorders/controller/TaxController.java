package br.com.alysonrodrigo.apimoutstiorders.controller;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.Category;
import br.com.alysonrodrigo.apimoutstiorders.domain.model.Tax;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.CategoryService;
import br.com.alysonrodrigo.apimoutstiorders.domain.service.TaxService;
import br.com.alysonrodrigo.apimoutstiorders.dto.TaxDTO;
import br.com.alysonrodrigo.apimoutstiorders.mapper.TaxMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taxes")
public class TaxController {

    private final TaxService taxService;
    private final CategoryService categoryService;
    private final TaxMapper taxMapper;

    public TaxController(TaxService taxService,
                         TaxMapper taxMapper,
                         CategoryService categoryService) {
        this.taxService = taxService;
        this.taxMapper = taxMapper;
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public List<Tax> getAllTaxes() {
        return taxService.getAllTaxes();
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<Tax> createTax(@Valid @RequestBody TaxDTO taxDTO) {

        Category category = categoryService.findById(taxDTO.getCategoryId());

        Tax tax = taxMapper.toEntity(taxDTO, category);
        Tax savedTax = taxService.saveTax(tax);
        return ResponseEntity.ok(savedTax);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Tax> updateTax(@PathVariable Long id, @Valid @RequestBody TaxDTO taxDTO) {

        Category category = categoryService.findById(taxDTO.getCategoryId());

        Tax tax = taxMapper.toEntity(taxDTO, category);
        tax.setId(id);
        Tax updatedTax = taxService.saveTax(tax);
        return ResponseEntity.ok(updatedTax);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTax(@PathVariable Long id) {
        taxService.deleteTax(id);
        return ResponseEntity.noContent().build();
    }
}
