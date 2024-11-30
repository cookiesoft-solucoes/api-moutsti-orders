package br.com.alysonrodrigo.apimoutstiorders.domain.service;

import br.com.alysonrodrigo.apimoutstiorders.domain.model.Tax;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TaxCacheService {

    private static final String TAX_CACHE_KEY = "taxes";

    private final RedisTemplate<String, Object> redisTemplate;

    public TaxCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
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


}
