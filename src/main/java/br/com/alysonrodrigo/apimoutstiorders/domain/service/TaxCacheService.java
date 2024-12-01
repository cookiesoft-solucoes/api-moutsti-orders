package br.com.alysonrodrigo.apimoutstiorders.domain.service;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.Tax;
import br.com.alysonrodrigo.apimoutstiorders.domain.repository.TaxRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class TaxCacheService {

    private static final String TAX_CACHE_KEY = "taxes";

    private final RedisTemplate<String, Object> redisTemplate;

    private final TaxRepository taxRepository;

    public TaxCacheService(RedisTemplate<String, Object> redisTemplate,
                           TaxRepository taxRepository) {
        this.redisTemplate = redisTemplate;
        this.taxRepository = taxRepository;
    }

    public void saveTaxesToCache(List<Tax> taxes) {
        redisTemplate.opsForValue().set(TAX_CACHE_KEY, taxes, 1, TimeUnit.DAYS); // Expira em 1 hora
    }

    /**
     * Recuperar lista de impostos do Redis.
     */
    public List<Tax> getTaxesFromCache() {
        return (List<Tax>) redisTemplate.opsForValue().get(TAX_CACHE_KEY);
    }

    /**
     * Remover lista de impostos do Redis.
     */
    public void evictTaxesFromCache() {
        redisTemplate.delete(TAX_CACHE_KEY);
    }

    /**
     * Busca as taxas associadas a uma categoria do Redis.
     * Se não encontradas no cache, busca no banco de dados e atualiza o cache.
     *
     * @param categoryId ID da categoria.
     * @return Lista de taxas associadas à categoria.
     */
    public List<Tax> getTaxesByCategory(Long categoryId) {
        String cacheKey = "taxes:category:" + categoryId;

        // Tenta buscar do Redis
        List<Object> cachedObjects = redisTemplate.opsForList().range(cacheKey, 0, -1);

        // Converter para List<Tax> se não for nulo ou vazio
        List<Tax> taxes = null;
        if (cachedObjects != null && !cachedObjects.isEmpty()) {
            taxes = cachedObjects.stream()
                    .filter(Objects::nonNull)
                    .map(object -> (Tax) object) // Cast para Tax
                    .toList();
        }

        // Se o cache estiver vazio, buscar no banco e atualizar o cache
        if (taxes == null || taxes.isEmpty()) {
            taxes = taxRepository.findByCategoryId(categoryId);
            if (!taxes.isEmpty()) {
                redisTemplate.opsForList().rightPushAll(cacheKey, taxes);
                redisTemplate.expire(cacheKey, Duration.ofHours(1)); // Expiração de 1 hora
            }
        }

        return taxes;
    }


}
