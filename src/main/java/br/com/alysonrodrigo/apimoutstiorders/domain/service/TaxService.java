package br.com.alysonrodrigo.apimoutstiorders.domain.service;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.Tax;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxService {

    private final TaxCacheService taxCacheService;

    private final TaxRepository taxRepository;

    public TaxService(TaxCacheService taxCacheService, TaxRepository taxRepository) {
        this.taxCacheService = taxCacheService;
        this.taxRepository = taxRepository;
    }

    /**
     * Recupera todos os impostos, primeiro do cache, depois do banco.
     */
    public List<Tax> getAllTaxes() {
        List<Tax> taxes = taxCacheService.getTaxesFromCache();

        if (taxes == null || taxes.isEmpty()) {
            taxes = taxRepository.findAll();
            taxCacheService.saveTaxesToCache(taxes);
        }

        return taxes;
    }

    /**
     * Limpa o cache e salva um novo imposto.
     */
    public Tax saveTax(Tax tax) {
        Tax savedTax = taxRepository.save(tax);
        taxCacheService.evictTaxesFromCache(); // Remove o cache antigo
        getAllTaxes(); // Recarrega o cache atualizado
        return savedTax;
    }

    /**
     * Exclui um imposto pelo ID, remove do banco e atualiza o cache.
     */
    public void deleteTax(Long id) {
        taxRepository.deleteById(id);
        taxCacheService.evictTaxesFromCache(); // Remove o cache antigo
        getAllTaxes(); // Recarrega o cache atualizado
    }

    public List<Tax> getTaxesByCategory(Long categoryId) {
        return taxRepository.findByCategoryId(categoryId);
    }
}
